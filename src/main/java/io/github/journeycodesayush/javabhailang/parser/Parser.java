package io.github.journeycodesayush.javabhailang.parser;

import java.util.List;
import java.util.ArrayList;

import static io.github.journeycodesayush.javabhailang.lexer.TokenType.*;

import io.github.journeycodesayush.javabhailang.BhaiLang;
import io.github.journeycodesayush.javabhailang.lexer.Token;
import io.github.journeycodesayush.javabhailang.lexer.TokenType;

/**
 * Parses a list of {@link io.github.journeycodesayush.javabhailang.lexer.Token}
 * objects
 * into an abstract syntax tree (AST) composed of {@link Stmt} and {@link Expr}
 * nodes.
 * <p>
 * Implements a recursive descent parser with support for:
 * </p>
 * <ul>
 * <li>Variable declarations</li>
 * <li>Expressions (arithmetic, logical, comparison)</li>
 * <li>Control flow statements (if, while)</li>
 * <li>Print statements</li>
 * <li>Blocks</li>
 * </ul>
 */
public class Parser {

    /**
     * A runtime exception representing a parsing error.
     */
    private static class ParseError extends RuntimeException {
    }

    /** The list of tokens to parse. */
    private final List<Token> tokens;

    /** Current position in the token list. */
    private int current = 0;

