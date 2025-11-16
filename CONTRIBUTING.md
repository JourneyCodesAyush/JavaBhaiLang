# Contributing to JavaBhaiLang

Thank you for your interest in contributing to **JavaBhaiLang** — the interpreter where Python whispers, Java obeys, and Bhai Lang unleashes pure chaos.  
Your contributions help keep the chaos well-structured. 😎

## 📑 Table of Contents

- [Contributing to JavaBhaiLang](#contributing-to-javabhailang)
  - [📑 Table of Contents](#-table-of-contents)
  - [How to Contribute](#how-to-contribute)
    - [1. Understand the Project Structure](#1-understand-the-project-structure)
      - [Key Directories](#key-directories)
    - [2. Fork the Repository](#2-fork-the-repository)
    - [3. Clone Your Fork](#3-clone-your-fork)
    - [4. Create a New Branch](#4-create-a-new-branch)
    - [5. Make Your Changes](#5-make-your-changes)
    - [6. Commit Using Angular Format](#6-commit-using-angular-format)
    - [7. Push Your Branch](#7-push-your-branch)
    - [8. Open a Pull Request](#8-open-a-pull-request)
  - [Development Tips](#development-tips)
  - [Reporting Issues](#reporting-issues)
  - [Code of Conduct](#code-of-conduct)
  - [Thank You!](#thank-you)


---

## How to Contribute

### 1. Understand the Project Structure

```bash
JavaBhaiLang/
├── run_bhai_lang.py                  # Python helper script to compile & run
├── src/
│   └── main/java/io/github/journeycodesayush/JavaBhaiLang/
│       ├── BhaiLang.java             # Main driver
│       ├── interpreter/              # Interpreter logic + runtime errors
│       ├── lexer/                    # Lexer, Token, TokenType
│       ├── parser/                   # Parser, AST nodes, statements
│       └── tool/                     # AST generator
├── LICENSE
└── README.md
```
#### Key Directories

- `lexer/` → Scans BhaiLang code into tokens
- `parser/` → Turns tokens into AST
- `interpreter/` → Executes BhaiLang statements
- `tool/` → AST generation utilities
- `run_bhai_lang.py` → Quick compile + run script
 
---

### 2. Fork the Repository

Click **Fork** on GitHub to create your own workspace.

---

### 3. Clone Your Fork

```bash
git clone https://github.com/<your-username>/javabhailang.git
cd javabhailang
```

---

### 4. Create a New Branch 

```bash
git checkout -b feat/my-feature
```
Use descriptive names:
`feat/while-loop`, `fix/parser-bug`, `feat/break-keyword`

---

### 5. Make Your Changes

You may:

- Add new grammar rules
- Improve parser/interpreter behavior
- Implement missing features like `bas kar bhai`, `agla dekh bhai`
- Expand built-ins (`bol bhai`)
- Add `.bhai` example scripts
- Improve performance or readability
- Update documentation

Test quickly using:

```bash
python run_bhai_lang.py yourfile.bhai
```

---

### 6. Commit Using Angular Format

Commit messages must follow:
```bash
<type>(<scope>): <short summary>
```

Allowed types:

| Type     | Meaning                               |
| -------- | ------------------------------------- |
| feat     | New feature                           |
| fix      | Bug fix                               |
| docs     | Documentation                         |
| style    | Formatting only                       |
| refactor | Code improvement without new behavior |
| test     | Tests added/updated                   |
| chore    | Maintenance tasks                     |
| build    | Build system changes                  |

Suggested scopes:
`interpreter`, `parser`, `lexer`, `tool`, `examples`, `docs`

Examples:

```bash
feat(interpreter): add support for bas kar bhai
fix(lexer): correct string literal parsing
docs: improve README examples
```

Commit body (for non-doc commits):

- Minimum 20 characters
- Explain why the change was made
- Use present tense

---

### 7. Push Your Branch

```bash
git push origin feat/my-feature
```

---

### 8. Open a Pull Request

Please include:

- What you changed
- Why you changed it
- Any `.bhai` examples if applicable

---

## Development Tips

- Follow existing grammar and AST patterns
- Keep logic modular and readable
- Test every new feature using `run_bhai_lang.py`
- Place examples in an `examples/` folder
- Commit in small, focused steps

---

## Reporting Issues

Found a bug or have an idea?

Open a GitHub Issue with:
- Clear description
- Steps to reproduce
- Relevant code snippet (BhaiLang or Java)

---

## Code of Conduct

This project follows [CODE_OF_CONDUCT](CODE_OF_CONDUCT.md).

Help keep the chaos respectful.

---

## Thank You!

Whether you fix a typo or add a new language construct, every contribution strengthens JavaBhaiLang.

Shukriya, bhai! ❤️
