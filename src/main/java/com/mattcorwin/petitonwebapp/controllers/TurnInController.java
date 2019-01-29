package com.mattcorwin.petitonwebapp.controllers;

import com.mattcorwin.petitonwebapp.models.Employee;
import com.mattcorwin.petitonwebapp.models.TurnIn;
import com.mattcorwin.petitonwebapp.models.data.EmployeeDao;
import com.mattcorwin.petitonwebapp.models.data.TurnInDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("turn-in")
public class TurnInController {

    @Autowired
    private TurnInDao turnInDao;

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayTurnInForm(Model model) {

        model.addAttribute("title", "Enter a new turn-in");
        model.addAttribute("employees", employeeDao.findAll());
        model.addAttribute(new TurnIn());

        return "turn-in/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processTurnInForm(@ModelAttribute @Valid TurnIn turnIn, Errors errors, Model model, @RequestParam int employeeId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter a new turn-in");
            model.addAttribute("employees", employeeDao.findAll());
            model.addAttribute("turnIn", turnIn);
            return "turn-in/add";
        }

        Optional<Employee> newEmployee = employeeDao.findById(employeeId);
        if (newEmployee.isPresent()) {
            Employee actualEmployee = newEmployee.get();
            turnIn.setEmployee(actualEmployee);
            turnInDao.save(turnIn);
            model.addAttribute("successMessage", "Turn-in Submitted Successfully");
            model.addAttribute("title", "Enter a new turn-in");
            model.addAttribute("employees", employeeDao.findAll());
            return "turn-in/add";
        }

        return "turn-in/add";

    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String displayEditForm(Model model) {

        model.addAttribute("employees", employeeDao.findAll());
        model.addAttribute("title", "Select a petitioner to edit a turn-in");
        return"turn-in/edit";
    }
}

