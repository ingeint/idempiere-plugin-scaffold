package com.ingeint.settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public final class Settings {

    public static final String TARGET_PATH = "target.path";
    public static final String PLUGIN_NAME = "plugin.name";

    private static Properties properties = new Properties();
    private static File propertiesFile;

    public static String getTargetPath() {
        return get(TARGET_PATH);
    }

    public static String getPluginName() {
        return get(PLUGIN_NAME);
    }

    public static void load(String path) throws IOException {
        propertiesFile = new File(path);

        if (!propertiesFile.exists()) {
            Files.copy(ClassLoader.getSystemResourceAsStream(path), propertiesFile.toPath());
        }

        properties.load(new FileReader(propertiesFile));
    }

    public static void set(String key, String value) {
        if (value == null) {
            properties.remove(key);
        } else {
            properties.put(key, value);
        }
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static String get(String key) {
        if (key == null) {
            return null;
        }
        return properties.getProperty(key);
    }

    public static Map<String, String> toMap() {
        return properties.stringPropertyNames()
                .stream()
                .collect(Collectors.toMap(key -> key, key -> get(key)));
    }

    public static void save() throws IOException {
        properties.store(new FileWriter(propertiesFile), null);
    }
}
