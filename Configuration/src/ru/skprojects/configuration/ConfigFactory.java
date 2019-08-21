package ru.skprojects.configuration;

import java.util.HashMap;
import java.util.Properties;

public class ConfigFactory {

    public static Config<Properties> getPropertiesConfig(Properties properties, String path) {
        return new PropertiesConfig(properties, path);
    }

    public static Config<Properties> getPropertiesConfig(Properties properties) {
        return new PropertiesConfig(properties);
    }

    public static Config<HashMap<String,String>> getMapConfig(HashMap<String,String> properties, String path) {
        return new MapConfig(properties, path);
    }

    public static Config<HashMap<String,String>> getMapConfig(HashMap<String,String> properties) {
        return new MapConfig(properties);
    }

    public static Config<Object> getConfig(Object properties, String path) {
        return new ObjectConfig(properties, path);
    }

    public static Config<Object> getConfig(Object properties) {
        return new ObjectConfig(properties);
    }

}
