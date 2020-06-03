package com.ran.apps.saad.controller;

import javax.servlet.http.HttpServletRequest;

import com.ran.apps.saad.filters.JwtAccessFilter;
import com.ran.apps.saad.model.User;
import com.ran.apps.saad.service.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private MyUserDetailsService userService;

    @Autowired
    private JwtAccessFilter accessFilter;

    @GetMapping("/register")
    public String getRegister(Model model, HttpServletRequest request) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(path = "/saveUser", method = RequestMethod.POST)
    public String getSaveUser(@RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password", required = true) String password, HttpServletRequest request) {
        if (accessFilter.checkCookiesForToken(request)) {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setActive(true);
            user.setRoles("ROLE_USER");
            userService.saveUser(user);
            return "redirect:/";
        }
        return "redirect:/login";
    }

}