package io.github.journeycodesayush.javabhailang.interpreter;

import io.github.journeycodesayush.javabhailang.lexer.*;

public class RuntimeError extends RuntimeException {
    public final Token token;

    RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

    public Token getToken() {
        return this.token;
    }
}
