package com.ingeint.settings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public final class Settings {

    public static final String TARGET_PATH = "target.path";
    public static final String PLUGIN_NAME = "plugin.name";
    public static final String PLUGIN_NAME_AS_PATH = "plugin.name.as.path";
    public static final String SETTINGS_PROPERTIES_PATH = "settings.properties";
    public static final String PROMPTS_PATH = "prompts.path";
    public static final String TEMPLATE_PLUGIN_PATH = "template.plugin.path";
    public static final String PLUGIN_ROOT = "plugin.root";
    public static final String PLUGIN_ROOT_AS_PATH = "plugin.root.as.path";
    public static final String PLUGIN_SYMBOLIC_NAME = "plugin.symbolic.name";
    public static final String PLUGIN_SYMBOLIC_NAME_AS_PATH = "plugin.symbolic.name.as.path";
    public static final String YEAR = "year";
    public static final String IDEMPIERE_PATH = "idempiere.path";
    public static final String PLUGIN_IDEMPIERE_RELATIVE_PATH = "plugin.idempiere.relative.path";

    private static Properties properties = new Properties();

    static {
        set(YEAR, Year.now().toString());
    }

    public static String getPluginRoot() {
        return get(PLUGIN_ROOT);
    }

    public static String getPromptsPath() {
        return get(PROMPTS_PATH);
    }

    public static String getTemplatePluginPath() {
        return get(TEMPLATE_PLUGIN_PATH);
    }

    public static String getTargetPath() {
        return get(TARGET_PATH);
    }

    public static String getPluginName() {
        return get(PLUGIN_NAME);
    }

    public static String getPluginSymbolicName() {
        return get(PLUGIN_SYMBOLIC_NAME);
    }

    public static String getPluginIdempiereRelativePath() {
        return get(PLUGIN_IDEMPIERE_RELATIVE_PATH);
    }

    public static String getIdempierePath() {
        return get(IDEMPIERE_PATH);
    }

    public static void load() throws IOException {
        properties.load(new InputStreamReader(ClassLoader.getSystemResourceAsStream(SETTINGS_PROPERTIES_PATH), StandardCharsets.UTF_8));
        if (Files.exists(Paths.get(SETTINGS_PROPERTIES_PATH))) {
            properties.load(new FileReader(SETTINGS_PROPERTIES_PATH, StandardCharsets.UTF_8));
        }
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
        properties.store(new FileWriter(SETTINGS_PROPERTIES_PATH), null);
    }

    public static String getPluginNameAsPath() {
        return get(PLUGIN_NAME_AS_PATH);
    }

    public static String getSymbolicNameAsPath() {
        return get(PLUGIN_SYMBOLIC_NAME_AS_PATH);
    }

    private static String nameToPath(String string) {
        return string.toLowerCase()
                .replaceAll("[^a-z0-9_.]", "-")
                .replaceAll("-{2,}", "-");
    }

    public static void updatePaths() {
        set(PLUGIN_NAME_AS_PATH, nameToPath(getPluginName()));
        set(PLUGIN_SYMBOLIC_NAME_AS_PATH, nameToPath(getPluginSymbolicName()));
        set(PLUGIN_ROOT_AS_PATH, packageToPath(getPluginRoot()));
    }

    private static String packageToPath(String packageName) {
        return packageName.toLowerCase()
                .replaceAll("[^a-z0-9]", "."
                ).replaceAll("\\.{2,}", ".")
                .replace(".", "/");
    }

    public static String getPluginRootAsPath() {
        return get(PLUGIN_ROOT_AS_PATH);
    }
}
