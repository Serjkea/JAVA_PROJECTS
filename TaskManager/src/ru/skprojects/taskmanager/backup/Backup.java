package ru.skprojects.taskmanager.backup;

import ru.skprojects.taskmanager.MainFormController;
import ru.skprojects.taskmanager.task.Task;
import ru.skprojects.taskmanager.taskstore.TaskStore;

import java.io.*;
import java.util.List;

public class Backup {

    public void loadFromFile(TaskStore store, String path) {
        if (store.getSize() != 0) store.removeAllTasks();
        File file = new File(path);
        try{
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                try {
                    store.restoreTasks((List<Task>) ois.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ois.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile(TaskStore store, String path) {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(store.getAllTasks());
            oos.close();
            if(store.getSize()!=0) {
                System.out.println("Tasks was saved");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}
