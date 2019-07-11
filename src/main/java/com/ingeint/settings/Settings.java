package com.ingeint.settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Year;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public final class Settings {

    public static final String TARGET_PATH = "target.path";
    public static final String PLUGIN_NAME = "plugin.name";
    public static final String SETTINGS_PROPERTIES_PATH = "settings.properties";
    public static final String PROMPTS_PATH = "prompts.path";
    public static final String SOURCE_PATH = "source.path";
    public static final String PLUGIN_ROOT = "plugin.root";

    private static Properties properties = new Properties();
    private static File propertiesFile;

    static {
        set("year", Year.now().toString());
    }

    public static String getPluginRoot() {
        return get(PLUGIN_ROOT);
    }

    public static String getPromptsPath() {
        return get(PROMPTS_PATH);
    }

    public static String getSourcePath() {
        return get(SOURCE_PATH);
    }

    public static String getTargetPath() {
        return get(TARGET_PATH);
    }

    public static String getPluginName() {
        return get(PLUGIN_NAME);
    }

    public static void load() throws IOException {
        propertiesFile = new File(SETTINGS_PROPERTIES_PATH);

        if (!propertiesFile.exists()) {
            Files.copy(ClassLoader.getSystemResourceAsStream(SETTINGS_PROPERTIES_PATH), propertiesFile.toPath());
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
