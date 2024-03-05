# Quick reference

- **Maintained by**:  
  [INGEINT](https://ingeint.com)

- **Where to file issues**:  
  [Mattermost Support Channel](https://mattermost.idempiere.org/idempiere/channels/support) or [Github Issues](https://github.com/ingeint/idempiere-plugin-scaffold/issues)

- **Official Links**:  
  [Dockerhub](https://hub.docker.com/r/idempiereofficial/idempiere),
  [Github](https://github.com/ingeint/idempiere-plugin-scaffold),
  [iDempiere](https://github.com/idempiere/idempiere)

## iDempiere Plugin Generator

This project creates an iDempiere plugin skeleton. Current iDempiere Version `11.0`.

### Examples

You can find and an examples at:

- iDempiere Plugin Example [example-plugin/com.ingeint.template](example-plugin/com.ingeint.template)
- iDempiere Unit Test Plugin Example [example-plugin/com.ingeint.template.test](example-plugin/com.ingeint.template.test)

### Prerequisites

- Java 17, commands `java` and `javac`.
- Set `IDEMPIERE_REPOSITORY` env variable.

### Scaffold Commands

| Command | Description |
| --- | --- |
| `./plugin-scaffold` | Creates a new plugin |
| `./plugin-scaffold clean` | Restart the scaffold configuration |

To use `.\plugin-scaffold.bat` for windows.

## Plugin Documentation

### Components

The new plugin will have the next components:

- The New iDempiere Plugin
- iDempiere Unit Test Fragment

### Plugin Prerequisites

- Java 17, commands `java` and `javac`.
- Set `IDEMPIERE_REPOSITORY` env variable.
- Clone target platform:

  ```shell
  git clone --branch 11.0 https://github.com/ingeint/idempiere-target-platform-plugin.git
  ```

### Commands

Compile plugin and run tests:

```bash
git clone --branch 11.0 https://github.com/ingeint/idempiere-target-platform-plugin.git target-platform
cd target-platform
./plugin-builder ../example-plugin/com.ingeint.template \
                 ../example-plugin/com.ingeint.template.test
```

> `./plugin-builder <plugin path>`

Use the parameter `debug` for debug mode example:

```bash
./plugin-builder debug <path to plugin>
```

> Use `.\build.bat` for windows.

## iDempiere Plugin Target Platform

A target platform is necessary to **compile** an iDempiere plugin.

For more information about how to build a plugin go to [https://github.com/ingeint/idempiere-target-platform-plugin](https://github.com/ingeint/idempiere-target-platform-plugin)

## iDempiere Plugin Deployer

This tool allows you connect to iDempiere's OSGI platform and deploy a plugin, it's useful for continuous integration platforms.

For more information go to [https://github.com/ingeint/idempiere-plugin-deployer](https://github.com/ingeint/idempiere-plugin-deployer)
