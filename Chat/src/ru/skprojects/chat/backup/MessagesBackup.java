package ru.skprojects.chat.backup;

import ru.skprojects.chat.server.User;

import java.io.*;
import java.util.ArrayList;

public class MessagesBackup implements Backup<ArrayList<String>> {

    private File file;

    public MessagesBackup(String PATH) {
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
    public void save(ArrayList<String> messages) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))){
            for (String msg : messages) {
                outputStream.writeObject(msg);
                outputStream.flush();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<String> load() {
        ArrayList<String> loadedMessages = null;
        FileInputStream fis = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(fis = new FileInputStream(file))){
            while (fis.available() > 0) {
                loadedMessages.add((String)inputStream.readObject());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return loadedMessages;
    }
}
