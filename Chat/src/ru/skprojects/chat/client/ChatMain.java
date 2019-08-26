package ru.skprojects.chat.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ChatMain extends Application {

    @Override
    public void start(Stage stage) {
        ChatLoginController chatLoginController = new ChatLoginController();
        chatLoginController.showForm();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
