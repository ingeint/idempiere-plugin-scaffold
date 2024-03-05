package com.ingeint.idempiere;

import com.ingeint.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class IdempierePathsTest {

    private Path currentParentPath;
    private Path currentPath;

    @BeforeEach
    void setUp() {
        currentParentPath = Paths.get("").toAbsolutePath().getParent();
        currentPath = Paths.get("").toAbsolutePath();
    }

    @Test
    void shouldGetAbsolutePathWhenSettingsIsRelative() {
        Settings.set(Settings.IDEMPIERE_PATH, "../idempiere");
        assertThat(IdempierePaths.getAbsolutePath())
                .isEqualTo(currentParentPath.resolve("idempiere"));
        assertThat(IdempierePaths.getRelativePath(currentPath))
                .isEqualTo(Paths.get("../idempiere"));
    }

    @Test
    void shouldGetRelativePath() {
        Settings.set(Settings.IDEMPIERE_PATH, currentParentPath.resolve("idempiere").toString());
        assertThat(IdempierePaths.getRelativePath(currentPath))
                .isEqualTo(Paths.get("../idempiere"));
    }

    @Test
    void shouldUpdateSettingsRelativePath() {
        Settings.set(Settings.IDEMPIERE_PATH, currentParentPath.resolve("idempiere").toString());
        Settings.set(Settings.PLUGIN_SYMBOLIC_NAME, "com.ingeint.example");
        Settings.set(Settings.PLUGIN_NAME, "Test Name");
        Settings.set(Settings.TARGET_PATH, ".");
        Settings.set(Settings.PLUGIN_ROOT, "com.ingeint");

        Settings.updatePaths();
        IdempierePaths.updateRelativePath();

        assertThat(Settings.getPluginIdempiereRelativePath()).isEqualTo("../../../idempiere");
    }
}
