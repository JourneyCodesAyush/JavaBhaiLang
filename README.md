# 🖥️ JavaBhaiLang Interpreter

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![JBang](https://img.shields.io/badge/JBang-Java%2021%2B-blue)
![Python](https://img.shields.io/badge/Python-3.10-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![Version](https://img.shields.io/github/v/tag/JourneyCodesAyush/javabhailang?label=version&color=purple)
![Status](https://img.shields.io/badge/status-feature--complete-teal)
![PRs Welcome](https://img.shields.io/badge/PRs-welcome-lightgrey)

---

<p align="center">
  <img src="assets/javaIcon.png" width="150"/>
  <!-- <img src="assets/bhailang.svg" width="120"/> -->
  <img src="assets/bhaiLangPNG.png" width="550"/>
</p>

<a href="https://www.flaticon.com/free-icons/webdevelopment" title="webdevelopment icons">Webdevelopment icons created by Alfredo Creates - Flaticon</a>

---

## 📑 Table of Contents

- [🖥️ JavaBhaiLang Interpreter](#️-javabhailang-interpreter)
  - [📑 Table of Contents](#-table-of-contents)
  - [🆕 What's New](#-whats-new)
    - [v0.10.0 Highlights](#v0100-highlights)
    - [v0.9.0-final Highlights](#v090-final-highlights)
    - [⚠️ Differences from Original BhaiLang](#️-differences-from-original-bhailang)
  - [⚡ Quick Install TL;DR](#-quick-install-tldr)
  - [🚀 Run via JBang (No Repo Clone)](#-run-via-jbang-no-repo-clone)
    - [Prerequisites](#prerequisites)
    - [Installer Scripts](#installer-scripts)
      - [Linux / macOS (Unix shells)](#linux--macos-unix-shells)
      - [Windows – PowerShell (install.ps1)](#windows--powershell-installps1)
      - [Windows – Command Prompt (install.bat)](#windows--command-prompt-installbat)
    - [Using JavaBhaiLang](#using-javabhailang)
    - [When should you clone the repository?](#when-should-you-clone-the-repository)
  - [🏃 Run Locally](#-run-locally)
    - [Steps:](#steps)
  - [📝 Examples](#-examples)
    - [Variables](#variables)
    - [Types](#types)
    - [Built-ins](#built-ins)
    - [Conditionals](#conditionals)
    - [Loops](#loops)
      - [Break \& Continue](#break--continue)
  - [📚 Language Reference](#-language-reference)
  - [⚙️ Features](#️-features)
  - [⚠️ Known Limitations](#️-known-limitations)
    - [✔️ Previously Resolved Limitations](#️-previously-resolved-limitations)
  - [📁 Project Structure](#-project-structure)
  - [🧑‍💻 Development Guide](#-development-guide)
    - [🧪 Running Tests (v0.6.1)](#-running-tests-v061)
  - [🧾 Commit Message Convention](#-commit-message-convention)
  - [🤝 Contributing](#-contributing)
  - [LICENSE](#license)
  - [📬 Author](#-author)
    - [❤️ Closure](#️-closure)

---

**JavaBhaiLang** is a minimal, fun programming language interpreter built in **Java**, inspired by _Robert Nystrom’s “[Crafting Interpreters](https://craftinginterpreters.com)”_.

> ⚡ JavaBhaiLang is now feature-complete (v0.9.0-final) - no new features planned.

This project, as of now, fully supports **[`BhaiLang`](https://bhailang.js.org)**, designed for experimentation without diving into full grammar, functions, or advanced features.

---

## 🆕 What's New

### v0.10.0 Highlights

- Numbers: `0` is falsey, all non-zero numbers are truthy.
- Booleans: `sahi` → truthy, `galat` → falsey.
- `nalla` → falsey.
- Strings: empty and non-empty strings are truthy.
- Runtime errors now report token positions if `bas kar bhai` or `agla dekh bhai` are used outside loops.
- Unit tests for truthiness, falsiness, and logical NOT operations.

### v0.9.0-final Highlights

- Parser now executes statements **between `hi bhai` and `bye bhai` only for file execution**.
- REPL executes all statements without restriction.
- REPL startup shows version number and GitHub repository link.
- **Print statements now insert implicit spaces between multiple expressions**, aligning with original BhaiLang behavior.

---

### ⚠️ Differences from Original BhaiLang

| Feature / Behavior                        | Original BhaiLang               | JavaBhaiLang (v0.9.0-final)                                                                                                         |
| ----------------------------------------- | ------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| Error reporting                           | Basic, line number may not show | Shows **line number** for easier debugging                                                                                          |
| Statements outside `hi bhai ... bye bhai` | Allowed and ignored             | **Throws an error** - Java version scans **character by character**, so extra statements beyond `hi bhai ... bye bhai` are detected |

---

_`In the grand scheme of things, Python quietly whispers, Java obeys loyally, and Bhai Lang just unleashes chaos. The true hierarchy of power is subtle.`_

```text
      +-------------------------------------+
      |   Python VM (PVM)                   |
      |   "The Unseen Puppeteer"            |
      |   Launches JVM, whispers to Java    |
      +----------------+--------------------+
                       |
                       v
      +-------------------------------------+
      |   Java Virtual Machine (JVM)        |
      |   "The Loyal Servant"               |
      |   Executes Bhai Lang Interpreter    |
      +----------------+--------------------+
                       |
                       v
      +-------------------------------------+
      |   Bhai Lang Interpreter             |
      |   "The Mouthpiece of Chaos"         |
      |   Reads and runs your Bhai Lang     |
      |   code like a true Bhai 😎          |
      +----------------+--------------------+
                       |
                       v
      +-------------------------------------+
      |   Bhai Lang Code                     |
      |   "The Soul of Madness"              |
      +-------------------------------------+

```

_TL;DR: Python whispers, Java obeys, Bhai Lang just does whatever it wants._

> ⚠️ **Note**: This is currently experimental and meant for fun and learning purposes.

> ⚠️ **Clarification About the “Hierarchy of Power” Diagram**
>
> The diagram above is meant purely for fun.  
> In reality:
>
> - **Python does not control or influence the interpreter.**
> - It only acts as a **lightweight wrapper script** to compile and run the Java code.
> - **All actual language processing** (lexing, parsing, resolving, interpreting) happens entirely in **Java**.
>
> The hierarchy graphic was included for humor, not as an architectural representation. 😄

---

## ⚡ Quick Install TL;DR

| Platform               | Quick Install Command                                                                                                                                                                                                                   |
| ---------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|                        |                                                                                                                                                                                                                                         |
| **Linux/macOS**        | `bash curl -fsSL https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/install.sh -o install.sh && chmod +x install.sh && ./install.sh --install`                                                                       |
| **Windows PowerShell** | `powershell Invoke-WebRequest "https://raw.githubusercontent.com/JourneyCodesAyush/javabhailang/main/install.ps1" -OutFile "D:\path\to\script.ps1"; Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass; .\script.ps1 --install` |
| **Windows CMD**        | `cmd curl -L https://raw.githubusercontent.com/JourneyCodesAyush/javabhailang/main/install.bat -o D:\path\to\script.bat && install.bat --install`                                                                                       |

---

## 🚀 Run via JBang (No Repo Clone)

JavaBhaiLang can be executed without cloning the repository using **JBang**.
This approach is intended for quickly trying the language or running scripts.

> ⚠️ Important
>
> - **JBang is required** for all modes
> - The `bhailang` command is a **JBang alias**, not a standalone executable
> - **Always** invoke the REPL or scripts via `jbang bhailang`
> - `--onetime` runs BhaiLang **once without creating an alias**
> - `--install` creates a **global JBang alias** for repeated use
> - An active internet connection is required (JBang fetches the latest version from GitHub)

### Prerequisites

- Java 17+ (required to run the JavaBhaiLang interpreter)
- Java 21+ (required **only** when using JBang)
- [JBang](https://www.jbang.dev/) installed and available in your PATH

### Installer Scripts

Platform-specific installer scripts are provided at the repository root.
All scripts support the same options:

- `--install` → Create a global JBang alias named `bhailang`
- `--onetime` → Run BhaiLang once without installing
- `--uninstall` → Remove the JBang alias
- `--help` → Show usage information

---

#### Linux / macOS (Unix shells)

```bash
curl -fsSL https://raw.githubusercontent.com/journeycodesayush/javabhailang/main/install.sh -o install.sh

# Give executable permission
chmod +x install.sh

# Run once
./install.sh --onetime

# Install alias
./install.sh --install

# Uninstall alias
./install.sh --uninstall
```

#### Windows – PowerShell (install.ps1)

> ⚠️ Windows Users:
> Avoid downloading the installer directly to the root `C:\` drive.
> If you choose to do so, proceed at your own risk, as some permissions may prevent script execution.
> It’s recommended to use a folder like `C:\Users\<YourName>\Downloads` or `C:\Scripts`.

```powershell
Invoke-WebRequest "https://raw.githubusercontent.com/JourneyCodesAyush/javabhailang/main/install.ps1" -OutFile "D:\path\to\script.ps1"
```

> ⚠️ PowerShell may block script execution by default.
> Run this once per terminal session:

```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```

```powershell
# Run once
.\install.ps1 --onetime

# Install alias
.\install.ps1 --install

# Uninstall alias
.\install.ps1 --uninstall
```

#### Windows – Command Prompt (install.bat)

> ⚠️ Note: Avoid saving install.bat directly in `C:\` to prevent permission issues.

```cmd
curl -L https://raw.githubusercontent.com/JourneyCodesAyush/javabhailang/main/install.bat -o D:\path\to\script.bat
```

```cmd
:: Run once
install.bat --onetime

:: Install alias
install.bat --install

:: Uninstall alias
install.bat --uninstall
```

### Using JavaBhaiLang

After installing the alias, always run BhaiLang using JBang:

```bash
jbang bhailang
jbang bhailang example.bhai
```

> ℹ️ Running `bhailang` directly will result in `command not found`.
> The alias must always be invoked through jbang.

### When should you clone the repository?

- To work offline
- To modify or extend the interpreter
- To pin a specific version

In those cases, follow the [🏃 Run Locally](#-run-locally) section below.

---

## 🏃 Run Locally

You need:

- **Java**
  - **Java 17+** → run interpreter locally
  - **Java 21+** → JBang + Maven tests
- **Python**
  - Used for the helper script (`run_bhai_lang.py`) that compiles and runs JavaBhaiLang

### Steps:

1. Clone the repo:

   ```bash
   git clone https://github.com/JourneyCodesAyush/javabhailang.git
   cd javabhailang
   ```

2. Open the REPL using the Python helper script:
   ```bash
   python run_bhai_lang.py
   ```
3. Execute a bhai lang script:
   ```bash
   python run_bhai_lang.py example.bhai
   ```

The Python script will:

- Compile all Java files in src/ to out/ directory
- Run the main JavaBhaiLang interpreter
- Clean up .class files on exit

---

## 📝 Examples

> NOTE: Semicolon `;` is **_mandatory_**

### Variables

Variables can be declared using `bhai ye hai`.

BhaiLang now also supports **complex assignment operators** (`+=`, `-=`, `*=`, `/=`).

```bhai
bhai ye hai a = 10;
bhai ye hai b = 5;

a += 3;   // Equivalent to: a = a + 3
b -= 2;   // Equivalent to: b = b - 2
a *= b;   // Equivalent to: a = a * b
b /= 3;   // Equivalent to: b = b / 3

bol bhai a, b;  // Prints the updated values
```

### Types

Numbers and Strings like other well known languages. Null values denoted by `nalla`, boolean values by `sahi` and `galat`

```

bhai ye hai string = "hello bhai";
bhai ye hai doosra_string = 'hello bhai';
bhai ye hai number = 14.0;
bhai ye hai boolean_value_true = sahi;
bhai ye hai boolean_value_false = galat;
bhai ye hai null_value = nalla;

```

### Built-ins

Use `bol bhai` to print anything to console. Now supports **multiple variables**:

```bhai
bhai ye hai a = 10;
bhai ye hai b = 20;

// Single variable
bol bhai a;

// Multiple variables
bol bhai "Values are:", a, b;
```

### Conditionals

This interpreter now supports full if–else-if–else ladders using:

- `agar bhai` → if
- `nahi to bhai` → else if
- `warna bhai` → else

```
bhai ye hai score = 75;

agar bhai (score >= 90) {
    bol bhai "Topper bhai!";
} nahi to bhai (score >= 60) {
    bol bhai "Pass hogaya bhai!";
} warna bhai {
    bol bhai "Thoda padh le bhai.";
}

```

### Loops

The statements inside `jab tak bhai` are executed until the condition is evaluated to `sahi`.
The moment condition turns `galat`, loop terminates.

```
bhai ye hai a = 0;
jab tak bhai (a < 10) {
      a = a + 1;
      agar bhai (a == 5) {
            bol bhai "andar se bol bhai 5";
      }
      agar bhai (a == 6) {
            bol bhai "andar se bol bhai 6";
      }
      bol bhai a;
}
bol bhai "done";
```

#### Break & Continue

You can control loop flow using:

- `agla dekh bhai;` → continue
- `bas kar bhai;` → break

```
bhai ye hai counter = 0;

jab tak bhai (counter < 10) {
      counter = counter + 1;

      agar bhai (counter == 3) {
            bol bhai "Skipping 3, counter:", counter;
            agla dekh bhai;
      }

      agar bhai (counter == 7) {
            bol bhai "Stop at 7, counter:", counter;
            bas kar bhai;
      }

      bol bhai counter;
}

bol bhai "Loop finished!";
```

---

## 📚 Language Reference

For the full BhaiLang language specification, see the [BhaiLang Language Docs](https://github.com/JourneyCodesAyush/TS-Bhai/blob/main/docs/language.md).

---

## ⚙️ Features

- 🖥️ Minimal JavaBhaiLang interpreter in Java
- 🎯 Focus on learning interpreter design rather than full language features
- 📝 Easy-to-extend for future grammar and functions
- 🐍 Python helper script for compiling and running code effortlessly
- 🔧 No external dependencies besides Java & Python
- ➕ Added support for loop control statements:
  - `bas kar bhai` → break
  - `agla dekh bhai` → continue

---

## ⚠️ Known Limitations

- No standard library except built-in print (`bol bhai`)
- Only single-file execution via `run_bhai_lang.py`

### ✔️ Previously Resolved Limitations

These features were missing earlier but are now fully implemented:

- Full `if–else-if–else` ladder
- Multi-variable `bol bhai`
- Complex assignment operators (`+=`, `-=`, `*=`, `/=`)
- Loop control: `bas kar bhai` (break) & `agla dekh bhai` (continue)

---

## 📁 Project Structure

```bash
JavaBhaiLang/
├── run_bhai_lang.py                  # Python helper script to compile & run
├── src/
│   ├── main/
│   │   └── java/
│   │       └── io/github/journeycodesayush/JavaBhaiLang/
│   │           ├── BhaiLang.java   # Driver code
│   │           ├── interpreter/    # Interpreter and Exception files
│   │           ├── lexer/          # Lexer, Token and TokenType
│   │           ├── parser/         # Parser, Expression and Statement
│   │           └── tool/           # Generate AST
│   └── test/
│       └── java/
│           └── io/github/journeycodesayush/JavaBhaiLang/
│               ├── TestHelper.java
│               └── InterpreterTest.java
│
│
├── LICENSE                  # MIT License
└── README.md                # You're reading it!

```

---

## 🧑‍💻 Development Guide

- Interpreter written in Java, modular design
- Extend by adding new grammar rules, statements, or features
- Use `run_bhai_lang.py` for fast testing during development
- Follow the Java conventions already in the repo
- When adding new grammar rules, statements, or built-in functions, ensure your code is modular and follows the existing patterns in:
  `src/main/java/io/github/journeycodesayush/JavaBhaiLang/`
- Use the Python helper script (`run_bhai_lang.py`) to quickly test your changes

---

### 🧪 Running Tests (v0.6.1)

> ⚠️ **Java Version Requirement**
>
> Running tests and building the project with **Maven requires Java 21 or newer**.

- A `src/test` directory has been added in **v0.6.1** to hold unit tests for the interpreter.
- Tests are written using **JUnit 5** only.
- Run tests using **Maven**:

> ⚠️ Note: Only JUnit 5 is supported for tests. Running with older JUnit versions may cause failures.

```bash
mvn test
```

---

## 🧾 Commit Message Convention

✍️ Follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

```bash
feat(<scope>): add new feature
fix(<scope>): bug fix
docs(<scope>): documentation change
```

---

## 🤝 Contributing

Want to contribute to **JavaBhaiLang**? Awesome! Here's a quick guide:

- **Fork the repo** and work on a separate branch (`feat/feature-name`, `fix/bug-name`)
- **Keep changes modular**: one feature, bug fix, or improvement per commit
- **Commit messages**: use **Angular format**: `<type>(<scope>): short description`
  - **Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
  - **Scopes:** `interpreter | lexer | parser | tool | examples | docs | tests`
- **Test your changes** using `run_bhai_lang.py`
- If adding new language features, statements, or built-in commands, follow the existing code structure in `src/main/java/io/github/journeycodesayush/JavaBhaiLang/` and test thoroughly with `run_bhai_lang.py`.
- **Open a Pull Request** with a clear description
- **Respect the Code of Conduct**: [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

---

## LICENSE

```text
MIT License

Copyright (c) 2025 JourneyCodesAyush

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

```

---

## 📬 Author

Made with &hearts; by [JourneyCodesAyush](https://github.com/journeycodesayush)

---

### ❤️ Closure

JavaBhaiLang is **feature-complete** as of the v0.9.0-final tag and will not receive new features in the near future.
This project taught me about Lexer, Parser, Interpreter, Abstract Syntax Tree (AST), Resolver, Environments, and most importantly, that **doing something consistently is where the fun lies**.

While building this, I aimed to make it as compatible with the original implementation as possible, but some stones are surely left unturned. I am **not abandoning this project** - it remains a symbol that I studied [`Crafting Interpreters`](https://craftinginterpreters.com) and actually learned from it.
This repo stands as proof that I have the **resilience to read a book and create something meaningful**.

Iska arth ye nahi ki _Bhai_ ki kahani yahi tak thi.

Yaha se _Bhai_ ke jeevan ka **`naya adhyay shuru hone ja raha hai!`**
