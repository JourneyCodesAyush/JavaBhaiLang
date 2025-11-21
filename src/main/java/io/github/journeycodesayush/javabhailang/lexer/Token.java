package io.github.journeycodesayush.javabhailang.lexer;

/**
 * Represents a single token produced by the BhaiLang lexer.
 * <p>
 * A token consists of a type, its lexeme (actual string from source),
 * an optional literal value, and the line number it appears on.
 * </p>
 */
public class Token {

    /** The type of this token. */
    final TokenType type;

    /** The string value (lexeme) of the token from the source code. */
    final String lexeme;

    /**
     * The literal value associated with the token, if any (e.g., number, string).
     */
    final Object literal;

    /** The line number in the source code where this token appears. */
    final int line;

    /**
     * Constructs a new token.
     *
     * @param type    the token type (TokenType)
     * @param lexeme  the lexeme from the source code (String)
     * @param literal the literal value of the token, if applicable (Object)
     * @param line    the line number where the token appears (int)
     */
    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    /**
     * Returns the type of this token.
     *
     * @return the token type (TokenType)
     */
    public TokenType getType() {
        return this.type;
    }

    /**
     * Returns the lexeme of this token.
     *
     * @return the token lexeme (String)
     */
    public String getLexeme() {
        return this.lexeme;
    }

    /**
     * Returns the literal value of this token.
     *
     * @return the literal value (Object), or null if none
     */
    public Object getLiteral() {
        return this.literal;
    }

    /**
     * Returns the line number where this token appears.
     *
     * @return the line number (int)
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Returns a formatted string representing this token.
     *
     * @return string representation of the token
     */
    public String toString() {
        String displayLexeme = (lexeme == null || lexeme.isEmpty()) ? "<none>" : lexeme;
        String displayLiteral = (literal == null) ? "<null>" : literal.toString();
        return String.format(
                "Type: %-15s Lexeme: %-20s Literal: %-15s Line: %d",
                type.toString(),
                displayLexeme,
                displayLiteral,
                line);
    }

}
