# ${plugin.name}

- Copyright: ${year} ${plugin.vendor}
- Repository: ${plugin.repository}
- License: GPL 2

## Description

Put the plugin description here

## Contributors

- Put the contributor list here, format: Year Name <name@email.com>.

## Components

- iDempiere Plugin [${plugin.symbolic.name}](${plugin.symbolic.name})
- iDempiere Unit Test Fragment [${plugin.symbolic.name}.test](${plugin.symbolic.name}.test)
- iDempiere Target Platform [${plugin.symbolic.name}.targetplatform](${plugin.symbolic.name}.targetplatform)

## Prerequisites

- Java 11, commands `java` and `javac`.
- iDempiere ${idempiere.version}
- Set `IDEMPIERE_REPOSITORY` env variable

## Features/Documentation

- Put the plugin feature list here

## Instructions

- Put the instructions list to install here

## Extra Links

- Put the documentation/links here

## Commands

Compile plugin and run tests:

```bash
./build
```

Use the parameter `debug` for debug mode example:

```bash
./build debug
```

To use `.\build.bat` for windows.
