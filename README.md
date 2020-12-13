# Quick reference

- **Maintained by**:  
  [INGEINT](https://ingeint.com)

- **Where to file issues**:  
  [Mattermost Support Channel](https://mattermost.idempiere.org/idempiere/channels/support) or [Github Issues](https://github.com/ingeint/idempiere-plugin-scaffold/issues)

- **Official Links**:  
  [Dockerhub](https://hub.docker.com/r/idempiereofficial/idempiere),
  [Github](https://github.com/ingeint/idempiere-plugin-scaffold),
  [iDempiere](https://github.com/idempiere/idempiere)

# iDempiere Plugin Generator

This project creates an iDempiere plugin skeleton. Current iDempiere Version `8.1`.

## Examples

- Whole plugin example in [idempiere-plugin-template](idempiere-plugin-template).
- iDempiere Plugin Example [idempiere-plugin-template/com.ingeint.template](idempiere-plugin-template/com.ingeint.template)
- iDempiere Unit Test Plugin Example [idempiere-plugin-template/com.ingeint.template.test](idempiere-plugin-template/com.ingeint.template.test)
- iDempiere Target Platform Plugin Example [idempiere-plugin-template/com.ingeint.template.targetplatform](idempiere-plugin-template/com.ingeint.template.targetplatform)

## Prerequisites

- Java 11, commands `java` and `javac`.
- Set `IDEMPIERE_REPOSITORY` env variable

## Commands

 | Command | Description |
 | - | - |
 | `./plugin-scaffold` | Creates a new plugin |
 | `./plugin-scaffold clean` | Restart the scaffold configuration |

To use `.\plugin-scaffold.bat` for windows.

# Plugin Documentation

## Components

The new plugin will have three components:

- The New iDempiere Plugin
- iDempiere Unit Test Fragment
- iDempiere Target Platform

And a `build.sh` script to compile it.

## Prerequisites

- Java 11, commands `java` and `javac`.
- Set `IDEMPIERE_REPOSITORY` env variable

## Commands

Compile plugin and run tests:

```bash
./build
```

Use the parameter `debug` for debug mode example:

```bash
./build debug
```

Use `.\build.bat` for windows.

# iDempiere Plugin Target Platform

A target platform is necessary to **compile** a iDempiere plugin. This is a smart target platform. Current iDempiere Version `8.1`.

The scaffold is going to provide you a target platform and a way to compile it in your new plugin.

For more information go to [https://github.com/ingeint/idempiere-target-platform-plugin](https://github.com/ingeint/idempiere-target-platform-plugin)

# iDempiere Plugin Deployer

This tool allows you connect to iDempiere's OSGI platform and deploy a plugin, it's useful for continuous integration platforms.

For more information go to [https://github.com/ingeint/idempiere-plugin-deployer](https://github.com/ingeint/idempiere-plugin-deployer)
