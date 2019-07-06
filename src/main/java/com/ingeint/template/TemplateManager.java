package com.ingeint.template;

import com.ingeint.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TemplateManager {

    private static final Logger logger = LoggerFactory.getLogger(TemplateManager.class);

    public void createTemplate(String path) throws IOException {
        copyChildFiles(new File(path));
    }

    private void copyChildFiles(File folder) throws IOException {
        List<File> files = Arrays.asList(folder.listFiles());

        for (File file : files) {
            if (file.isDirectory()) {
                copyChildFiles(file);
            } else {
                Path sourceFilePath = Paths.get(file.getPath());
                Path targetFilePath = Paths.get(Settings.getExportPath(), file.getPath());

                File folderTargetFile = targetFilePath.toFile().getParentFile();
                logger.info("creating folder {}", file.getParent());
                folderTargetFile.mkdirs();

                logger.debug("copying {} to {}", sourceFilePath, targetFilePath);
                Files.copy(sourceFilePath, targetFilePath);
            }
        }
    }
}
