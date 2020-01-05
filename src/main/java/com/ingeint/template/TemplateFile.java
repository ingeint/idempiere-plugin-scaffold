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
                .replace(Settings.PLUGIN_SYMBOLIC_NAME, Settings.getSymbolicNameAsPath())
                .replace(Settings.PLUGIN_ROOT, pluginRootToPath());

        return Paths.get(
                Settings.getTargetPath(),
                Settings.getPluginNameAsPath(),
                relativePath,
                sourceFile.getName()
        );
    }

    private String pluginRootToPath() {
        return Settings.getPluginRoot()
                .replace(".", "/");
    }

    public void write() throws IOException {
        getTargetFolder().mkdirs();
        Files.writeString(getTargetPath(), loadAndFillRawFile(), StandardCharsets.UTF_8);
        getTargetPath().toFile().setExecutable(true);
    }

    private String loadAndFillRawFile() throws IOException {
        String currentFile = Files.readString(getSourcePath(), StandardCharsets.UTF_8);
        return stringSubstitutor.replace(currentFile);
    }

    private File getTargetFolder() {
        return getTargetPath().toFile().getParentFile();
    }
}
