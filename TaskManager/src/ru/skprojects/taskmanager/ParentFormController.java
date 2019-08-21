package ru.skprojects.taskmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ParentFormController {

    private Stage stage;
    private final MainFormController mainFormController;

    public ParentFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addtaskform.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add New Task");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        stage.showAndWait();
    }

    public TextField new_task_name;
    public TextArea new_task_description;

    public void addNewTask(ActionEvent actionEvent) {
        mainFormController.taskName = new_task_name.getText();  //TODO enter checks
        mainFormController.taskDescription = new_task_description.getText();
        mainFormController.addNewTask(actionEvent);
        closeForm(actionEvent);
    }

    public void backToMainForm(ActionEvent actionEvent) {
        closeForm(actionEvent);
    }

    private void closeForm(ActionEvent actionEvent){
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
