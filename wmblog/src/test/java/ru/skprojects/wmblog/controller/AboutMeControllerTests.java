package ru.skprojects.wmblog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import ru.skprojects.wmblog.controller.AboutMeController;

@SpringBootTest
class AboutMeControllerTests {

    @Test
    void aboutme() {
        AboutMeController aboutMeController = new AboutMeController();

        ModelAndView modelAndView = aboutMeController.aboutMe();

        Assert.notNull(modelAndView,"check model");
        Assert.isTrue(modelAndView.getViewName().equals("aboutme"), "check view");
    }
}
