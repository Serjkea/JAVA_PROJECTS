package ru.skprojects.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatClientController {

    private final Stage stage;

    public ChatClientController(ChatClient chatClient) {
        this.chatClient = chatClient;
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatclientform.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Chat");
            showForm();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void showForm() {
        stage.showAndWait();
    }

    private ChatClient chatClient;

    public Button btnSend;
    public Button btnClose;
    public TextField sendText;
    public TextArea chatText;

    public void initialize() {
        chatClient.startChat();
    }

    public void sendMessage(ActionEvent actionEvent) {
        chatClient.sendMessage(chatText.getText()); //TODO check entered text
    }

    public void closeChat(ActionEvent actionEvent) {
        chatClient.stopChat();
        stage.hide();
    }
}
