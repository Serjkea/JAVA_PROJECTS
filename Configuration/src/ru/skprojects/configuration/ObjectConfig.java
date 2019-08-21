package ru.skprojects.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectConfig implements Config<Object> {

    private static final String FILE_NAME= "config.cfg";
    private static final String DEFAULT_PATH = System.getProperty("user.dir");
    private String PATH;

    private Object properties;

    ObjectConfig(Object properties) {
        this.PATH = DEFAULT_PATH;
        this.properties = properties;
    }

    ObjectConfig(Object properties,String path) {
        this.PATH = path;
        this.properties = properties;
    }

    @Override
    public Object loadConfig() {
        File file = new File(PATH + FILE_NAME);
        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                this.properties = in.readObject();
                in.close();
                return this.properties;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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

    public void setDefaultProperties(Object defaultProperties) {
        this.properties = defaultProperties;
    }

    public Object getDefaultProperties() {
        return this.properties;
    }

}
