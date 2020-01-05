package com.ingeint.idempiere;

import com.ingeint.settings.Settings;

import java.nio.file.Path;
import java.nio.file.Paths;

public class IdempierePaths {

    public static final String IDEMPIERE_REPOSITORY = "IDEMPIERE_REPOSITORY";

    public static void load() {
        String idempiereRepository = Settings.get(Settings.IDEMPIERE_PATH, System.getenv(IDEMPIERE_REPOSITORY));
        if (idempiereRepository != null) {
            Settings.set(Settings.IDEMPIERE_PATH, idempiereRepository);
        }
    }

    public static Path getAbsolutePath() {
        return Paths.get(Settings.getIdempierePath()).toAbsolutePath().normalize();
    }

    public static void updateRelativePath() {
        Path pluginPath = Paths.get(Settings.getTargetPath())
                .resolve(Settings.getPluginNameAsPath())
                .resolve(Settings.getPluginSymbolicName())
                .toAbsolutePath()
                .normalize();

        Settings.set(Settings.PLUGIN_IDEMPIERE_RELATIVE_PATH, getRelativePath(pluginPath).toString());
    }

    public static Path getRelativePath(Path path) {
        return path.relativize(getAbsolutePath());
    }
}