    /**
     * Constructs a parser for the given list of tokens.
     *
     * @param tokens the list of tokens to parse
     */
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses the token list and returns a list of statements representing the
     * program.
     *
     * @return list of {@link Stmt} objects
     */
    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(declaration());
        }
        return statements;
    }

    /**
     * Parses a declaration (variable declaration or statement).
     *
     * @return a {@link Stmt} node
     */
    private Stmt declaration() {
        try {
            if (match(BHAI_YE_HAI))
                return varDeclaration();

            return statement();
        } catch (ParseError error) {
            synchronize();
            return null;
        }
    }

    /**
     * Parses a variable declaration.
     *
     * @return a {@link Stmt.Var} representing the variable declaration
     */
    private Stmt varDeclaration() {
        Token name = consume(IDENTIFIER, "Expect variable name.");

        Expr initializer = null;
        if (match(EQUAL)) {
            initializer = expression();
        }
        consume(SEMICOLON, "Expect ';' after variable declaration.");
        return new Stmt.Var(name, initializer);
    }

    /**
     * Parses a statement (if, while, print, block, or expression).
     *
     * @return a {@link Stmt} object representing the statement
     */
    private Stmt statement() {
        if (match(AGAR_BHAI)) {
            return ifStatement();
        }
        if (match(BOL_BHAI)) {
            return printStatement();
        }
        if (match(JAB_TAK_BHAI)) {
            return whileStatement();
        }
        if (match(BAS_KAR_BHAI)) {
            consume(SEMICOLON, "Expect ';' after 'bas kar bhai'.");
            return new Stmt.Break();
        }
        if (match(AGLA_DEKH_BHAI)) {
            consume(SEMICOLON, "Expect ';' after 'agla dekh bhai'.");
            return new Stmt.Continue();
        }
        if (match(LEFT_CURLY_BRACE))
            return new Stmt.Block(block());
        return expressionStatement();
    }

    /**
     * Parses a while loop statement.
     *
     * @return a {@link Stmt.While} object representing the loop
     */
    private Stmt whileStatement() {
        consume(LEFT_PAREN, "Expect a '(' after 'jab tak bhai'.");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect a ')' after condition.");
        Stmt body = statement();

        return new Stmt.While(condition, body);
    }

    /**
     * Parses an if statement, including optional else branch.
     *
     * @return a {@link Stmt.If} object representing the if statement
     */
    private Stmt ifStatement() {
        consume(LEFT_PAREN, "Expect '(' after 'if' condition");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after if condition.");

        Stmt thenBranch = statement();
        Stmt elseBranch = null;
        if (match(WARNA_BHAI)) {
            elseBranch = statement();
        }

        return new Stmt.If(condition, thenBranch, elseBranch);
    }

    /**
     * Parses a print statement.
     *
     * @return a {@link Stmt.Print} object representing the print statement
     */
    private Stmt printStatement() {
        List<Expr> expressions = new ArrayList<>();
        expressions.add(expression());
        while (match(COMMA)) {
            expressions.add(expression());
        }
        consume(SEMICOLON, "Expect ';' after value.");
        return new Stmt.Print(expressions);
    }

    /**
     * Parses a standalone expression statement.
     *
     * @return a {@link Stmt.Expression} object
     */
    private Stmt expressionStatement() {
        Expr expr = expression();
        consume(SEMICOLON, "Expect ';'after value.");
        return new Stmt.Expression(expr);
    }

    /**
     * Parses a block of statements enclosed in curly braces.
     *
     * @return a list of {@link Stmt} objects inside the block
     */
    private List<Stmt> block() {
        List<Stmt> statements = new ArrayList<>();

        while (!check(RIGHT_CURLY_BRACE) && !isAtEnd()) {
            statements.add(declaration());
        }

        consume(RIGHT_CURLY_BRACE, "Expect '}' after block.");
        return statements;
    }

    /**
     * Returns the current token without consuming it.
     *
     * @return the current {@link Token}
     */
    private Token peek() {
        return tokens.get(current);
    }

    /**
     * Checks whether we have reached the end of the token list.
     *
     * @return true if at the end, false otherwise
     */
    private boolean isAtEnd() {
        return peek().getType() == EOF;
    }

    /**
     * Returns the previous token.
     *
     * @return the previous {@link Token}
     */
    private Token previous() {
        return tokens.get(current - 1);
    }

    /**
     * Advances to the next token and returns the previous one.
     *
     * @return the previous {@link Token}
     */
    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    /**
     * Checks if the current token matches any of the given types.
     * Advances if a match is found.
     *
     * @param types one or more {@link TokenType} to match
     * @return true if matched, false otherwise
     */
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the current token is of the given type without consuming it.
     *
     * @param type the {@link TokenType} to check
     * @return true if it matches, false otherwise
     */
    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return peek().getType() == type;
    }

    /**
     * Parses an expression starting point.
     *
     * @return a {@link Expr} object
     */
    private Expr expression() {
        return assignment();
    }

    /**
     * Parses an assignment expression.
     *
     * @return a {@link Expr} representing the assignment
     */
    private Expr assignment() {
        Expr expr = logical_or();

        if (match(EQUAL)) {
            Token equals = previous();
            Expr value = assignment();

            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(name, value);
            }
            error(equals, "Invalid assignment target.");
        } else if (match(PLUS_EQUAL)) {
            Expr value = assignment();
            Expr binaryExpr = new Expr.Binary(expr, new Token(PLUS, "+", null, previous().getLine()), value);
            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(name, binaryExpr);
            }
        } else if (match(MINUS_EQUAL)) {
            Expr value = assignment();
            Expr binaryExpr = new Expr.Binary(expr, new Token(MINUS, "-", null, previous().getLine()), value);
            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(name, binaryExpr);
            }
        } else if (match(STAR_EQUAL)) {
            Expr value = assignment();
            Expr binaryExpr = new Expr.Binary(expr, new Token(STAR, "*", null, previous().getLine()), value);
            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(name, binaryExpr);
            }
        } else if (match(SLASH_EQUAL)) {
            Expr value = assignment();
            Expr binaryExpr = new Expr.Binary(expr, new Token(SLASH, "/", null, previous().getLine()), value);
            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(name, binaryExpr);
            }
        }

        return expr;
    }

    /**
     * Parses logical OR expressions.
     *
     * @return a {@link Expr.Logical} or nested expression
     */
    private Expr logical_or() {
        Expr expr = logical_and();
        while (match(LOGICAL_OR)) {
            Token operator = previous();
            Expr right = logical_and();
            expr = new Expr.Logical(expr, operator, right);

        }
        return expr;
    }

    /**
     * Parses logical AND expressions.
     *
     * @return a {@link Expr.Logical} or nested expression
     */
    private Expr logical_and() {
        Expr expr = equality();
        while (match(LOGICAL_AND)) {
            Token operator = previous();
            Expr right = equality();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }

    /**
     * Parses equality expressions (==, !=).
     *
     * @return a {@link Expr.Binary} or nested expression
     */
    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    /**
     * Parses comparison expressions (<, <=, >, >=).
     *
     * @return a {@link Expr.Binary} or nested expression
     */
    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER_EQUAL, GREATER, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    /**
     * Parses addition and subtraction expressions.
     *
     * @return a {@link Expr.Binary} or nested expression
     */
    private Expr term() {
        Expr expr = factor();

        while (match(PLUS, MINUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    /**
     * Parses multiplication and division expressions.
     *
     * @return a {@link Expr.Binary} or nested expression
     */
    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    /**
     * Parses unary expressions (!, -).
     *
     * @return a {@link Expr.Unary} or nested expression
     */
    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }
        return primary();
    }

    /**
     * Parses primary expressions: literals, variables, or grouped expressions.
     *
     * @return an {@link Expr} representing the primary expression
     */
    private Expr primary() {
        if (match(BOOLEAN))
            return new Expr.Literal(previous().getLiteral());

        if (match(NALLA))
            return new Expr.Literal(null);

        if (match(STRING, NUMBER))
            return new Expr.Literal(previous().getLiteral());

        if (match(IDENTIFIER))
            return new Expr.Variable(previous());

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect a ')' after expression.");
            return new Expr.Grouping(expr);
        }

        throw error(peek(), "Expect expression.");
    }

    /**
     * Consumes a token of the expected type or throws an error.
     *
     * @param type    expected {@link TokenType}
     * @param message error message if type doesn't match
     * @return the consumed {@link Token}
     */
    private Token consume(TokenType type, String message) {
        if (check(type))
            return advance();
        throw error(peek(), message);
    }

    /**
     * Reports a parse error at a token.
     *
     * @param token   the {@link Token} at which error occurred
     * @param message error message
     * @return a {@link ParseError} object
     */
    private ParseError error(Token token, String message) {
        BhaiLang.error(token, message);
        return new ParseError();
    }

    /**
     * Synchronizes the parser after a parse error to continue parsing.
     */
    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().getType() == SEMICOLON) {
                return;
            }
            switch (peek().getType()) {
                case BHAI_YE_HAI -> {
                    return;
                }
                case BOL_BHAI -> {
                    return;
                }
                case AGAR_BHAI -> {
                    return;
                }
                case WARNA_BHAI -> {
                    return;
                }
                case NAHI_TO_BHAI -> {
                    return;
                }
                case JAB_TAK_BHAI -> {
                    return;
                }
                case BAS_KAR_BHAI -> {
                    return;
                }
                case AGLA_DEKH_BHAI -> {
                    return;
                }

                default -> {
                }
            }
            advance();
        }
    }

}
