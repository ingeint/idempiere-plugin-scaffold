package com.ingeint.settings;

import com.google.gson.Gson;

import java.io.Console;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SettingsReader {

    private SettingsPrompt[] loadPrompts(String path) {
        Gson gson = new Gson();

        InputStreamReader jsonFromJar = new InputStreamReader(ClassLoader.getSystemResourceAsStream(path), StandardCharsets.UTF_8);
        return gson.fromJson(jsonFromJar, SettingsPrompt[].class);
    }

    public void read(String path) {
        Console console = System.console();

        for (SettingsPrompt prompt : loadPrompts(path)) {
            String defaultValue = Settings.get(prompt.getKey(), prompt.getValue());
            String value = console.readLine("%s [%s]: ", prompt.getPrompt(), defaultValue);
            Settings.set(prompt.getKey(), value.isBlank() ? defaultValue : value);
        }
    }
}
