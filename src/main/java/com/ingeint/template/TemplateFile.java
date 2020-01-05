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

    private File sourceFile;
    private StringSubstitutor stringSubstitutor = new StringSubstitutor(Settings.toMap());

    public TemplateFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Path getSourcePath() {
        return Paths.get(sourceFile.getPath());
    }

    public Path getTargetPath() {
        String relativePath = Paths.get(Settings.getTemplatePluginPath())
                .relativize(Paths.get(sourceFile.getParent()))
                .toString()
                .replace(Settings.PLUGIN_SYMBOLIC_NAME, symbolicNameToPath())
                .replace(Settings.PLUGIN_ROOT, pluginRootToPath());

        return Paths.get(
                Settings.getTargetPath(),
                pluginNameToPath(),
                relativePath,
                sourceFile.getName()
        );
    }

    private String symbolicNameToPath() {
        return Settings.getPluginSymbolicName()
                .replaceAll("[^A-Za-z0-9_.]", "-")
                .replaceAll("-{2,}", "-");
    }

    private String pluginRootToPath() {
        return Settings.getPluginRoot()
                .replace(".", "/");
    }

    private String pluginNameToPath() {
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
