package com.mattcorwin.petitonwebapp.controllers;



import com.mattcorwin.petitonwebapp.models.Employee;
import com.mattcorwin.petitonwebapp.models.UserService;
import com.mattcorwin.petitonwebapp.models.data.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author Matt Corwin
 * This class contains methods to process user login
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private UserService userService;



    /**
     * This route handler displays the login form
     * @param model used to add attributes to the thymeleaf template
     * @return returns login/index.html, displaying the login form
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayLogin(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute(new Employee());
        return "login/index";

    }

    /**
     * Displays the signup form
     * @param model used to add attributes to the thymeleaf template
     * @return returns login/signup.html
     */
    @RequestMapping(value="signup", method = RequestMethod.GET)
    public String displaySignup(Model model) {
        model.addAttribute("title", "Signup for an account");
        model.addAttribute(new Employee());
        return"login/signup";
    }

    /**
     * Processes the signup form POST request if there are no errors
     * @param newEmployee new Employee generated from form data by model binding
     * @param errors contains any errors generated during model binding
     * @param model used to add attributes to the thymeleaf template
     * @return returns /index.html if no errors in signup process, otherwise re-displays login/signup.html
     */
    @RequestMapping(value="signup", method = RequestMethod.POST)
    public String processSignup(@ModelAttribute @Valid Employee newEmployee, Errors errors, Model model, @RequestParam String passwordRetype) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Signup for an account");
            model.addAttribute("employee", newEmployee);
            if (!newEmployee.getPassword().equals(passwordRetype)) {
                model.addAttribute("noMatch", "Password and retype do not match");
            }
            return "login/signup";
        }

        if (!newEmployee.getPassword().equals(passwordRetype)) {
            model.addAttribute("noMatch", "Password and retype do not match");
            model.addAttribute("title", "Signup for an account");
            model.addAttribute("employee", newEmployee);
            return "login/signup";
        }
        Employee knownEmployee = userService.findUserByUsername(newEmployee.getUsername());
        if(knownEmployee != null) {
            if (knownEmployee.getUsername().equals(newEmployee.getUsername())) {
                model.addAttribute("signupError", "Please enter a new username, that one already exists");
                model.addAttribute("employee", newEmployee);
                return "login/signup";
            }
        }



        model.addAttribute("turnIns", newEmployee.getSelectedTurnIns());
        model.addAttribute("employee", newEmployee);
        userService.saveEmployee(newEmployee);
        model.addAttribute("title", "Account summary for " + newEmployee.getFirstName() + " " + newEmployee.getLastName());

        return "/user/index";

    }

    @RequestMapping(value="access-denied")
    public String accessDenied(Model model) {
        return "/login/access-denied";
    }



}

