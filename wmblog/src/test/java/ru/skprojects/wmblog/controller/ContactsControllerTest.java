package ru.skprojects.wmblog.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactsControllerTest {

    private ContactsController contactsController;

    @BeforeEach
    void init() {
        this.contactsController = new ContactsController();
    }

    @AfterEach
    void after() {
        this.contactsController = null;
    }

    @Test
    void contacts() {
        ModelAndView modelAndView = contactsController.contacts();

        Assert.notNull(modelAndView, "check model");
        Assert.isTrue(modelAndView.getViewName().equals("contacts"), "check contacts");
    }

    @Test
    void send() {
        ModelAndView modelAndView = contactsController.send("John","mail@box.com","Test message");

        Assert.notNull(modelAndView, "check model");
        Assert.isTrue(modelAndView.getViewName().equals("contacts"), "check contacts");
        Assert.isTrue(contactsController.getContactInfo().equals("John mail@box.com Test message"), "check info");
    }
}