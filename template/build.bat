@echo off

set DEBUG_MODE=

if "%1" == "debug" (
  set DEBUG_MODE=debug
)

cd ${plugin.symbolic.name}.targetplatform
call .\plugin-builder.bat %DEBUG_MODE% ..\${plugin.symbolic.name} ..\${plugin.symbolic.name}.test
cd ..
