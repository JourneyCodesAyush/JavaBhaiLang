# BhaiLang installer script for PowerShell 7+

function Install-Bhailang {
    Write-Host "Running this command: jbang alias add --name bhailang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java"
    jbang alias add --name bhailang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java
    Write-Host "Use BhaiLang with 'jbang bhailang [file-name]'"
    
}

function Uninstall-Bhailang {
    Write-Host "Running this command: jbang alias remove bhailang"
    
    jbang alias remove bhailang
    
    Write-Host "Uninstalled bhailang globally..."
}

function Invoke-Bhailang-Onetime {
    Write-Host "Running this command: jbang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java"
    jbang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java
    
}

function Show-Help {
    Write-Host "Usage:"
    Write-Host "  .\bhailang.ps1 [OPTION]"
    Write-Host "Options:"
    Write-Host "  --install      Install BhaiLang globally"
    Write-Host "  --onetime      Run BhaiLang one time"
    Write-Host "  --uninstall    Remove the global alias"
    Write-Host "  --help         Show this help message"
}

function CommandExists {
    param ([string]$cmd)
    return [bool](Get-Command $cmd -ErrorAction SilentlyContinue)
}


if ( -not (CommandExists "java")) {
    Write-Error "Java is not installed or not in PATH"
    exit 1
}

if ( -not (CommandExists "javac")) {
    Write-Error "Javac is not installed or not in PATH"
    exit 1
}

if ( -not (CommandExists "jbang")) {
    Write-Error "JBang is not installed or not in PATH"
    exit 1
}

if ($args.Count -eq 0) {
    Show-Help
    exit 1
}

$option = $args[0].ToString().ToLower()

switch ($option) {
    "--install" { Install-Bhailang }
    "--uninstall" { Uninstall-Bhailang }
    "--onetime" { Invoke-Bhailang-Onetime }
    "--help" { Show-Help }

    Default {
        Write-Host "Invalid option: $option"
        Show-Help
        # Write-Host "Use --help for more info"
        exit 1
    }
}