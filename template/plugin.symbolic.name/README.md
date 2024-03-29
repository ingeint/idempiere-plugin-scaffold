# ${plugin.name}

## Standard

- New callout
    * Name: CName
    * Package: ${plugin.root}.callout
    * Example: ${plugin.root}.callout.CStringFormat

- New process
    * Name: PName
    * Package: ${plugin.root}.process
    * Example: ${plugin.root}.process.PGenerateWithholding

- New form
    * Name: FName
    * Package: ${plugin.root}.form
    * Example: ${plugin.root}.form.FMultiPayment

- New event
    * Name: EName
    * Package: ${plugin.root}.event
    * Example: ${plugin.root}.event.EAfterCompleteInvoice

- New model (extends class X)
    * Name: MName
    * Package: ${plugin.root}.model
    * Example: ${plugin.root}.model.MTableExample

## Folder estructure

```
    ${plugin.symbolic.name}
        |_.settings
        |   |_org.eclipse.core.resources.prefs
        |   |_org.eclipse.jdt.core.prefs
        |   |_org.eclipse.m2e.core.prefs
        |   |_org.eclipse.pde.core.prefs
        |_.classpath
        |_.project
        |_.gitignore
        |_build.properties
        |_LICENSE
        |_README.md
        |_pom.xml
        |_META-INF
        |   |_MANIFEST.MF
        |   |_2Pack_X.X.X.zip
        |_OSGI-INF
        |   |_CalloutFactory.xml
        |   |_EventFactory.xml
        |   |_FormFactory.xml
        |   |_ModelFactory.xml
        |   |_ProcessFactory.xml
        |_src
            |_${plugin.root}
                |_base (plugin core)
                |   |_BundleInfo.java (gets plugin information dynamically)
                |   |_CustomCallout.java (IColumnCallout implementation)
                |   |_CustomEventFactory.java (AbstractEventHandler implementation)
                |   |_CustomEvent.java (for event implementation)
                |   |_CustomForm.java (IFormController implementation)
                |   |_CustomProcess.java (SvrProcess implementation)
                |_component (plugin's components)
                |   |_CalloutFactory.java (registers callout classes automatically )
                |   |_EventFactory.java (registers event handler classes automatically)
                |   |_FormFactory.java (registers form classes automatically)
                |   |_ProcessFactory.java (registers process classes automatically)
                |   |_ModelFactory.java (registers model classes automatically)
                |_util
                |   |_TimestampUtil.java
                |   |_SqlBuilder.java
                |   |_FileTemplateBuilder.java
                |   |_KeyValueLogger.java
                |_callout (new callouts, extends CustomCallout)
                |_event (new events, extends CustomEvent)
                |_form (new forms, extends CustomForm)
                |_process (new processes, extends CustomProcess)
                |_model (autogenerated models)
```

## How it works

### Components

- New callout
    * Create callout in package `${plugin.root}.callout`, extends from `CustomCallout`
    * Annotate it with the `@Callout` annotation at class level

- New process
    * Create process in package `${plugin.root}.process`, extends from `CustomProcess`
    * Annotate it with the `@Process` annotation at class level

- New form
    * Create form in package `${plugin.root}.form`, extends from `CustomForm`
    * Annotate it with the `@Form` annotation at class level

- New event
    * Create event in package `${plugin.root}.event`, extends from `CustomEvent`
    * Annotate it with the `@EventTopicDelegate` annotation at class level

- New model (extends form class X)
    * Create model in package `${plugin.root}.model`, extends class `X`. Example: `X_TL_TableExample -> MTableExample`
    * Annotate it with the `@Model` annotation at class level
    * More information
        * https://wiki.idempiere.org/en/Developing_Plug-Ins_-_IModelFactory
        * https://wiki.idempiere.org/en/Developing_iDempiere_4:_Create_a_new_class_model_with_window_and_tabs#Model_generator


### Utils

- `FileTemplateBuilder`: Creates complex text file using [freemarker](https://freemarker.apache.org/), example:

```java
FileTemplateBuilder.builder()
    .file("invoice-template.xml")
    .inject("invoice", new Invoice())
    .export("invoice.xml")
```

The `invoice-template.xml` file:

```xml
<invoice>
    <name>${invoice.name}</name>
    <id>${invoice.id}</id>
    <lines>
        <#list invoice.invoiceLines as line>
        <line>
            <product name="${line.product}" price="${line.price}"/>
        </line>
        </#list>
    </lines>
</invoice>
```

- `KeyValueLogger`: Use CLogger to log information with a key/value format, example:

```java
KeyValueLogger keyValueLogger = KeyValueLogger.instance(App.class);
keyValueLogger.message("Hello World!!").info();
```

The output:

```bash
20:34:24.270 ELogLoginInfo.log: message="Hello World!!"
```

Othes output examples:

```css
19:50:16.044 OpenTransactionInterceptor.log: dateTime="2020-02-12 19:50:16.039 -0500" httpMethod="POST" client="11" language="es_CO" endpoint="/api/auth/login" transaction="Trx_e1dcd314-a508-44c1-9a0c-d34d4caacb2b" [33]
19:50:16.157 CloseTransactionInterceptor.log: dateTime="2020-02-12 19:50:16.156 -0500" httpStatus="200" endpoint="/api/auth/login" transaction="Trx_e1dcd314-a508-44c1-9a0c-d34d4caacb2b" [33]
```

- `SqlBuilder`: It is a wrapper for StringBuilder, allows you to create sql from files, example:

```java
String sql = SqlBuilder.builder().file("read-bpartner.sql").build()
```

The `read-bpartner.sql` file:

```sql
-- This query reads the partners using as filter "is vendor"
select name
from c_bpartner
where isvendor = ?
```

- `TimestampUtil`: to create timestamp objects, example:

```java
Timestamp currentTime = TimestampUtil.now();
```

### Adding a new library

Add the new dependency (`artifacItem`) to the [pom.xml](pom.xml) file in the `artifactItems` attribute, example:

```xml
    <artifactItems>
        <artifactItem>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>28.0-jre</version>
        </artifactItem>
    </artifactItems>
```

Then, add a new classpath entry in the [.classpath](.classpath) file, example:
```xml
    <classpathentry kind="lib" path="lib/guava.jar"/>
```

Verify you are including the folder `lib` in the [build.properties](build.properties) file, exaple:

```properties
bin.includes = .,\
               META-INF/,\
               OSGI-INF/,\
               lib/
```

Finally, add the new dependency in de [MANIFEST.MF](META-INF/MANIFEST.MF) file as a `Bundle-ClassPath` attribute, example:

```manifest
Bundle-ClassPath: .,
 lib/guava.jar
```

---

> Plugin skeleton generated by https://github.com/ingeint/idempiere-plugin-scaffold
