package com.ingeint.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class TemplateManager {

    private static final Logger logger = LoggerFactory.getLogger(TemplateManager.class);


    public void createTemplate(String path) throws IOException {
        copyChildFiles(new File(path));
    }

    private void copyChildFiles(File folder) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                copyChildFiles(file);
            } else {
                copyAndFillFile(new TemplateFile(file));
            }
        }
    }

    private void copyAndFillFile(TemplateFile templateFile) throws IOException {
        logger.info("Creating {}", templateFile.getTargetPath());
        templateFile.writeTarget();
    }
}
