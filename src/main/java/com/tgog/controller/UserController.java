package com.tgog.controller;

import com.tgog.model.User;
import com.tgog.service.UserService;
import jakarta.servlet.http.HttpSession;
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
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/admin/user")
    public String displayUserPage(Model model) {
        model.addAttribute("user", new User());
        return "user.html";
    }

    @RequestMapping(value = "/admin/saveUser", method = POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, Errors errors) {
        if(errors.hasErrors()){
            log.error("User form validation failed due to : " + errors.toString());
            return "user.html";
        }
        userService.createNewUser(user);
        return "redirect:/admin/displayUsers/page/1?sortField=userId&sortDir=desc";
    }

    @RequestMapping("/admin/displayUsers/page/{pageNum}")
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<User> page = userService.findAllUsers(pageNum,sortField,sortDir);
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

    @RequestMapping(value = "/admin/user/changeStatus", method = GET)
    public String changeStatus(@RequestParam int id) {
        userService.updateUserStatus(id);
        return "redirect:/admin/displayUsers/page/1?sortField=userId&sortDir=desc";
    }


    @RequestMapping("/admin/displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        User profile = new User();
        profile.setName(user.getName());
        profile.setMobileNumber(user.getMobileNumber());
        profile.setEmail(user.getEmail());
        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @PostMapping(value = "/admin/updateProfile")
    public String updateProfile(@ModelAttribute("profile") User user, Errors errors,
                                HttpSession session)
    {
        if(errors.hasErrors()){
            return "profile.html";
        }
        User person = (User) session.getAttribute("loggedInUser");
        person.setEmail(user.getEmail());
        User savedUser = userService.updateUser(user);
        session.setAttribute("loggedInUser", savedUser);
        return "redirect:/displayProfile";
    }
}
