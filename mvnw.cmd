@echo off
setlocal

set WRAPPER_DIR=%~dp0.mvn\wrapper
set JAR=%WRAPPER_DIR%\maven-wrapper.jar
set PROPS=%WRAPPER_DIR%\maven-wrapper.properties

if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"

if not exist "%PROPS%" (
  >"%PROPS%" echo distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip
  >>"%PROPS%" echo wrapperUrl=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar
)

if not exist "%JAR%" (
  for /f "tokens=2 delims==" %%A in ('findstr /b wrapperUrl= "%PROPS%"') do set WRAPPER_URL=%%A
  powershell -Command "(New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%','%JAR%')" || exit /b 1
)

java -Dmaven.multiModuleProjectDirectory=%~dp0 -cp "%JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
