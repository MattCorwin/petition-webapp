package com.mattcorwin.petitonwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class RootController {

    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }
}


