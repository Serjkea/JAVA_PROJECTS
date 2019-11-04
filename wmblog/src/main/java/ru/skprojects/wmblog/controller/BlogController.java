package ru.skprojects.wmblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.skprojects.wmblog.service.MessageService;

@Controller
@RequestMapping(value="/blog")
public class BlogController {

    private final MessageService messageService;

    public BlogController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ModelAndView blog() {
        return new ModelAndView("blog", "messages", messageService.getAllMessages());
    }
}
