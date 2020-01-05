package com.ingeint.template;

import com.ingeint.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class TemplateManager {

    private static final Logger logger = LoggerFactory.getLogger(TemplateManager.class);

    public void createPlugin() throws IOException {
        copyChildren(new File(Settings.getTemplatePluginPath()));
    }

    private void copyChildren(File folder) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                copyChildren(file);
            } else {
                copyChild(new TemplateFile(file));
            }
        }
    }

    private void copyChild(TemplateFile templateFile) throws IOException {
        logger.info("Creating {}", templateFile.getTargetPath());
        templateFile.write();
    }
}
