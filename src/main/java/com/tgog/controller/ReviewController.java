package com.tgog.controller;

import com.tgog.model.Review;
import com.tgog.service.ReviewService;
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
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @RequestMapping("/admin/displayReviews/page/{pageNum}")
    public ModelAndView displayReviews(Model model,
                                     @PathVariable(name = "pageNum") int pageNum,
                                     @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir) {
        Page<Review> page = reviewService.findAllReviews(pageNum,sortField,sortDir);
        List<Review> reviewList = page.getContent();
        ModelAndView modelAndView = new ModelAndView("reviewAdmin.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalMsgs", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("reviewList",reviewList);
        return modelAndView;
    }

    @RequestMapping("/displayReviews")
    public ModelAndView displayReviewList(Model model) {
        List<Review> reviewList = reviewService.findAllReviews();
        ModelAndView modelAndView = new ModelAndView("review.html");
        modelAndView.addObject("reviewList",reviewList);
        model.addAttribute("review", new Review());
        return modelAndView;
    }

    @RequestMapping(value = "/saveReview", method = POST)
    public String saveReview(@ModelAttribute("review") Review review, Errors errors) {
        if(errors.hasErrors()){
            log.error("Review form validation failed due to : " + errors.toString());
            return "review.html";
        }
        reviewService.saveReview(review);
        return "redirect:/displayReviews";
    }

    @RequestMapping(value = "/admin/review/changeStatus", method = GET)
    public String changeReviewStatus(@RequestParam int id) {
        reviewService.updateShowOnPageStatus(id);
        return "redirect:/admin/displayReviews/page/1?sortField=reviewId&sortDir=desc";
    }
}
