package com.ingeint.settings;

import com.google.gson.Gson;

import java.io.Console;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class SettingsReader {
    private List<SettingsPrompt> prompts;

    public void load(String path) {
        readDefaultValues(path);
        readPropertiesFromUser();
    }

    private void readDefaultValues(String path) {
        Gson gson = new Gson();
        prompts = Arrays.asList(gson.fromJson(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream(path), StandardCharsets.UTF_8),
                SettingsPrompt[].class));
    }

    private void readPropertiesFromUser() {
        Console console = System.console();
        Settings settings = Settings.getInstance();
        prompts.forEach(prompt -> {
            String defaultValue = settings.get(prompt.getKey(), prompt.getValue());
            String value = console.readLine("%s [%s]: ", prompt.getPrompt(), defaultValue);
            settings.set(prompt.getKey(), value.isBlank() ? defaultValue : value);
        });
    }
}
