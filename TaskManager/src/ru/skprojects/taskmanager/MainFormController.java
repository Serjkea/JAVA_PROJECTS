package ru.skprojects.taskmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.skprojects.configuration.Config;
import ru.skprojects.configuration.ConfigFactory;
import ru.skprojects.taskmanager.backup.Backup;
import ru.skprojects.taskmanager.task.Task;
import ru.skprojects.taskmanager.taskstore.TaskStore;
import ru.skprojects.taskmanager.taskstore.TaskStoreImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainFormController {

    private final Stage stage;

    public MainFormController() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("taskmanagerform.fxml"));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Task Manager");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        stage.showAndWait();
    }

    private static final String BACKUP_FILENAME = "tasks";
    private static final String LOGGER_FILENAME = "taskmanager";
    private static final String PREFIX_PATHNAME = System.getProperty("user.dir") + "/";
    private static final String SUFFIX_DATANAME = ".dat";
    private static final String SUFFIX_PRNTNAME = ".txt";

    private static Backup backup = new Backup();

    private static Properties properties = new Properties();

    static {
        properties.put("show_completed", false);
        properties.put("show_active", true);
        properties.put("backup", false);
        properties.put("reminder", false);
        properties.put("saved_config", false);
    }

    private static Config<Properties> config = ConfigFactory.getPropertiesConfig(properties,PREFIX_PATHNAME);

    private static TaskStore store = TaskStoreImpl.getTaskStore();

    public TextArea description;
    public CheckBox active_tasks;
    public CheckBox completed_tasks;
    public ListView<Task> task_list;

    public String taskName;
    public String taskDescription;

    public void initialize() {
        properties = config.loadConfig();
        if (Boolean.parseBoolean(properties.getProperty("backup"))) {
            backup.loadFromFile(store,PREFIX_PATHNAME+BACKUP_FILENAME+SUFFIX_DATANAME);
        }
        active_tasks.setSelected(Boolean.parseBoolean(properties.getProperty("show_active")));
        completed_tasks.setSelected(Boolean.parseBoolean(properties.getProperty("show_completed")));
        task_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        task_list.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!task_list.getItems().isEmpty()) {
                    description.clear();
                    description.appendText(task_list.getSelectionModel().getSelectedItem().getDescription());
                }
            }
        });
//        checkChanges();
    }

    public void performTask(ActionEvent actionEvent) {
        store.getTaskByName(task_list.getSelectionModel().getSelectedItem().getName()).isCompleted = true;
        store.getTaskByName(task_list.getSelectionModel().getSelectedItem().getName()).isActive = false;
        checkChanges();
    }

    public void deleteTask(ActionEvent actionEvent) {
        store.removeTask(task_list.getSelectionModel().getSelectedItem());
        checkChanges();
    }

    public void deleteAllTasks(ActionEvent actionEvent) {
        store.removeAllTasks();
        checkChanges();
    }

    public void addNewTask(ActionEvent actionEvent) {
        store.addTask(taskName, taskDescription);
        checkChanges();
        closeForm(actionEvent);
    }

    public void showChange(ActionEvent actionEvent) {
        checkChanges();
    }

    public void closeTaskManager(ActionEvent actionEvent) {
        backup.saveToFile(store,PREFIX_PATHNAME+BACKUP_FILENAME+SUFFIX_DATANAME);
        properties.setProperty("show_active", String.valueOf(active_tasks.isSelected()));
        properties.setProperty("show_completed", String.valueOf(completed_tasks.isSelected()));
        properties.setProperty("backup", String.valueOf(true));
        properties.setProperty("reminder", String.valueOf(false));
        properties.setProperty("saved_config", String.valueOf(true));
        config.saveConfig();
        closeForm(actionEvent);
    }

    public void backToMainForm(ActionEvent actionEvent) {
        closeForm(actionEvent);
    }

    public void showAddNewTaskForm(ActionEvent actionEvent) {
        ParentFormController parentFormController = new ParentFormController(this);
        parentFormController.showStage();
    }

    private void closeForm(ActionEvent actionEvent){
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void checkChanges() {
        ObservableList<Task> observable = null;
        List<Task> tasks;
        if (!store.getAllTasks().isEmpty()) {
            if (active_tasks.isSelected() && !completed_tasks.isSelected()) {
                tasks = new ArrayList<Task>();
                for (Task task : store.getAllTasks()) {
                    if (task.isActive) tasks.add(task);
                }
                observable = FXCollections.observableList(tasks);
            } else if (!active_tasks.isSelected() && completed_tasks.isSelected()) {
                tasks = new ArrayList<Task>();
                for (Task task : store.getAllTasks()) {
                    if (task.isCompleted) tasks.add(task);
                }
                observable = FXCollections.observableList(tasks);
            } else if (active_tasks.isSelected() && completed_tasks.isSelected()){
                observable = FXCollections.observableList(store.getAllTasks());;
            }
        }
        if (task_list == null) {
            task_list = new ListView<Task>(observable);
        } else {
            task_list.setItems(observable);
        }
        description.clear();
        task_list.refresh();
    }

}
