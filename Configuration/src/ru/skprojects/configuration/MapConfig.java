package ru.skprojects.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class MapConfig implements Config<HashMap<String, String>>{

    private static final String FILE_NAME= "config.cfg";
    private static final String DEFAULT_PATH = System.getProperty("user.dir");
    private String PATH;

    private HashMap<String, String> properties;

    MapConfig(HashMap<String, String> properties) {
        this.PATH = DEFAULT_PATH;
        this.properties = properties;
    }

    MapConfig(HashMap<String, String> properties,String path) {
        this.PATH = path;
        this.properties = properties;
    }

    @Override
    public HashMap<String, String> loadConfig() {
        File file = new File(PATH + FILE_NAME);
        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                Object tmp = in.readObject();
                in.close();
                if(tmp instanceof HashMap<?,?>) {
                    this.properties = (HashMap<String,String>) tmp;
                } else {
                    throw new ClassCastException();
                }
                return this.properties;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        } return this.properties;
    }

    @Override
    public void saveConfig() {
        File file = new File(PATH + FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this.properties);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultProperties(HashMap<String,String> defaultProperties) {
        this.properties = defaultProperties;
    }

    public HashMap<String,String> getDefaultProperties() {
        return this.properties;
    }


}
