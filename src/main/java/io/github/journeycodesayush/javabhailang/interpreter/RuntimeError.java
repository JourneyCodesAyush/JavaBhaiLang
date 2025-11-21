package io.github.journeycodesayush.javabhailang.interpreter;

import io.github.journeycodesayush.javabhailang.lexer.*;

/**
 * Represents a runtime error that occurs during the execution of a BhaiLang
 * program.
 * <p>
 * This exception extends {@link RuntimeException} and carries the {@link Token}
 * at which the error occurred, allowing the interpreter to provide precise
 * error
 * reporting to the user.
 * </p>
 */
public class RuntimeError extends RuntimeException {

    /** The token associated with the runtime error. */
    public final Token token;

    /**
     * Constructs a new RuntimeError with the specified token and message.
     *
     * @param token   the {@link Token} at which the error occurred
     * @param message a descriptive error message
     */
    RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

    /**
     * Returns the token that caused this runtime error.
     *
     * @return the {@link Token} associated with the error
     */
    public Token getToken() {
        return this.token;
    }
}
