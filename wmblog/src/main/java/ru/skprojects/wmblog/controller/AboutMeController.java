package ru.skprojects.wmblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/aboutme")
public class AboutMeController {

    @GetMapping
    public ModelAndView aboutMe() {
        return new ModelAndView("aboutme");
    }

}
