package com.tgog.controller;

import com.tgog.config.S3Properties;
import com.tgog.model.Review;
import com.tgog.model.Tour;
import com.tgog.repository.TourRepository;
import com.tgog.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
public class HomeController {
    @Autowired
    TourRepository tourRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    S3Properties s3Properties;

    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage(Model model){
        Iterable<Tour> tours = tourRepository.findAll();

        List<Tour> tourList = StreamSupport
                .stream(tours.spliterator(), false)
                .collect(Collectors.toList());
        Tour.Type[] types = Tour.Type.values();
        for (Tour.Type type : types) {
            model.addAttribute(type.toString(),
                    (tourList.stream().filter(tour -> tour.getType().equals(type)).collect(Collectors.toList())));
        }
        List<Review> reviewList = reviewService.findAllReviews();
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("imagesBaseUrl",s3Properties.getImagesBaseUrl());
        return "home.html";
    }

    @RequestMapping(value={"/aboutUs"})
    public String displayAboutUsPage(){
        return "aboutUs.html";
    }

}
