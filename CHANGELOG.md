# Changelog

All notable changes to this project will be documented in this file.

This changelog follows [Keep a Changelog](https://keepachangelog.com/en/1.0.0/) guidelines and uses [Semantic Versioning](https://semver.org/).

---

## v0.10.0 – 2025-12-31

### Added

- **Truthiness & Falsiness Handling**:

  - Numbers: `0` is falsey, non-zero (positive or negative) is truthy.
  - Booleans: `sahi` → truthy, `galat` → falsey.
  - `nalla` → falsey.
  - Strings: empty and non-empty strings are truthy.

- **Break & Continue Statements**:
  - Added `Token` tracking to **report runtime errors if used outside loops**.

### Tests

- Added unit tests for truthiness, falsiness, and logical NOT operations.

### Changed

- Parser and AST updated to include `Token` in `Break` and `Continue` nodes for improved error reporting.
- Interpreter now throws `RuntimeError` with token reference when `break` or `continue` is used outside loops.

---

## v0.9.0-final – 2025-12-20

### Added

- `isRepl` flag in main driver code to distinguish between REPL and file execution.
- Parser now executes statements **between `hi bhai` and `bye bhai` only for file execution**.
- REPL executes all statements without restriction.
- REPL startup now shows version number and GitHub repository link.

### Changed

- Better alignment with original BhaiLang implementation while maintaining stricter lexer.
- **Print statements now insert implicit spaces between multiple expressions**, aligning with original BhaiLang behavior.
- Updated corresponding **tests** to reflect the new print spacing.

---

## v0.8.0 – 2025-12-18

### Added

- Support for single-quoted strings, e.g., `'hello'`.
- Support for multi-line comments using `/* */`.

---

## v0.7.0 – 2025-12-12

### Added

- `Output` interface to abstract output handling.
- `ConsoleOutput` for standard console printing.
- `StringCollectingOutput` for capturing output in unit tests.
- `TestHelper` class to run BhaiLang code in tests using reflection.
- REPL support in `BhaiLang` main class.
- Ability to run scripts from files via `BhaiLang`.

### Changed

- Cross-platform line-ending handling in tests (`\n` compatibility for Linux/macOS).

### Notes

- This release makes the interpreter fully testable with captured output.
- Prepares the project for future feature additions with a clean testing setup.

---

## v0.6.1 – 2025-12-11

### Added

- Adds `src/test` directory for unit tests and internal testing purposes.

---

## v0.6.0 – 2025-12-05

### Added

- **Resolver**: Introduces a variable resolution pass to handle scopes before interpretation.

---

## v0.5.0 – 2025-11-29

### Added

- **if-elseif-else conditionals**: Allows the use of `agar bhai`(if) with `nahi to bhai`(else if) along with `warna bhai`(else).

### Fixed

- **Interpreter**: Prints `sahi` and `galat` instead of Java's `true` and `false`, aligning console output with the language's boolean literals.

---

## v0.4.1 – 2025-11-26

### Fixed

- **Lexer (`Scanner.java`)**: Correctly handle `+` and `+=` operators.
  - Ensures a `+` token is added when not followed by `=`, instead of mistakenly using `-`.

### Changed

- **Interpreter** and **Parser**: Refactored switch-case syntax to modern Java 14+ arrow syntax for cleaner code.

---

## v0.4.0 – 2025-11-24

### Added

- **Complex assignment operators**: Added support for `+=`, `-=`, `*=`, `/=` in the interpreter for arithmetic expressions.

---

## v0.3.0 – 2025-11-23

### Added

- **Parser**: Supports multiple variables in `bol bhai` (print) statement.
- **Interpreter**: Can now evaluate and print multiple expressions in a single statement.
- **ASTPrinter**: Prints multiple expressions in JSON AST for better debugging and inspection.

---

## v0.2.0 – 2025-11-22

### Added

- **Loop control statements**:
  - `bas kar bhai` → break
  - `agla dekh bhai` → continue
  - Enables finer control over loop execution.

---

## v0.1.0 – 2025-11-16

### Added

- Initial unstable release.
- Supports variable declaration, print statements, conditionals, and loops.
- Includes a basic interpreter and AST evaluation for program execution.
