package ru.skprojects.wmblog.controller;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import ru.skprojects.wmblog.controller.BlogController;
import ru.skprojects.wmblog.model.Message;
import ru.skprojects.wmblog.repository.MessageRepository;
import ru.skprojects.wmblog.service.MessageService;
import ru.skprojects.wmblog.service.MessageServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class BlogControllerTests {

    @Test
    void messages() {
        List<Message> messages = IntStream.range(1,3).mapToObj(i -> new Message("Title" + i, "Text" + i, "Image" + i)).collect(Collectors.toList());
        MessageRepository messageRepository = Mockito.mock(MessageRepository.class);
        MessageService messageService = new MessageServiceImpl(messageRepository);
        Mockito.doReturn(messages).when(messageRepository).findAll();
        BlogController blogController = new BlogController(messageService);

        ModelAndView modelAndView = blogController.blog();

        Assert.notNull(modelAndView,"check model");
        Assert.isTrue(modelAndView.getModel().get("messages").equals(messages), "check messages");
        Assert.isTrue(modelAndView.getViewName().equals("blog"), "check view");
    }
}
