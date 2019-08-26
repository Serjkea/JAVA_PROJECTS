package ru.skprojects.chat.client;

import java.io.IOException;
import java.net.Socket;

public interface ChatClient {

    public void setChatClientController(ChatClientController chatClientController);
    public void sendMessage(String message);
    public void getMessage(String message);
    public void startChat();
    public void stopChat();
    public boolean logIn(String login, String password, String rPassword);

}
