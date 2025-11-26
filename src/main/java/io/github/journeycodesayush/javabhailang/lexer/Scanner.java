package io.github.journeycodesayush.javabhailang.lexer;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import io.github.journeycodesayush.javabhailang.BhaiLang;
import static io.github.journeycodesayush.javabhailang.lexer.TokenType.*;

/**
 * The lexer for BhaiLang source code.
 * <p>
 * Converts a raw source string into a list of {@link Token} objects.
 * Handles single-character tokens, multi-character operators, keywords,
 * literals (numbers, strings, booleans), null values, and multi-word keywords.
 * </p>
 */
public class Scanner {

    /** The source code string to scan. */
    private final String source;

    /** The list of tokens produced after scanning. */
    private final List<Token> tokens = new ArrayList<>();

    /** Start index of the current lexeme in the source string. */
    private int start = 0;

    /** Current index being processed in the source string. */
    private int current = 0;

    /** Current line number in the source code. */
    private int line = 1;

    /** Buffer to accumulate characters for multi-character tokens or words. */
    private StringBuilder wordBuffer = new StringBuilder();

    /** Mapping of single-word keywords to their token types. */
    private static final Map<String, TokenType> keywords;

    /**
     * Mapping of multi-word keywords (as lists of strings) to their token types.
     */
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

    /**
     * Constructs a scanner for the given source code.
     *
     * @param source the BhaiLang source code to tokenize (String)
     */
    public Scanner(String source) {
        this.source = source;
    }

    /**
     * Scans the entire source string and returns a list of tokens.
     *
     * @return a list of {@link Token} objects representing the scanned source
     */
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

    /**
     * Checks whether the scanner has reached the end of the source string.
     *
     * @return true if the scanner has processed all characters, false otherwise
     */
    private boolean isAtEnd() {
        return current >= source.length();
    }

    /**
     * Returns the current character without advancing the scanner.
     *
     * @return the current character, or '\0' if at the end
     */
    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    /**
     * Returns the next character without advancing the scanner.
     *
     * @return the next character, or '\0' if at the end
     */
    private char peekNext() {
        if (current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }

    /**
     * Checks if the current character matches the expected character.
     *
     * @param expected the character to match (char)
     * @return true if matched and scanner advanced, false otherwise
     */
    private boolean match(char expected) {
        if (isAtEnd())
            return false;
        if (source.charAt(current) != expected)
            return false;
        current++;
        return true;
    }

    /**
     * Adds a token to the token list with the given type.
     *
     * @param type the type of token (TokenType)
     */
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    /**
     * Adds a token with the given type, lexeme, and literal value.
     *
     * @param type    the token type (TokenType)
     * @param lexeme  the source lexeme (String)
     * @param literal the literal value (Object), if applicable
     */
    private void addToken(TokenType type, String lexeme, Object literal) {
        if (lexeme == null || lexeme.isBlank())
            return;
        tokens.add(new Token(type, lexeme.trim(), literal, line));
    }

    /**
     * Adds a token with the given type and literal value, inferring lexeme from
     * source.
     *
     * @param type    the token type (TokenType)
     * @param literal the literal value (Object), if applicable
     */
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text.trim(), literal, line));
    }

    /**
     * Scans a single token from the source.
     * <p>
     * Handles operators, delimiters, numbers, strings, identifiers, keywords,
     * whitespace, and comments.
     * </p>
     */
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
            case '+' -> {
                addToken(match('=') ? PLUS_EQUAL : PLUS);
            }
            case '-' -> {
                addToken(match('=') ? MINUS_EQUAL : MINUS);
            }
            case '*' -> {
                addToken(match('=') ? STAR_EQUAL : STAR);
            }
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
                } else if (match('='))
                    addToken(SLASH_EQUAL);
                else
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

    /**
     * Processes a collected word buffer into a token, handling keywords,
     * multi-word keywords, and identifiers.
     *
     * @param word the word to process (String)
     */
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

    /**
     * Reads the next word from the source string for multi-word keyword processing.
     *
     * @return the next word, or null if none
     */
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

    /**
     * Scans a string literal and adds it as a token.
     */
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

    /**
     * Advances the scanner by one character and returns it.
     *
     * @return the current character
     */

    private char advance() {
        return source.charAt(current++);
    }

    /**
     * Checks if a character is alphabetic (a-z, A-Z, or '_').
     *
     * @param c the character to check (char)
     * @return true if alphabetic, false otherwise
     */
    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    /**
     * Checks if a character is alphanumeric (alphabetic or digit).
     *
     * @param c the character to check (char)
     * @return true if alphanumeric, false otherwise
     */
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    /**
     * Checks if a character is a digit (0-9).
     *
     * @param c the character to check
     * @return true if a digit, false otherwise
     */
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Scans a number literal (integer or floating-point) and adds it as a token.
     */
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

    /**
     * Checks if a character is whitespace (space, tab, newline, carriage return).
     *
     * @param c the character to check (char)
     * @return true if whitespace, false otherwise
     */
    private boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

}
