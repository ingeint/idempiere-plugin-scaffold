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

    private static final String EXPORT_PATH = "export.path";
    private static Settings instance = new Settings();
    private Properties properties = new Properties();
    private File propertiesFile;

    private Settings() {
    }

    public static Settings getInstance() {
        return instance;
    }

    public static String getExportPath() {
        return getInstance().get(EXPORT_PATH);
    }

    public void load(String path) throws IOException {
        propertiesFile = new File(path);

        if (!propertiesFile.exists()) {
            Files.copy(ClassLoader.getSystemResourceAsStream(path), propertiesFile.toPath());
        }

        properties.load(new FileReader(propertiesFile));
    }

    public void set(String key, String value) {
        if (value == null) {
            properties.remove(key);
        } else {
            properties.put(key, value);
        }
    }

    public String get(String key, String defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public String get(String key) {
        if (key == null) {
            return null;
        }
        return properties.getProperty(key);
    }

    @Override
    public String toString() {
        return toMap().toString();
    }

    public Map<String, String> toMap() {
        return properties.stringPropertyNames()
                .stream()
                .collect(Collectors.toMap(key -> key, key -> get(key)));
    }

    public void save() throws IOException {
        properties.store(new FileWriter(propertiesFile), null);
    }
}
