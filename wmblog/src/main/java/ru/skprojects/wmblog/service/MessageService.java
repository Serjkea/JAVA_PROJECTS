package ru.skprojects.wmblog.service;

import ru.skprojects.wmblog.model.Message;

import java.util.List;

public interface MessageService {

    void addNewMessage(Message message);
    List<Message> getAllMessages();
    Message getMessageById(Integer id);
}
