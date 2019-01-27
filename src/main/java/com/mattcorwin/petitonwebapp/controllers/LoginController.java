package com.mattcorwin.petitonwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    //LOGIN GET REQUEST
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayLogin(Model model) {
        model.addAttribute("title", "Login");
        return "login/index";

    }

    //LOGIN POST REQUEST, verify login data by CREATING NEW VALID EMPLOYEE OBJECT, pass to main screen
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processLogin(@RequestParam String username, @RequestParam String password, @RequestParam String verify, Model model) {

        return "index";
    }
}
