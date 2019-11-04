package ru.skprojects.wmblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/contacts")
public class ContactsController {

    private String name;
    private String email;
    private String message;

    @GetMapping
    public ModelAndView contacts() {
        return new ModelAndView("contacts");
    }

    @PostMapping
    public ModelAndView send(@RequestParam(value="name") String name, @RequestParam(value="email") String email, @RequestParam(value="message") String message) {
        this.name = name;
        this.email = email;
        this.message = message;
        return new ModelAndView("contacts","status","OK");
    }

    public String getContactInfo() {
        return this.name + " " + this.email + " " + this.message;
    }

}
