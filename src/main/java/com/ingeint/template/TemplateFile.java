package com.ingeint.template;

import com.ingeint.settings.Settings;
import org.apache.commons.text.StringSubstitutor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateFile {
    private static final String SRC_PATH = "src";
    private static final String INGEINT_PATH = "com/ingeint";

    private File sourceFile;
    private StringSubstitutor stringSubstitutor = new StringSubstitutor(Settings.toMap());

    public TemplateFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Path getSourcePath() {
        return Paths.get(sourceFile.getPath());
    }

    public Path getTargetPath() {
        String relativePath = Paths.get(Settings.getSourcePath()).relativize(Paths.get(sourceFile.getParent())).toString();

        if (relativePath.startsWith(SRC_PATH)) {
            relativePath = relativePath.replace(INGEINT_PATH, pluginRootToStringPath());
        }

        return Paths.get(
                Settings.getTargetPath(),
                Settings.getPluginSymbolicName(),
                relativePath,
                sourceFile.getName()
        );
    }

    private String pluginRootToStringPath() {
        return Settings.getPluginRoot()
                .replace(".", "/");
    }

    private String getPluginNamePath() {
        return Settings.getPluginName()
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("-{2,}", "-");
    }

    public void write() throws IOException {
        getTargetFolder().mkdirs();
        Files.writeString(getTargetPath(), loadAndFillRawFile(), StandardCharsets.UTF_8);
    }

    private String loadAndFillRawFile() throws IOException {
        String currentFile = Files.readString(getSourcePath(), StandardCharsets.UTF_8);
        return stringSubstitutor.replace(currentFile);
    }

    private File getTargetFolder() {
        return getTargetPath().toFile().getParentFile();
    }
}
