package ru.skprojects.wmblog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import ru.skprojects.wmblog.service.MessageService;

@SpringBootTest
class AdminControllerTest {

    private AdminController adminController;

    @BeforeEach
    void init() {
        MessageService messageService = Mockito.mock(MessageService.class);
        Mockito.doReturn(null).when(messageService).getAllMessages();
        this.adminController = new AdminController(messageService);
    }

    @Test
    void admin() {
        ModelAndView modelAndView = this.adminController.admin();

        Assert.notNull(modelAndView, "check model");
        Assert.isTrue(modelAndView.getViewName().equals("admin"), "check view");
    }

    @Test
    void addMessage() {
        ModelAndView modelAndView = this.adminController.addMessage("Title", "Text", "Image");

        Assert.notNull(modelAndView, "check model");
        Assert.isTrue(modelAndView.getViewName().equals("admin"), "check view");
    }
}