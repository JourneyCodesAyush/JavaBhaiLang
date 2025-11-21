package io.github.journeycodesayush.javabhailang.lexer;

/**
 * Represents all the types of tokens in BhaiLang.
 * <p>
 * Used by the lexer and parser to classify each token in the source code.
 * </p>
 */
public enum TokenType {

    // Single-character tokens
    /** { character */
    LEFT_CURLY_BRACE,
    /** } character */
    RIGHT_CURLY_BRACE,
    /** ( character */
    LEFT_PAREN,
    /** ) character */
    RIGHT_PAREN,
    /** ; character */
    SEMICOLON,
    /** + character */
    PLUS,
    /** - character */
    MINUS,
    /** * character */
    STAR,
    /** / character */
    SLASH,
    /** . character */
    DOT,
    /** , character */
    COMMA,

    // One or two character tokens
    /** ! character */
    BANG,
    /** != characters */
    BANG_EQUAL,
    /** = character */
    EQUAL,
    /** == characters */
    EQUAL_EQUAL,
    /** &lt; character */
    GREATER,
    /** &lt;= characters */
    GREATER_EQUAL,
    /** &gt; character */
    LESS,
    /** &gt;= characters */
    LESS_EQUAL,
    /** &amp;&amp; characters */
    LOGICAL_AND,
    /** || characters */
    LOGICAL_OR,

    // Identifier, Literal, String, Number
    /** Identifiers (variable names) */
    IDENTIFIER,
    /** String literals */
    STRING,
    /** Boolean literals (sahi, galat) */
    BOOLEAN,
    /** Numeric literals */
    NUMBER,

    // Keywords
    /** hi bhai keyword */
    HI_BHAI,
    /** bye bhai keyword */
    BYE_BHAI,
    /** bol bhai keyword for printing */
    BOL_BHAI,
    /** bhai ye hai keyword for variable declaration */
    BHAI_YE_HAI,
    /** agar bhai keyword for if statements */
    AGAR_BHAI,
    /** warna bhai keyword for else statements */
    WARNA_BHAI,
    /** nahi to bhai keyword for else-if (not fully implemented) */
    NAHI_TO_BHAI,
    /** jab tak bhai keyword for loops */
    JAB_TAK_BHAI,
    /** bas kar bhai keyword for break */
    BAS_KAR_BHAI,
    /** agla dekh bhai keyword for continue */
    AGLA_DEKH_BHAI,
    /** sahi literal */
    SAHI,
    /** galat literal */
    GALAT,

    // Null
    /** nalla literal */
    NALLA,

    // End of File
    /** End of file token */
    EOF
}
