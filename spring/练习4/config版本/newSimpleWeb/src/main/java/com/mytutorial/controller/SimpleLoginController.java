package com.mytutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mytutorial.model.Login;

@Controller
@RequestMapping("/")
public class SimpleLoginController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(ModelMap model) {
        //pass the viewName, modelName, and model object
        return new ModelAndView("login", "login-form", new Login()); //login.jsp,
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String submit(@ModelAttribute("login-form") Login login, ModelMap model) {
        System.out.println("User is: " + login.getUserId());
        model.addAttribute("loggedInUser", login);
        return "login-success";
    }

}
