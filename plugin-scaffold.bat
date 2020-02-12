@echo off

if "%1" == "clean" (
  call .\gradlew.bat -q clean
  del settings.properties
) else (
  call .\gradlew.bat -q clean installDist
  call .\build\install\idempiere-plugin-generator\bin\idempiere-plugin-generator.bat
)
