# 🖥️ JavaBhaiLang Interpreter

![Java](https://img.shields.io/badge/Java-17-orange)
![Python](https://img.shields.io/badge/Python-3.10-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![Version](https://img.shields.io/github/v/tag/JourneyCodesAyush/javabhailang?label=version&color=purple&sort=semver)
![Status](https://img.shields.io/badge/status-active-brightgreen)
![PRs Welcome](https://img.shields.io/badge/PRs-welcome-blue)

---

## 📑 Table of Contents

- [🖥️ JavaBhaiLang Interpreter](#️-javabhailang-interpreter)
  - [📑 Table of Contents](#-table-of-contents)
  - [🆕 What's New](#-whats-new)
    - [v0.4.1](#v041)
  - [🏃 Run Locally](#-run-locally)
    - [Steps:](#steps)
  - [📝 Examples](#-examples)
    - [Variables](#variables)
    - [Types](#types)
    - [Built-ins](#built-ins)
    - [Conditionals](#conditionals)
    - [Loops](#loops)
      - [Break \& Continue](#break--continue)
  - [⚙️ Features](#️-features)
  - [⚠️ Known Limitations](#️-known-limitations)
  - [📁 Project Structure](#-project-structure)
  - [🧑‍💻 Development Guide](#-development-guide)
  - [🧾 Commit Message Convention](#-commit-message-convention)
  - [🤝 Contributing](#-contributing)
  - [LICENSE](#license)
  - [📬 Author](#-author)

---

**JavaBhaiLang** is a minimal, fun programming language interpreter built in **Java**, inspired by _Robert Nystrom’s “[Crafting Interpreters](https://craftinginterpreters.com)”_.

This project, as of now, is a **subset of JavaBhaiLang**, designed for experimentation without diving into full grammar, functions, or advanced features (yet). Future updates may include full JavaBhaiLang support and user-defined functions.

---

## 🆕 What's New

### v0.4.1

- **Lexer (`Scanner.java`)**: Correctly handle `+` and `+=` operators.
  - Ensures a `+` token is added when not followed by `=`, instead of mistakenly using `-`.
- **Interpreter** and **Parser**: Refactored switch-case syntax to modern Java 14+ arrow syntax for cleaner code.

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

---

## 🏃 Run Locally

You need:

- **Java** (any recent version, 2-3 years old also works)
- **Python** (for the helper script that compiles and runs JavaBhaiLang)

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

bol bhai a, b;  # Prints the updated values
```

### Types

Numbers and Strings like other well known languages. Null values denoted by `nalla`, boolean values by `sahi` and `galat`

```

bhai ye hai string = "hello bhai";
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

# Single variable
bol bhai a;

# Multiple variables
bol bhai "Values are:", a, b;
```

### Conditionals

This interpreter supports `agar bhai` and `warna bhai` as if-else ladder.
Does not support `if-else-if` ladder as of now though the syntax is defined.

```
bhai ye hai a = 10;
agar bhai (a < 20) {
      bol bhai "a is less than 20";
} warna bhai {
      bol bhai "a is greater than or equal to 25";
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

- `if-else-if` ladder is partially implemented
- No standard library except built-in print (`bol bhai`)
- Multi-variable `bol bhai` is now supported
- Complex assignment operators are now supported (`+=`, `-=`, `*=`, `/=`)
- Only single-file execution via `run_bhai_lang.py`
- `bas kar bhai` (break) and `agla dekh bhai` (continue) are now supported

---

## 📁 Project Structure

```bash
JavaBhaiLang/
├── run_bhai_lang.py                  # Python helper script to compile & run
├── src/
│   └── main/
│       └── java/
│           └── io/github/journeycodesayush/JavaBhaiLang/
│               ├── BhaiLang.java   # Driver code
│               ├── interpreter/    # Interpreter and Exception files
│               ├── lexer/          # Lexer, Token and TokenType
│               ├── parser/         # Parser, Expression and Statement
│               └── tool/           # Generate AST
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
