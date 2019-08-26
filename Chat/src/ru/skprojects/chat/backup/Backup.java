package ru.skprojects.chat.backup;

import java.io.Serializable;

public interface Backup<T extends Serializable> {

    public void save(T t);
    public T load();

}
