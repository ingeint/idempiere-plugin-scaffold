package com.ingeint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ingeint.settings.Settings;
import com.ingeint.settings.SettingsReader;

import java.io.IOException;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            Settings settings = Settings.getInstance();
            settings.load("settings.properties");
            logger.debug("starting app with settings: {}", settings);
            
            SettingsReader settingsReader = new SettingsReader();
            settingsReader.readProperties();
        } catch (IOException e) {
            logger.error("error starting app", e);
        }
    }
}
