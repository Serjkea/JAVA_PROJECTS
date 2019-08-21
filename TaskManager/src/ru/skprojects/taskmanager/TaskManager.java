package ru.skprojects.taskmanager;

import javafx.application.Application;
import javafx.stage.Stage;

public class TaskManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainFormController mainFormController = new MainFormController();
        mainFormController.showStage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
