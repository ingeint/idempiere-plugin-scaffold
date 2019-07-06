package com.ingeint;

import com.ingeint.settings.Settings;
import com.ingeint.settings.SettingsReader;
import com.ingeint.template.TemplateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final String SETTINGS_PROPERTIES_PATH = "settings.properties";
    private static final String SETTINGS_JSON_PATH = "settings.json";
    private static final String TEMPLATE_PATH = "template";

    public static void main(String[] args) {
        try {
            Settings.load(SETTINGS_PROPERTIES_PATH);

            SettingsReader settingsReader = new SettingsReader();
            settingsReader.load(SETTINGS_JSON_PATH);

            TemplateManager templateManager = new TemplateManager();
            templateManager.createTemplate(TEMPLATE_PATH);

            Settings.save();
        } catch (IOException e) {
            logger.error("error starting app", e);
        }
    }
}
