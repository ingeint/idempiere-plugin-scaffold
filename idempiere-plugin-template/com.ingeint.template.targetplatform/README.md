# iDempiere Plugin Template - Target Platform

A target platform is necessary to build a iDempiere plugin. This is a smart target platform. Current iDempiere Version `8.1`.

## Prerequisites

- Java 11, commands `java` and `javac`.
- Maven 3.6.0
- Set `IDEMPIERE_REPOSITORY` env variable.

## Getting started

- Compile target platform:

```bash
./plugin-builder
```

You could create a file `plugins.txt` with all plugin's paths on newlines, example:

```
/plugin-path-1
/plugin-path-2
```

- Compiling target platform and plugins:

```bash
./plugin-builder /plugin-path-1 /plugin-path-2
```

- Using parameter `debug` for debug mode example:

```bash
./plugin-builder debug /plugin-path-1 /plugin-path-2
```

Use `.\plugin-builder.bat` for windows.

- Set the current hash commit as qualifier (just for linux) `commit` parameter (it'll need a $GIT_COMMIT env variable):

```bash
./plugin-builder commit
```

- Set build number as qualifier (just for linux, usually on jenkins) `build` parameter (it'll need a $BUILD_NUMBER env variable)::

```bash
./plugin-builder build
```

## How does it work?

This script will generate automatically the files to compile any plugin:

-  `./pom.xml`
- `com.ingeint.template.p2.targetplatform/com.ingeint.template.p2.targetplatform.target`
- `com.ingeint.template.p2.targetplatform/pom.xml`

For more information about the plugin structure go [here](https://github.com/globalqss/globalqss-idempiere-lco).
