package com.ingeint.settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Settings {

    private static Settings instance = new Settings();
    private Properties properties = new Properties();
    private File propertiesFile;

    private Settings() {
    }

    public static synchronized Settings getInstance() {
        return instance;
    }

    public synchronized void load(String path) throws IOException {
        propertiesFile = new File(path);

        if (!propertiesFile.exists()) {
            Files.copy(ClassLoader.getSystemResourceAsStream(path), propertiesFile.toPath());
        }

        properties.load(new FileReader(propertiesFile));
    }

    public synchronized void set(String key, String value) {
        if (value == null) {
            properties.remove(key);
        } else {
            properties.put(key, value);
        }
    }

    public synchronized String get(String key, String defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public synchronized String get(String key) {
        if (key == null) {
            return null;
        }
        return properties.getProperty(key);
    }

    @Override
    public String toString() {
        return toMap().toString();
    }

    public synchronized Map<String, String> toMap() {
        return properties.stringPropertyNames()
                .stream()
                .collect(Collectors.toMap(key -> key, key -> get(key)));
    }

    public synchronized void save() throws IOException {
        properties.store(new FileWriter(propertiesFile), null);
    }
}
