package ru.skprojects.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig implements Config<Properties> {

    private static final String FILE_NAME= "config.properties";
    private static final String DEFAULT_PATH = System.getProperty("user.dir");
    private String PATH;

    private Properties properties;

    PropertiesConfig(Properties properties) {
        this.PATH = DEFAULT_PATH;
        this.setDefaultProperties(properties);
    }

    PropertiesConfig(Properties properties,String path) {
        this.PATH = path;
        this.setDefaultProperties(properties);
    }

    @Override
    public Properties loadConfig() {
        File file = new File(PATH + FILE_NAME);
        if (file.exists()) {
            try {
                this.properties.load(new FileInputStream(file));
                return this.properties;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
            this.properties.store(new FileOutputStream(file), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultProperties(Properties defaultProperties) {
        this.properties = defaultProperties;
    }

    public Properties getDefaultProperties() {
        return properties;
    }

}
