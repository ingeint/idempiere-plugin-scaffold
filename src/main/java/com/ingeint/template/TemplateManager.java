package com.ingeint.template;

import com.ingeint.settings.Settings;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateManager {

    private static final Logger logger = LoggerFactory.getLogger(TemplateManager.class);

    private StringSubstitutor stringSubstitutor = new StringSubstitutor(Settings.toMap());

    public void createTemplate(String path) throws IOException {
        copyChildFiles(new File(path));
    }

    private void copyChildFiles(File folder) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                copyChildFiles(file);
            } else {
                copyAndFillFile(file);
            }
        }
    }

    private void copyAndFillFile(File file) throws IOException {
        Path sourceFilePath = Paths.get(file.getPath());
        Path targetFilePath = Paths.get(Settings.getExportPath(), file.getPath());

        File targetFolder = targetFilePath.toFile().getParentFile();
        logger.debug("creating folder '{}'", targetFolder);
        targetFolder.mkdirs();

        logger.debug("copying '{}' to '{}'", sourceFilePath, targetFilePath);
        String currentFile = Files.readString(sourceFilePath);
        String updateFile = stringSubstitutor.replace(currentFile);
        Files.writeString(targetFilePath, updateFile);
    }
}
