@REM BhaiLang installer script .bat file for CMD

@echo off

:: Check for java
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo Java not found
    exit /b 1
)

:: Check for javac
where javac >nul 2>&1
if %errorlevel% neq 0 (
    echo Javac not found
    exit /b 1
)

:: Check for jbang
where jbang >nul 2>&1
if %errorlevel% neq 0 (
    echo Jbang not found
    exit /b 1
)

if "%1" == "" goto help
if "%1" == "--help" goto help
if "%1" == "--install" goto install
if "%1" == "--onetime" goto run_onetime
if "%1" == "--uninstall" goto uninstall
echo Invalid option: %1
goto :eof



:install
echo Installing bhailang globally...
echo jbang alias add --name bhailang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java

jbang alias add --name bhailang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java

goto :eof

:run_onetime
echo Running bhailang...
echo jbang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java
jbang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java
goto :eof

:uninstall
echo Uninstalling bhailang globally...
echo jbang alias remove bhailang
jbang alias remove bhailang 
goto :eof

:help
echo JavaBhaiLang Installer Script
echo =============================
echo.
echo Usage:
echo   script.bat [OPTION]
echo.
echo Options:
echo   --install      Install 'bhailang' globally
echo   --onetime      Run 'bhailang' one time
echo   --uninstall    Remove the global alias
echo   --help         Show this help message
goto :eof

