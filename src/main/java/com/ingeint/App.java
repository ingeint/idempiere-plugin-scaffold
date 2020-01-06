package com.ingeint;

import com.ingeint.idempiere.IdempierePaths;
import com.ingeint.settings.Settings;
import com.ingeint.settings.SettingsReader;
import com.ingeint.template.TemplateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            Settings.load();

            IdempierePaths.load();

            SettingsReader settingsReader = new SettingsReader();
            settingsReader.read();

            IdempierePaths.updateRelativePath();

            TemplateManager templateManager = new TemplateManager();
            templateManager.createPlugin();

            Settings.save();

            logger.info("Plugin created in: " + Paths.get(Settings.getTargetPath()).toAbsolutePath().normalize());
        } catch (IOException e) {
            logger.error("Error starting app", e);
        }
    }
}
