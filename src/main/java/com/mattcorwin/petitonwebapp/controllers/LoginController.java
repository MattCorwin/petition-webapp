package com.mattcorwin.petitonwebapp.controllers;

import com.mattcorwin.petitonwebapp.models.Employee;
import com.mattcorwin.petitonwebapp.models.TurnIn;
import com.mattcorwin.petitonwebapp.models.data.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private EmployeeDao employeeDao;

    //LOGIN GET REQUEST
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayLogin(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute(new Employee());
        return "login/index";

    }

    //LOGIN POST REQUEST, verify login data by CREATING NEW VALID EMPLOYEE OBJECT, pass to main screen
    //Todo: implement password hashing
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processLogin(@ModelAttribute @Valid Employee employee, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Login");
            model.addAttribute("employee", employee);
            if (!employee.getPassword().equals(employee.getPasswordRetype())) {
                model.addAttribute("noMatch", "Password and retype do not match");
            }
            return "login/index";
        }

        if (!employee.getPassword().equals(employee.getPasswordRetype())) {
            model.addAttribute("noMatch", "Password and retype do not match");
            model.addAttribute("title", "Login");
            model.addAttribute("employee", employee);
            return "login/index";
        }

        for (Employee knownEmployee : employeeDao.findAll()) {
            if(employee.getUsername().equals(knownEmployee.getUsername())) {

                if (employee.getPassword().equals(knownEmployee.getPassword())) {
                    model.addAttribute("employee", knownEmployee);
                    model.addAttribute("turnIns", knownEmployee.getSelectedTurnIns());
                    model.addAttribute("title", "Account summary for " + knownEmployee.getFirstName() + " " + knownEmployee.getLastName());
                    return "index";
                }
                else {
                    model.addAttribute("loginError", "Incorrect Password");
                }
            }
        }

         model.addAttribute("loginError", "Username not found");

        return "login/index";
    }

    @RequestMapping(value="signup", method = RequestMethod.GET)
    public String displaySignup(Model model) {
        model.addAttribute("title", "Signup for an account");
        model.addAttribute(new Employee());
        return"login/signup";
    }


    @RequestMapping(value="signup", method = RequestMethod.POST)
    public String processSignup(@ModelAttribute @Valid Employee newEmployee, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Signup for an account");
            model.addAttribute("employee", newEmployee);
            if (!newEmployee.getPassword().equals(newEmployee.getPasswordRetype())) {
                model.addAttribute("noMatch", "Password and retype do not match");
            }
            return "login/signup";
        }

        if (!newEmployee.getPassword().equals(newEmployee.getPasswordRetype())) {
            model.addAttribute("noMatch", "Password and retype do not match");
            model.addAttribute("title", "Signup for an account");
            model.addAttribute("employee", newEmployee);
            return "login/signup";
        }
        for (Employee knownEmployee : employeeDao.findAll()) {
            if(newEmployee.getUsername().equals(knownEmployee.getUsername())) {
                model.addAttribute("signupError", "Please enter a new username, that one already exists");
                model.addAttribute("employee", newEmployee);
                return "login/signup";
            }
        }



        model.addAttribute("turnIns", newEmployee.getSelectedTurnIns());
        model.addAttribute("employee", newEmployee);
        employeeDao.save(newEmployee);
        model.addAttribute("title", "Account summary for " + newEmployee.getFirstName() + " " + newEmployee.getLastName());

        return "index";

    }
}


