package com.ingeint;

import com.ingeint.settings.SettingsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            AppSettings appSettings = AppSettings.getInstance();
            appSettings.load("settings.properties");

            SettingsReader settingsReader = new SettingsReader();
            settingsReader.load("settings.json");
            settingsReader.readProperties();

            appSettings.save();
            settingsReader.save();
        } catch (IOException e) {
            logger.error("error starting app", e);
        }
    }
}
