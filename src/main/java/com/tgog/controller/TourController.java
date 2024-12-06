package com.tgog.controller;

import com.tgog.model.User;
import com.tgog.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ImageController {


    @Autowired
    TourService tourService;

    @GetMapping("/admin/tour")
    public String displayUserPage(Model model) {
        model.addAttribute("tour", new User());
        return "tour.html";
    }

    @RequestMapping(value = "/admin/saveTour", method = POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, Errors errors) {
        if(errors.hasErrors()){
            log.error("Tour form validation failed due to : " + errors.toString());
            return "user.html";
        }
        tourService.createNewUser(user);
        return "redirect:/admin/displayTours/page/1?sortField=userId&sortDir=desc";
    }

    @RequestMapping("/admin/displayTours/page/{pageNum}")
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<User> page = tourService.findAllUsers(pageNum,sortField,sortDir);
        List<User> userList = page.getContent();
        ModelAndView modelAndView = new ModelAndView("userList.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalMsgs", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("userList",userList);
        return modelAndView;
    }

//    @RequestMapping(value = "/admin/changeStatus", method = GET)
//    public String changeStatus(@RequestParam int id) {
//        tourService.updateUserStatus(id);
//        return "redirect:/admin/displayTours/page/1?sortField=userId&sortDir=desc";
//    }

}
