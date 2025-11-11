package io.github.journeycodesayush.javabhailang.lexer;

public enum TokenType {

    // Single-character tokens
    LEFT_CURLY_BRACE, RIGHT_CURLY_BRACE,
    LEFT_PAREN, RIGHT_PAREN, SEMICOLON, PLUS, MINUS, STAR, SLASH, DOT, COMMA,

    // One or two character tokens
    BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, LOGICAL_AND, LOGICAL_OR,

    // Identifier, Literal, String, Number
    IDENTIFIER, STRING, BOOLEAN, NUMBER,

    // Keyword
    HI_BHAI, BYE_BHAI,
    BOL_BHAI, BHAI_YE_HAI,
    AGAR_BHAI, WARNA_BHAI, NAHI_TO_BHAI,
    JAB_TAK_BHAI, BAS_KAR_BHAI, AGLA_DEKH_BHAI,
    SAHI, GALAT,

    // Null character
    NALLA,

    // End of File
    EOF
}