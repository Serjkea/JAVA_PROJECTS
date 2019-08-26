package ru.skprojects.chat.backup;

import ru.skprojects.chat.server.User;

import java.io.*;
import java.util.ArrayList;

public class UsersBackup implements Backup<ArrayList<User>> {

    private File file;

    public UsersBackup(String PATH) {
        file = new File(PATH);
        if (!file.exists())  {
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(ArrayList<User> users) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))){
            for (User u : users) {
                outputStream.writeObject(u);
                outputStream.flush();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<User> load() {
        ArrayList<User> loadedUsers = null;
        FileInputStream fis = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(fis = new FileInputStream(file))){
            while (fis.available() > 0) {
                loadedUsers.add((User)inputStream.readObject());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return loadedUsers;
    }
}
