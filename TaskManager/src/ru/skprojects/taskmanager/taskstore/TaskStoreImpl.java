package ru.skprojects.taskmanager.taskstore;

import ru.skprojects.taskmanager.task.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TaskStoreImpl implements TaskStore,Iterable<Task> {

    private static TaskStore store;

    private static int id = 0;
    private List<Task> tasks;

    private TaskStoreImpl() {
    }

    public static TaskStore getTaskStore() {
        if(store == null) store = new TaskStoreImpl();
        return store;
    }

    @Override
    public void addTask(String name, String description) {
        if(tasks == null) tasks = new LinkedList<Task>();
        tasks.add(new Task.Builder()
                .setName(name)
                .setDescription(description)
                .setId(++id)
                .build()
        );
    }

    @Override
    public Task getTaskByName(String name) {
        if(!tasks.isEmpty() && tasks != null) {
            for(Task task: tasks) {
                if(task.getName().equals(name)) return task;
            }
        }
        return null;
    }

    @Override
    public Task getTaskById(int id) {
        if(!tasks.isEmpty() && tasks != null) {
            for(Task task: tasks) {
                if(task.getId() == id) return task;
            }
        }
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks;
    }

    @Override
    public List<String> getAllTasksNames() {
        List<String> taskNames;
        if (tasks.isEmpty())  {
            return null;
        }
        else {
            taskNames = new ArrayList<String>();
            for(Task task: tasks) {
                taskNames.add(task.getName());
            }
            return taskNames;
        }
    }


    @Override
    public void removeTask(Task task) {
        if(tasks.contains(task))
            tasks.remove(task);
    }

    @Override
    public void removeAllTasks() {
        if(!tasks.isEmpty() && tasks != null)
            tasks.clear();
    }

    @Override
    public void restoreTasks(List<Task> tasks) {
        if (tasks != null ) {
            this.tasks = tasks;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Task task : tasks) {
            sb.append(task+"\n\n");
        }
        return sb.toString();
    }

    private class TaskStoreIterator  implements Iterator<Task>  {

        private int pointer = 0;
        private boolean isEmpty = (tasks.size() == 0);

        @Override
        public boolean hasNext() {
            return pointer < tasks.size();
        }

        @Override
        public Task next() {
            if(hasNext()) return tasks.get(pointer++);
            else {
                isEmpty = true;
                return null;
            }
        }

        @Override
        public void remove() {
            if(!isEmpty) tasks.remove(pointer--);
        }

    }

    public int getSize() {
        if (tasks==null) return 0;
        return tasks.size();
    }

    @Override
    public Iterator<Task> iterator() {
        return new TaskStoreIterator();
    }

}
