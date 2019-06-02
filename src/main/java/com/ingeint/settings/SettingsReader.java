package com.ingeint.settings;

import com.google.gson.Gson;

import java.io.Console;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class SettingsReader {
    private Console console;
    private Settings settings;
    private List<SettingsPrompt> prompts;

    public SettingsReader() {
        prompts = readJson();
        console = System.console();
        settings = Settings.getInstance();
    }

    private List<SettingsPrompt> readJson() {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream("settings.json"), StandardCharsets.UTF_8),
                SettingsPrompt[].class));
    }

    public void readProperties() {
        prompts.forEach(prompt -> {
            String value = console.readLine("%s [%s]: ", prompt.getPrompt(), prompt.getValue());
            settings.set(prompt.getKey(), value.isBlank() ? prompt.getValue() : value);
        });
    }

}
