package ru.skprojects.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface ChatServerController {

    public void startServer();
    public void stopServer();
    public void sendMessage(String message);
    public void restoreMessages(ClientService client);
    public void removeClient(ClientService client);
    public String login(BufferedWriter ocs, BufferedReader ics);

}
