package io.github.journeycodesayush.javabhailang.lexer;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import io.github.journeycodesayush.javabhailang.BhaiLang;
import static io.github.journeycodesayush.javabhailang.lexer.TokenType.*;

public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private StringBuilder wordBuffer = new StringBuilder();

    private static final Map<String, TokenType> keywords;
    private static final Map<List<String>, TokenType> multiKeywords;

    static {
        keywords = new HashMap<>();
        keywords.put("sahi", BOOLEAN);
        keywords.put("galat", BOOLEAN);
        keywords.put("nalla", NALLA);

        multiKeywords = new HashMap<>();
        multiKeywords.put(List.of("hi", "bhai"), HI_BHAI);
        multiKeywords.put(List.of("bye", "bhai"), BYE_BHAI);
        multiKeywords.put(List.of("bol", "bhai"), BOL_BHAI);
        multiKeywords.put(List.of("bhai", "ye", "hai"), BHAI_YE_HAI);
        multiKeywords.put(List.of("agar", "bhai"), AGAR_BHAI);
        multiKeywords.put(List.of("warna", "bhai"), WARNA_BHAI);
        multiKeywords.put(List.of("nahi", "to", "bhai"), NAHI_TO_BHAI);
        multiKeywords.put(List.of("jab", "tak", "bhai"), JAB_TAK_BHAI);
        multiKeywords.put(List.of("bas", "kar", "bhai"), BAS_KAR_BHAI);
        multiKeywords.put(List.of("agla", "dekh", "bhai"), AGLA_DEKH_BHAI);
    }

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        if (wordBuffer.length() > 0) {
            processWord(wordBuffer.toString());
            wordBuffer.setLength(0);
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }

    private boolean match(char expected) {
        if (isAtEnd())
            return false;
        if (source.charAt(current) != expected)
            return false;
        current++;
        return true;
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, String lexeme, Object literal) {
        if (lexeme == null || lexeme.isBlank())
            return;
        tokens.add(new Token(type, lexeme.trim(), literal, line));
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text.trim(), literal, line));
    }

    private void scanToken() {
        char c = advance();

        if (!isAlphaNumeric(c) && wordBuffer.length() > 0) {
            processWord(wordBuffer.toString());
            wordBuffer.setLength(0);
        }

        switch (c) {
            case '(' -> addToken(LEFT_PAREN);
            case ')' -> addToken(RIGHT_PAREN);
            case '{' -> addToken(LEFT_CURLY_BRACE);
            case '}' -> addToken(RIGHT_CURLY_BRACE);
            case ',' -> addToken(COMMA);
            case '.' -> addToken(DOT);
            case ';' -> addToken(SEMICOLON);
            case '+' -> addToken(PLUS);
            case '-' -> addToken(MINUS);
            case '*' -> addToken(STAR);
            case '!' -> addToken(match('=') ? BANG_EQUAL : BANG);
            case '=' -> addToken(match('=') ? EQUAL_EQUAL : EQUAL);
            case '>' -> addToken(match('=') ? GREATER_EQUAL : GREATER);
            case '<' -> addToken(match('=') ? LESS_EQUAL : LESS);
            case '/' -> {
                if (match('/')) {
                    if (wordBuffer.length() > 0) {
                        processWord(wordBuffer.toString());
                        wordBuffer.setLength(0);
                    }
                    while (!isAtEnd() && peek() != '\n')
                        advance();
                } else
                    addToken(SLASH);
            }
            case '&' -> {
                if (match('&'))
                    addToken(LOGICAL_AND);
                else
                    BhaiLang.error(line, "Unexpected character '&'. Use '&&' for logical AND.");
            }
            case '|' -> {
                if (match('|'))
                    addToken(LOGICAL_OR);
                else
                    BhaiLang.error(line, "Unexpected character '|'. Use '||' for logical OR.");
            }
            case ' ', '\t', '\r' -> {
            }
            case '\n' -> {
                if (wordBuffer.length() > 0) {
                    processWord(wordBuffer.toString());
                    wordBuffer.setLength(0);
                }
                line++;
            }
            case '"' -> string();
            default -> {
                if (isDigit(c))
                    number();
                else if (isAlphaNumeric(c))
                    wordBuffer.append(c);
                else
                    BhaiLang.error(line, "Unexpected character.");
            }
        }
    }

    private void processWord(String word) {
        word = word.trim();
        if (word.isEmpty())
            return;

        // Multi-word keywords
        for (List<String> keyList : multiKeywords.keySet()) {
            if (keyList.get(0).equals(word)) {
                List<String> collected = new ArrayList<>();
                collected.add(word);
                boolean matchFailed = false;

                for (int i = 1; i < keyList.size(); i++) {
                    String nextWord = readNextWord();
                    if (nextWord == null || nextWord.isEmpty() || !nextWord.equals(keyList.get(i))) {
                        matchFailed = true;
                        if (nextWord != null && !nextWord.isEmpty()) {
                            wordBuffer.setLength(0);
                            wordBuffer.append(nextWord);
                        }
                        break;
                    }
                    collected.add(nextWord);
                }

                if (matchFailed) {
                    for (String w : collected) {
                        if (w != null && !w.isBlank())
                            addToken(IDENTIFIER, null);
                    }
                } else {
                    if (!collected.isEmpty()) {
                        addToken(multiKeywords.get(keyList), String.join(" ", collected), null);
                    }
                }
                return;
            }
        }

        // Single-word keywords
        TokenType type = keywords.get(word);
        if (type == BOOLEAN && word.equals("sahi"))
            addToken(type, word.trim(), true);
        else if (type == BOOLEAN && word.equals("galat"))
            addToken(type, word.trim(), false);
        else if (type == NALLA && word.equals("nalla"))
            addToken(type, word.trim(), null);
        else if (!word.isBlank())
            addToken(IDENTIFIER, word.trim(), null);
    }

    private String readNextWord() {
        while (!isAtEnd() && isWhiteSpace(peek())) {
            if (peek() == '\n')
                line++;
            advance();
        }
        int wordStart = current;
        while (!isAtEnd() && isAlphaNumeric(peek()))
            advance();

        String nextWord = source.substring(wordStart, current).trim();
        return nextWord.isEmpty() ? null : nextWord;
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n')
                line++;
            advance();
        }
        if (isAtEnd()) {
            BhaiLang.error(line, "Unterminated string.");
            return;
        }
        advance();
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(peek()))
            advance();
        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek()))
                advance();
        }
        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

}
