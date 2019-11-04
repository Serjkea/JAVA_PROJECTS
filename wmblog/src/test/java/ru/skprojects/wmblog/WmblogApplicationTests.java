package ru.skprojects.wmblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.Assert;
import ru.skprojects.wmblog.controller.AdminController;
import ru.skprojects.wmblog.controller.BlogController;
import ru.skprojects.wmblog.repository.MessageRepository;
import ru.skprojects.wmblog.service.MessageService;
import ru.skprojects.wmblog.service.MessageServiceImpl;

import java.util.List;

@SpringBootTest
class WmblogApplicationTests {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void checkMessages() {
        MessageService messageService = new MessageServiceImpl(messageRepository);
        BlogController blogController = new BlogController(messageService);
        AdminController adminController = new AdminController(messageService);
        String title = "Test Title";
        String text = "Test text";
        String image = "Test.img";

        ModelAndView adminModelAndView = adminController.addMessage(title,text,image);

        Assert.notNull(adminModelAndView, "check adminModel");
        Assert.isTrue(adminModelAndView.getViewName().equals("admin"), "check adminView");

        ModelAndView blogModelAndView = blogController.blog();

        Assert.notNull(blogModelAndView, "check blogModel");
        Assert.isTrue(blogModelAndView.getViewName().equals("blog"), "check blogView");
        Assert.isTrue(((List)blogModelAndView.getModel().get("messages")).get(0).toString().equals(title + " " + image + " " + text), "check message");
    }

}
