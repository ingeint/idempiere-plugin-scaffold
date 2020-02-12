set DEBUG_MODE=

set PLUGINS_PATH=%*

if "%1" == "debug" (
  set DEBUG_MODE=-X
  call set PLUGINS_PATH=%%PLUGINS_PATH:*%1=%%
)

javac TargetPlatformPomGenerator.java
java TargetPlatformPomGenerator %PLUGINS_PATH%
call mvn validate -Didempiere.target=com.ingeint.template.p2.targetplatform %DEBUG_MODE%
call mvn verify -Didempiere.target=com.ingeint.template.p2.targetplatform %DEBUG_MODE%
javac TargetPlatformPluginTagger.java
java TargetPlatformPluginTagger %PLUGINS_PATH%
