import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import static java.util.stream.Collectors.toList;

public class TargetPlatformBundleTagger {

    public static final Path BUNDLES_PATH = Paths.get("target").toAbsolutePath().normalize();

    public static void main(String[] args) throws IOException {
        createBundlesTarget();
        copyJars(convertArgsToPaths(args));
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

                    Path newJar = file.toPath().getParent().resolve(jarName);
                    Path newJarInTarget = BUNDLES_PATH.resolve(jarName);

                    Files.copy(file.toPath(), newJar, StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(file.toPath(), newJarInTarget, StandardCopyOption.REPLACE_EXISTING);

                    System.out.printf("Output bundle: %s\n", newJarInTarget.getParent().getParent().getParent().relativize(newJarInTarget));
                }
            }
        }
    }

    private static void createBundlesTarget() {
        BUNDLES_PATH.toFile().mkdirs();
    }

    private static List<Path> convertArgsToPaths(String[] args) {
        return Arrays.stream(args).map(s -> Paths.get(s).toAbsolutePath()).collect(toList());
    }

}