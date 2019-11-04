package ru.skprojects.wmblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.skprojects.wmblog.model.Message;
import ru.skprojects.wmblog.service.MessageService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    private final MessageService messageService;

    public AdminController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ModelAndView admin() {
        return new ModelAndView("admin");
    }

    @PostMapping
    public ModelAndView addMessage(@RequestParam(name = "title") String title, @RequestParam(name = "text") String text, @RequestParam(name = "image") String image ) {
        Message newMessage = new Message();
        newMessage.setTitle(title);
        newMessage.setText(text);
        newMessage.setImage(image);
        messageService.addNewMessage(newMessage);
        return new ModelAndView("admin", "response", "OK");
    }
}
