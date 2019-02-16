package com.mattcorwin.petitonwebapp.controllers;


import com.mattcorwin.petitonwebapp.models.Comment;
import com.mattcorwin.petitonwebapp.models.CommentTypes;
import com.mattcorwin.petitonwebapp.models.Employee;
import com.mattcorwin.petitonwebapp.models.UserService;
import com.mattcorwin.petitonwebapp.models.data.CommentDao;
import com.mattcorwin.petitonwebapp.models.data.EmployeeDao;
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
import java.time.LocalDate;

/**
 * @author Matt Corwin
 * This class contains methods that display and process a comment form
 */
@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    /**
     * This route handler accepts the initial GET request and displays the comment form
     * @param model used to add attributes to the thymeleaf template
     * @return returns the comment/index.html file, which is the comment form
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayCommentForm(Model model) {

        model.addAttribute("title", "Contact Us");
        model.addAttribute("commentTypes", CommentTypes.values());
        model.addAttribute(new Comment());
        return "comment/index";
    }

    /**
     * Creates a new comment object and saves it to the database if there are no errors
     * @param comment is the comment object created from the form data
     * @param errors any potential errors from the model binding process
     * @param model used to add attributes to the thymeleaf template
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processCommentForm(@ModelAttribute @Valid Comment comment, Errors errors, Model model) {

        model.addAttribute("title", "Contact us");
        model.addAttribute("commentTypes", CommentTypes.values());

        if (errors.hasErrors()) {
            model.addAttribute("comment", comment);
            model.addAttribute("commentTypes", CommentTypes.values());

            return "comment/index";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = userService.findUserByUsername(auth.getName());
        comment.setEmployee(employee);
        comment.setDate(LocalDate.now());
        commentDao.save(comment);
        model.addAttribute("successMessage", "Comment submitted successfully");

        return "comment/index";
    }

}
