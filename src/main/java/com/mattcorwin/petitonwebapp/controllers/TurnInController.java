package com.mattcorwin.petitonwebapp.controllers;

import com.mattcorwin.petitonwebapp.models.Employee;
import com.mattcorwin.petitonwebapp.models.TurnIn;
import com.mattcorwin.petitonwebapp.models.data.EmployeeDao;
import com.mattcorwin.petitonwebapp.models.data.TurnInDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Matt Corwin
 * Handles the /turn-in route to process additions and alterations to an individual employee's account
 */
@Controller
@RequestMapping("turn-in")
public class TurnInController {

    @Autowired
    private TurnInDao turnInDao;

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * This route handler displays the form to add a turn-in
     * @param model used to add attributes to the thymeleaf model
     * @return returns turn-in/add.html template
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayTurnInForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeDao.findByUsername(auth.getName());
        model.addAttribute("name", employee.getUsername());
        model.addAttribute("title", "Enter a new turn-in");
        model.addAttribute("employees", employeeDao.findAll());
        model.addAttribute(new TurnIn());

        return "turn-in/add";
    }

    /**
     * Handles the POST request from the turn-in form
     * @param turnIn new TurnIn object created from form data
     * @param errors contains any errors generated during the model binding process
     * @param model used to add attributes to the thymeleaf template
     * @param employeeId used to identify the employee owner of the to be added turn-in
     * @return returns the turn-in/add.html whether or not there are errors, flashes success message if successful
     */
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

    /**
     * Displays the initial edit turn-in select with employee names from the database
     * @param model used to add attributes to the thymeleaf template
     * @return returns the template at turn-in/edit.html
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String displayEditForm(Model model) {

        model.addAttribute("employees", employeeDao.findAll());
        model.addAttribute("title", "Edit a turn-in");
        return "turn-in/edit";
    }

    /**
     * Handles the initial POST request from the turn-in form, displays a select populated with employee turn-in dates
     * @param id selected employee's id
     * @param model used to add attributes to the thymeleaf template
     * @return returns the turn-in/edit.html template
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST, params = {"id"})
    public String processEditForm(@RequestParam int id, Model model) {

        model.addAttribute("title", "Edit a turn-in");
        model.addAttribute("employees", employeeDao.findAll());
        Optional<Employee> newEmployee = employeeDao.findById(id);
        Employee actualEmployee = newEmployee.get();
        model.addAttribute("selectedEmployee",actualEmployee);
        return "turn-in/edit";
    }

    /**
     * Receives the id from selected Employee turn-in date, populates the rest of the form with the currently stored values
     * @param id the selected employee's id
     * @param dateId the selected date's id
     * @param model used to add attributes to the thymeleaf template
     * @return returns the turn-in/edit.html template
     */
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

    /**
     * Processes the final submission of the edit turn-in form, saves the result to the database
     * @param updatedTurnIn new Turn-in object generated from form data
     * @param errors contains the errors generated during the Turn-in model binding
     * @param id id of the selected Employee
     * @param dateId date of the selected Turn-in
     * @param previousTotalPayout TotalPayout stored before the edit
     * @param model used to add attributes to the thymeleaf template
     * @return returns the turn-in/edit.html template
     */
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


        model.addAttribute("editError", "Something went wrong");
        return "turn-in/edit";
    }



}

