import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import static java.util.stream.Collectors.toList;

public class TargetPlatformPluginTagger {

    private static final Path PLUGINS_TARGET_PATH = Paths.get("target").toAbsolutePath();
    private static final Path PLUGIN_FILE_LIST = Paths.get("plugins.txt").toAbsolutePath();

    public static void main(String[] args) throws IOException {
        createBundlesTarget();
        copyJars(getPluginsPath(args));
    }

    private static void copyJars(List<Path> paths) throws IOException {
        for (Path path : paths) {
            Path targetPath = path.resolve("target").toAbsolutePath().normalize();
            File targetFolder = targetPath.toFile();
            for (File file : targetFolder.listFiles()) {
                if (file.isFile() && file.getName().endsWith("SNAPSHOT.jar")) {
                    JarInputStream jarStream = new JarInputStream(new FileInputStream(file));
                    Manifest manifest = jarStream.getManifest();

                    String symbolicName = manifest.getMainAttributes().getValue("Bundle-SymbolicName");
                    String version = manifest.getMainAttributes().getValue("Bundle-Version");
                    String jarName = String.format("%s-%s.jar", symbolicName, version);

                    Path newJar = file.toPath().getParent().resolve(jarName).toAbsolutePath();
                    Path newJarInTarget = PLUGINS_TARGET_PATH.resolve(jarName).toAbsolutePath();

                    Files.copy(file.toPath(), newJar, StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(file.toPath(), newJarInTarget, StandardCopyOption.REPLACE_EXISTING);

                    System.out.printf("Output plugin: %s\n", newJarInTarget);
                }
            }
        }
    }

    private static void createBundlesTarget() {
        PLUGINS_TARGET_PATH.toFile().mkdirs();
    }

    private static List<Path> getPluginsPath(String[] args) throws IOException {
        List<String> stringPaths = Arrays.asList(args);

        if (stringPaths.isEmpty() && PLUGIN_FILE_LIST.toFile().exists()) {
            stringPaths = Files.readAllLines(PLUGIN_FILE_LIST, StandardCharsets.UTF_8);
        }

        return stringPaths.stream().map(s -> Paths.get(s).toAbsolutePath()).collect(toList());
    }

}