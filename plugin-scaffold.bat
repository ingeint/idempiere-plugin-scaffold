@echo off
call .\gradlew.bat -q clean installDist
call .\build\install\idempiere-plugin-generator\bin\idempiere-plugin-generator.bat
