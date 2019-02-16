package com.mattcorwin.petitonwebapp.controllers;


import com.mattcorwin.petitonwebapp.models.Employee;
import com.mattcorwin.petitonwebapp.models.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="")
    public String displayUserIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = userService.findUserByUsername(auth.getName());

        model.addAttribute("turnIns", employee.getSelectedTurnIns());
        model.addAttribute("employee", employee);
        model.addAttribute("title", "Account summary for " + employee.getFirstName() + " " + employee.getLastName());

        return "/user/index";
    }
}
