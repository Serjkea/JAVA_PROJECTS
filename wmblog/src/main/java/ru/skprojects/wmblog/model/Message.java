package ru.skprojects.wmblog.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private String image;

    public Message() {}

    public Message(String title, String text, String image) {
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    @Override
    public int hashCode() {
        return  31*this.title.hashCode() +
                31*this.text.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj != null) {
            if (obj instanceof Message) {
                Message tmp = (Message) obj;
                return  this.title.equals(tmp.title) &&
                        this.text.equals(tmp.text);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.title + " " +
               this.image + " " +
               this.text;
    }

}
