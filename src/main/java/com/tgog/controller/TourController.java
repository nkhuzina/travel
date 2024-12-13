package com.tgog.controller;

import com.tgog.config.AppProporties;
import com.tgog.config.S3Properties;
import com.tgog.constants.ApplicationType;
import com.tgog.model.Contact;
import com.tgog.model.Tour;
import com.tgog.service.ContactService;
import com.tgog.service.TourService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Slf4j
@Controller
public class TourController {

    @Autowired
    TourService tourService;

    @Autowired
    AppProporties appProporties;

    @Autowired
    S3Properties s3Properties;

    @Autowired
    ContactService contactService;

    @GetMapping("/admin/tour")
    public String displayTourPage(Model model) {
        model.addAttribute("tour", new Tour());
        return "tour.html";
    }

    @GetMapping("/tour")
    public String displayTour(Model model,
                              @RequestParam(name = "tourId") Integer tourId) {
        model.addAttribute("tourId", tourId);
        model.addAttribute("contact", new Contact());
        return "tourPage.html";
    }

    @RequestMapping(value = "/joinTour", method = POST)
    public String joinTour(@RequestParam(name = "tourId") Integer tourId,
                           @Valid @ModelAttribute("contact") Contact contact,
                           Errors errors,
                           Model model) {
        if(errors.hasErrors()){
            log.error("Tour form validation failed due to : " + errors.toString());
            model.addAttribute("tourId", tourId);
            return "tourPage.html";
        }
        contact.setType(ApplicationType.JOIN);
        contact.setTourId(tourId);
        contactService.saveMsgDetails(contact);
        return "redirect:/tour?tourId="+tourId;
    }


    @RequestMapping(value = "/admin/saveTour", method = POST)
    public String saveTour(@Valid @ModelAttribute("tour") Tour tour,
                           @RequestParam("image") MultipartFile file, Errors errors) throws IOException {
        if(errors.hasErrors()){
            log.error("Tour form validation failed due to : " + errors.toString());
            return "tour.html";
        }
        uploadFileTos3bucket(file);
        tour.setImagePath(Paths.get(file.getOriginalFilename()).toString());
        tourService.createNewTour(tour);
        return "redirect:/admin/displayTours/page/1?sortField=tourId&sortDir=desc";
    }


    private void uploadFileTos3bucket(MultipartFile file) throws IOException {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(s3Properties.getAccessKey(),
                s3Properties.getSecretKey());
        try (S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key( file.getOriginalFilename())
                    .build();
            s3Client.putObject(putRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        }
    }

    @RequestMapping("/admin/displayTours/page/{pageNum}")
    public ModelAndView displayTours(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<Tour> page = tourService.findAllTours(pageNum,sortField,sortDir);
        List<Tour> tourList = page.getContent();
        System.out.println("tourList = " + tourList.stream().count());
        ModelAndView modelAndView = new ModelAndView("tourList.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalMsgs", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("tourList",tourList);
        return modelAndView;
    }


//@RequestMapping(value = "admin/tourEdit", method = GET)
//    public String editTourPage(Model model) {
//        model.addAttribute("tour", new Tour());
//        return "tour.html";
//    }
    @RequestMapping(value = "/admin/tour/changeStatus", method = GET)
    public String changeTourStatus(@RequestParam int id) {
        tourService.updateShowOnPageStatus(id);
        return "redirect:/admin/displayTours/page/1?sortField=tourId&sortDir=desc";
    }

}
