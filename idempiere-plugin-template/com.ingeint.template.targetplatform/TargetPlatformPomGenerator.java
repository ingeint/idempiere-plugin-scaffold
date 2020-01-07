import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class TargetPlatformPomGenerator {

    private static final String IDEMPIERE_REPOSITORY_ENV = "IDEMPIERE_REPOSITORY";

    static {
        if (System.getenv(IDEMPIERE_REPOSITORY_ENV) == null) {
            System.err.printf("Error: set the '%s' env variable first\n", IDEMPIERE_REPOSITORY_ENV);
            System.exit(1);
        }
    }

    private static final Path CURRENT_PATH = Paths.get("").toAbsolutePath();

    private static final Path TARGET_PLATFORM_TEMPLATE_PATH = CURRENT_PATH.resolve("template/target-platform-template.xml").toAbsolutePath();
    private static final Path TARGET_PLATFORM_PATH = CURRENT_PATH.resolve("com.ingeint.template.p2.targetplatform/com.ingeint.template.p2.targetplatform.target").toAbsolutePath();

    private static final Path TARGET_PLATFORM_POM_TEMPLATE_PATH = CURRENT_PATH.resolve("template/target-platform-pom-template.xml").toAbsolutePath();
    private static final Path TARGET_PLATFORM_POM_PATH = CURRENT_PATH.resolve("com.ingeint.template.p2.targetplatform/pom.xml").toAbsolutePath();

    private static final Path MAIN_POM_TEMPLATE_PATH = CURRENT_PATH.resolve("template/main-pom-template.xml").toAbsolutePath();
    private static final Path MAIN_POM_PATH = CURRENT_PATH.resolve("pom.xml").toAbsolutePath();

    private static final Path IDEMPIERE_REPOSITORY_PATH = Paths.get(System.getenv(IDEMPIERE_REPOSITORY_ENV)).toAbsolutePath();
    private static final Path IDEMPIERE_P2_PATH = IDEMPIERE_REPOSITORY_PATH.resolve("org.idempiere.p2/target/repository").toAbsolutePath();
    private static final Path IDEMPIERE_PARENT_POM_PATH = IDEMPIERE_REPOSITORY_PATH.resolve("org.idempiere.parent/pom.xml").toAbsolutePath();

    private static final Path PLUGIN_FILE_LIST = Paths.get("plugins.txt").toAbsolutePath();

    public static void main(String[] args) throws IOException {
        writeTargetPlatform();
        writeTargetPlatformPom();

        List<Path> pluginPaths = getPluginsPath(args);

        updatePluginPom(pluginPaths);
        writeMainPom(pluginPaths);
    }

    private static List<Path> getPluginsPath(String[] args) throws IOException {
        List<String> stringPaths = Arrays.asList(args);

        if (stringPaths.isEmpty() && PLUGIN_FILE_LIST.toFile().exists()) {
            stringPaths = Files.readAllLines(PLUGIN_FILE_LIST, StandardCharsets.UTF_8);
            System.out.printf("Reading plugins path from file: %s\n", PLUGIN_FILE_LIST);
        }

        return stringPaths.stream().map(s -> Paths.get(s).toAbsolutePath()).collect(toList());
    }

    private static void writeTargetPlatform() throws IOException {
        String template = readFile(TARGET_PLATFORM_TEMPLATE_PATH);
        String newPom = template.replace("${TPidempiereP2}", pathToString(IDEMPIERE_P2_PATH));
        writeFile(TARGET_PLATFORM_PATH, newPom);
    }

    private static void writeTargetPlatformPom() throws IOException {
        Path idempiereParentRelativePath = CURRENT_PATH.resolve("com.ingeint.template.p2.targetplatform").relativize(IDEMPIERE_PARENT_POM_PATH);

        String pomTemplate = readFile(TARGET_PLATFORM_POM_TEMPLATE_PATH);
        String newPom = pomTemplate.replace("${TPidempiereParent}", pathToString(idempiereParentRelativePath));
        writeFile(TARGET_PLATFORM_POM_PATH, newPom);
    }

    private static void updatePluginPom(List<Path> pluginPaths) throws IOException {
        for (Path path : pluginPaths) {
            Path pomPath = path.resolve("pom.xml").toAbsolutePath();
            Path idempiereParentRelativePath = path.relativize(IDEMPIERE_PARENT_POM_PATH);
            String replacement = String.format("<relativePath>%s</relativePath>", pathToString(idempiereParentRelativePath));
            String dependencyPom = readFile(pomPath);
            String newDependencyPom = dependencyPom.replaceFirst("<relativePath>.*org.idempiere.parent.*</relativePath>", replacement);
            writeFile(pomPath, newDependencyPom);
        }
    }

    private static void writeMainPom(List<Path> pluginPaths) throws IOException {
        String dependencies = pluginPaths.stream().map(CURRENT_PATH::relativize)
                .map(path -> String.format("<module>%s</module>", pathToString(path)))
                .collect(joining());

        String pomTemplate = readFile(MAIN_POM_TEMPLATE_PATH);
        String newPom = pomTemplate.replace("${TPdependencies}", dependencies);
        writeFile(MAIN_POM_PATH, newPom);
    }

    private static String readFile(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static void writeFile(Path path, String content) throws IOException {
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    private static String pathToString(Path path) {
        String stringPath = path.toString();
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            stringPath = stringPath.replace("\\", "/");
        }
        return stringPath;
    }

}
