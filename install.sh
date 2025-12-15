#!/bin/bash

set -e

install_globally(){
    echo "Running this command: jbang alias add --name bhailang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java"
    
    # jbang alias add bhailang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java
    
    jbang alias add --name bhailang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java
    
    
    # echo "Installed bhailang globally..."
    # echo "Try opening the REPL with 'bhailang' or run a bhai script with 'bhailang script.bhai'"
    
    if [[ ":$PATH:" != *":$HOME/.jbang/bin:"* ]]; then
        echo "~/.jbang/bin is not in your PATH."
        echo "After installation, you can add it with:"
        echo "  export PATH=\"\$HOME/.jbang/bin:\$PATH\""
        echo "Then run: source ~/.bashrc or source ~/.zshrc depending on your shell."
    else
        echo "Installed BhaiLang globally! Try using 'bhailang'..."
    fi
}

uninstall_globally(){
    echo "Running this command: jbang alias remove bhailang"
    
    jbang alias remove bhailang
    rm -f ~/.jbang/bin/bhailang
    
    echo "BhaiLang alias removed."
    echo "If you manually added '~/.jbang/bin' to your PATH, remove it yourself from your shell config."
    echo "Then restart your terminal or run 'source ~/.bashrc' or 'source ~/.zshrc'."
}

run_onetime(){
    
    echo "Running this command: jbang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java"
    jbang https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java
    
}

show_help() {
    cat << EOF

JavaBhaiLang Installer Script
=============================

Usage:
  ./install.sh [OPTION]

Options:
  --install      Install 'bhailang' globally via JBang alias
  --onetime      Run 'bhailang' one time without installing
  --uninstall    Remove the global 'bhailang' alias
  --help         Show this help message

Examples:
  # Install globally
  ./install.sh --install

  # Run once without installing
  ./install.sh --onetime

  # Uninstall the global alias
  ./install.sh --uninstall

Notes:
  - Requires Java and JBang installed and in your PATH
  - After installing globally, run REPL or scripts with:
      bhailang <script.bhai>
  - Script uses latest version from:
      https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/bhailang.java

EOF
}

# Check if Java exists
if ! command -v java &>/dev/null; then
    echo "Java is not installed or not in PATH"
    exit 1
fi

# Check if Java Compiler exists
if ! command -v javac &>/dev/null; then
    echo "Javac is not installed or not in PATH"
    exit 1
fi

# Check if JBang exists
if ! command -v jbang &>/dev/null; then
    echo "JBang is not installed or not in PATH"
    exit 1
fi

while [[ $# -gt 0 ]]; do
    case $1 in
        --help)
            action="help"
        ;;
        --install)
            action="install"
        ;;
        --onetime)
            action="onetime"
        ;;
        --uninstall)
            action="uninstall"
        ;;
        *)
            action=""
            echo "Invalid command: $1"
            exit;
        ;;
    esac
    shift
done

if [ "$action" == "help" ]; then
    show_help
    exit 0
fi

if [ "$action" == "install" ]; then
    install_globally
    
    elif [ "$action" == "onetime" ]; then
    run_onetime
    
    elif [ "$action" == "uninstall" ]; then
    uninstall_globally
    
else
    echo "Invalid command. Exiting..."
fi

