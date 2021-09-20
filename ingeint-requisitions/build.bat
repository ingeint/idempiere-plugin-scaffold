@echo off

set DEBUG_MODE=

if "%1" == "debug" (
  set DEBUG_MODE=debug
)

cd com.ingeint.requisitions.targetplatform
call .\plugin-builder.bat %DEBUG_MODE% ..\com.ingeint.requisitions ..\com.ingeint.requisitions.test
cd ..
