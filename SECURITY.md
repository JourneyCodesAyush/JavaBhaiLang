# 🔒 Security Policy

Even though **JavaBhaiLang** is a local and experimental project, we still encourage responsible reporting of any suspicious behavior, vulnerabilities, or security concerns.

---

## 🛡️ Supported Versions

This project does not follow a formal release cycle.  
However:

- The **main branch** is maintained  
- Security issues will be reviewed and fixed on a best-effort basis

---

## 🐞 Reporting a Vulnerability

If you find any of the following:

- Unsafe or unexpected interpreter behavior  
- Possible code injection or arbitrary execution  
- Suspicious file system access  
- Crashes triggered by crafted input  
- Dependency-related security concerns  
- Malicious activity in contributions  

Please follow these steps:

### 1. **Do NOT create a public GitHub issue**

Security vulnerabilities should be reported privately.

### 2. **Contact the maintainer directly**

Email: **journeycodes.ayush@gmail.com**  
(or use contact info on the GitHub profile)

Include:

- Description of the issue  
- Steps to reproduce  
- Expected vs actual behavior  
- Potential impact  
- Suggested fix (optional)

### 3. **Await acknowledgment**

You will receive a response within **48–72 hours**.

---

## 🔍 What Happens Next?

- The report will be verified  
- A fix will be created  
- A patched update will be published  
- A security advisory may be issued (if necessary)  
- You may be credited (optional)

---

## 🧪 Security Best Practices for Contributors

- Avoid adding code that enables arbitrary Java execution  
- Be careful with file I/O or external command usage  
- Validate lexer/parser input handling  
- Test grammar changes with edge-case input  
- Do not commit secrets or credentials  
- Keep changes modular and reviewable

---

## 🙏 Thank You

Your efforts help keep **JavaBhaiLang** safe, fun, and maintainable.  
Responsible disclosure is greatly appreciated.
