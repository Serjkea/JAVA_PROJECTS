package ru.skprojects.chat.client;

import com.sun.nio.sctp.SendFailedNotification;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatLoginController {

    private final Stage stage;

    public ChatLoginController() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatloginform.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Login");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void showForm() {
        stage.showAndWait();
    }

    public void hideForm() {
        stage.hide();
    }

    private ChatClient chatClient;

    private Boolean isLogin;

    public Button btnOK;
    public TextField loginField;
    public TextField passwordField;
    public TextField repeatPasswordField;
    public Label loginText;

    public void initialize() {
        chatClient = new ChatClientImpl(this);
        repeatPasswordField.setVisible(true);
        loginText.setVisible(true);
    }

    public void onButtonClick(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText(); //TODO enter checks
        String rPassword = repeatPasswordField.getText();
        System.out.println(chatClient);
        if ((login != null)&&(password!=null)) {
            isLogin = this.chatClient.logIn(login, password, rPassword);
            System.out.println("login " + isLogin);
        }
        if (isLogin) {
            System.out.println("next");
            this.chatClient.setChatClientController(new ChatClientController(this.chatClient));
            hideForm();
        }
    }
}
