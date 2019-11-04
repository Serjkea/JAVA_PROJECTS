package ru.skprojects.wmblog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.skprojects.wmblog.model.Message;

import java.util.Date;

@SpringBootTest
class MessageTests {

    private Message message;

    @BeforeEach
    void initTest() {
        this.message = new Message();
    }

    @AfterEach
    void afterTest() {
        this.message = null;
    }

    @Test
    void createMessage() {
        Assert.notNull(this.message,"create message");
    }

    @Test
    void checkTitleField() {
        String title = "Test Title";
        this.message.setTitle(title);
        Assert.isTrue(this.message.getTitle().equals(title), "check title");
    }

    @Test
    void checkTextField() {
        String text = "Test text";
        this.message.setText(text);
        Assert.isTrue(this.message.getText().equals(text), "check text");
    }

    @Test
    void checkImageField() {
        String link = "image.img";
        this.message.setImage(link);
        Assert.isTrue(this.message.getImage().equals(link), "check link");
    }

    @Test
    void checkDateField() {
        Date date = new Date();
        this.message.setDate(date);
        Assert.isTrue(this.message.getDate().equals(date), "check date");
    }

    @Test
    void createNotEmptyMessage() {
        String title = "New Title";
        String text = "Hello! This is test text";
        String image = "image.img";
        this.message.setTitle(title);
        this.message.setText(text);
        this.message.setImage(image);
        Assert.hasText(this.message.toString(), title + " " + image + " " + text);
    }

    @Test
    void checkIsNullDateField() {
        Assert.isNull(this.message.getDate(),"");
    }


}
