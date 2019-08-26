package ru.skprojects.chat.server;

public class ChatServerMain {

    public static void main(String[] args) {
        ChatServerController chatServerController = new ChatServerControllerImpl();
        chatServerController.startServer();
    }

}
