package ru.skprojects.wmblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.skprojects.wmblog.repository.MessageRepository;

@Controller
@RequestMapping(value="/blog")
public class BlogController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public ModelAndView blog() {
        return new ModelAndView("blog", "messages", messageRepository.findAll());
    }
}
