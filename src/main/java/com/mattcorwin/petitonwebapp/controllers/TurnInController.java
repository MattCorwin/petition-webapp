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
import java.time.LocalDate;
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
            actualEmployee.addToAccountBalance(turnIn.getTotalPayout());
            employeeDao.save(actualEmployee);
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
        model.addAttribute("title", "Edit a turn-in");
        return "turn-in/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST, params = {"id"})
    public String processEditForm(@RequestParam int id, Model model) {

        model.addAttribute("title", "Edit a turn-in");
        model.addAttribute("employees", employeeDao.findAll());
        Optional<Employee> newEmployee = employeeDao.findById(id);
        Employee actualEmployee = newEmployee.get();
        model.addAttribute("selectedEmployee",actualEmployee);
        return "turn-in/edit";
    }


    @RequestMapping(value = "edit", method = RequestMethod.POST, params = {"id", "dateId"})
    public String processEditForm(@RequestParam int id, @RequestParam int dateId, Model model) {
        model.addAttribute("title", "Edit a turn-in");
        model.addAttribute("employees", employeeDao.findAll());
        Optional<Employee> newEmployee = employeeDao.findById(id);
        Employee actualEmployee = newEmployee.get();
        model.addAttribute("selectedEmployee",actualEmployee);
        Optional<TurnIn> tempTurnIn = turnInDao.findById(dateId);
        TurnIn actualTurnIn = tempTurnIn.get();
        model.addAttribute("selectedTurnIn", actualTurnIn);
        return "turn-in/edit";
    }

    @RequestMapping(value = "edit/submit", method = RequestMethod.POST)
    public String submitEditForm(@ModelAttribute @Valid TurnIn updatedTurnIn, Errors errors, @RequestParam int id,
                                 @RequestParam int dateId, @RequestParam double previousTotalPayout, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit a turn-in");
            model.addAttribute("employees", employeeDao.findAll());
            Optional<Employee> newEmployee = employeeDao.findById(id);
            Employee actualEmployee = newEmployee.get();
            model.addAttribute("selectedEmployee",actualEmployee);
            Optional<TurnIn> tempTurnIn = turnInDao.findById(dateId);
            TurnIn actualTurnIn = tempTurnIn.get();
            model.addAttribute("selectedTurnIn", actualTurnIn);
            return "turn-in/edit";
        }
        Optional<Employee> newEmployee = employeeDao.findById(id);
        if (newEmployee.isPresent()) {
            Employee actualEmployee = newEmployee.get();
            updatedTurnIn.setEmployee(actualEmployee);
            turnInDao.save(updatedTurnIn);
            turnInDao.deleteById(dateId);
            double difference = updatedTurnIn.getTotalPayout() - previousTotalPayout;
            actualEmployee.addToAccountBalance(difference);
            employeeDao.save(actualEmployee);
            model.addAttribute("successMessage", "Turn-in Edited Successfully");
            model.addAttribute("title", "Enter a new turn-in");
            model.addAttribute("employees", employeeDao.findAll());

            return "turn-in/edit";
        }


        model.addAttribute("successMessage", "Something went wrong");
        return "turn-in/edit";
    }



}

