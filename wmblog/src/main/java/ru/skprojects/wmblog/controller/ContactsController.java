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

    @GetMapping
    public ModelAndView contacts() {
        return new ModelAndView("contacts");
    }

    @PostMapping
    public ModelAndView send(@RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="message") String message) {

        return new ModelAndView("contacts","status","OK");
    }

}
