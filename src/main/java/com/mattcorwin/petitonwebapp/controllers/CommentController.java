package com.mattcorwin.petitonwebapp.controllers;


import com.mattcorwin.petitonwebapp.models.Comment;
import com.mattcorwin.petitonwebapp.models.CommentTypes;
import com.mattcorwin.petitonwebapp.models.Employee;
import com.mattcorwin.petitonwebapp.models.data.CommentDao;
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
import java.time.LocalDate;

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CommentDao commentDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayCommentForm(Model model) {
        model.addAttribute("title", "Contact Us");
        model.addAttribute("commentTypes", CommentTypes.values());
        model.addAttribute(new Comment());
        return "comment/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processCommentForm(@ModelAttribute @Valid Comment comment, Errors errors, @RequestParam String username, Model model) {

        model.addAttribute("title", "Contact us");
        model.addAttribute("commentTypes", CommentTypes.values());

        if (errors.hasErrors()) {
            model.addAttribute("comment", comment);
            model.addAttribute("commentTypes", CommentTypes.values());
            model.addAttribute("username", username);

            return "comment/index";
        }


        for (Employee knownEmployee : employeeDao.findAll()) {
            if(username.equals(knownEmployee.getUsername())) {
                comment.setEmployee(knownEmployee);
                comment.setDate(LocalDate.now());
                commentDao.save(comment);
                model.addAttribute("successMessage", "Comment submitted successfully");
                return "comment/index";
            }
        }
        return "comment/index";
    }

}
