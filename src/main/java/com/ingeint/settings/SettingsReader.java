package com.ingeint.settings;

import com.google.gson.Gson;
import com.ingeint.AppSettings;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class SettingsReader {
    private Console console;
    private AppSettings appSettings;
    private List<SettingsPrompt> prompts;
    private Gson gson = new Gson();
    private File jsonFile;

    public SettingsReader() {
        console = System.console();
        appSettings = AppSettings.getInstance();
    }

    public void load(String path) throws IOException {
        jsonFile = new File(path);

        if (!jsonFile.exists()) {
            Files.copy(ClassLoader.getSystemResourceAsStream(path), jsonFile.toPath());
        }

        prompts = Arrays.asList(gson.fromJson(new FileReader(jsonFile), SettingsPrompt[].class));
    }

    public void readProperties() {
        prompts.forEach(prompt -> {
            String value = console.readLine("%s [%s]: ", prompt.getPrompt(), prompt.getValue());
            appSettings.set(prompt.getKey(), value.isBlank() ? prompt.getValue() : value);
        });
    }

    public void save() throws IOException {
        gson.toJson(prompts, new FileWriter(jsonFile));
    }
}
