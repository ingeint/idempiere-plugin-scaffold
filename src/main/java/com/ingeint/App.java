package com.ingeint;

import com.ingeint.settings.Settings;
import com.ingeint.settings.SettingsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {

    private static final String SETTINGS_PROPERTIES = "settings.properties";
    private static final String SETTINGS_JSON = "settings.json";
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            Settings settings = Settings.getInstance();
            settings.load(SETTINGS_PROPERTIES);

            SettingsReader settingsReader = new SettingsReader();
            settingsReader.load(SETTINGS_JSON);


            settings.save();
        } catch (IOException e) {
            logger.error("error starting app", e);
        }
    }
}
