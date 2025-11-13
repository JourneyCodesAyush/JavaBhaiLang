package io.github.journeycodesayush.javabhailang.interpreter;

import io.github.journeycodesayush.javabhailang.lexer.*;

public class NallaPointerException extends NullPointerException {
    public final Token token;

    NallaPointerException(Token token, String message) {
        super(message);
        this.token = token;
    }

    public Token getToken() {
        return this.token;
    }
}
