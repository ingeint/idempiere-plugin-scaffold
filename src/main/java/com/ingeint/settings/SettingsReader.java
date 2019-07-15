package com.ingeint.settings;

import com.google.gson.Gson;

import java.io.Console;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SettingsReader {

    private SettingsPrompt[] loadPrompts() {
        Gson gson = new Gson();

        InputStreamReader jsonFromJar = new InputStreamReader(ClassLoader.getSystemResourceAsStream(Settings.getPromptsPath()), StandardCharsets.UTF_8);
        return gson.fromJson(jsonFromJar, SettingsPrompt[].class);
    }

    public void read() {
        Console console = System.console();

        for (SettingsPrompt prompt : loadPrompts()) {
            String defaultValue = Settings.get(prompt.getKey(), prompt.getValue());
            String value = console.readLine("%s [%s]:\n", prompt.getPrompt(), defaultValue);
            Settings.set(prompt.getKey(), value.isBlank() ? defaultValue : value);
        }
    }
}
