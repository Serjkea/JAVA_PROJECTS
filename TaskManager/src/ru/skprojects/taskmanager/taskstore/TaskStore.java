package ru.skprojects.taskmanager.taskstore;

import ru.skprojects.taskmanager.task.Task;

import java.util.List;

public interface TaskStore {

    public void addTask(String name, String description);
    public Task getTaskByName(String name);
    public Task getTaskById(int id);
    public List<Task> getAllTasks();
    public List<String> getAllTasksNames();
    public void removeTask(Task task);
    public void removeAllTasks();
    public void restoreTasks(List<Task> tasks);
    public int getSize();

}
