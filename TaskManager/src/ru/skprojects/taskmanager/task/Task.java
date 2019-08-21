package ru.skprojects.taskmanager.task;

import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;

public class Task implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Date date;
    private int id;

    public boolean isActive;
    public boolean isCompleted;

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        date = new Date();
        isActive = true;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void complete() {
        isActive = false;
        isCompleted = true;
    }

    public void activate() {
        isActive = true;
        isCompleted = false;
    }

    public void disactivate() {
        isActive = false;
        isCompleted = false;
    }

    @Override
    public String toString() {
//        Formatter formatter = new Formatter();
//        formatter.format("id: %d\nname: %s\n%s\n%s\nactive: %s\ncompleted: %s", id, name, description, date, isActive, isCompleted);
//        return formatter.toString();
          return getName();
    }

    @Override
    public int hashCode() {
        return 31*name.hashCode() +
               31*description.hashCode() +
               31*id +
               31*date.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj instanceof Task) {
            Task temp = (Task) obj;
            if (temp.name.equals(this.name)
                    && temp.description.equals(this.description)
                    && (temp.id == this.id) && temp.date.equals(this.date))
                return true;
        }
        return false;
    }

    public static class Builder {

        private String name;
        private String description;
        private int id;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Task build() {
            return new Task(name, description, id);
        }

    }
}
