package io.github.journeycodesayush.javabhailang.interpreter;

import io.github.journeycodesayush.javabhailang.lexer.*;

/**
 * Represents a runtime error caused by a null-like value in BhaiLang programs.
 * <p>
 * This exception extends {@link NullPointerException} and is thrown when
 * the interpreter encounters a "nalla" (null) value where a valid value is
 * expected. It also tracks the {@link Token} where the error occurred, so
 * that the interpreter can provide precise error reporting to the user.
 * </p>
 */
public class NallaPointerException extends NullPointerException {

    /** The token associated with the null error. */
    public final Token token;

    /**
     * Constructs a new NallaPointerException with the specified token and message.
     *
     * @param token   the {@link Token} where the null-like error occurred
     * @param message a descriptive error message
     */
    NallaPointerException(Token token, String message) {
        super(message);
        this.token = token;
    }

    /**
     * Returns the token that caused this exception.
     *
     * @return the {@link Token} associated with the null error
     */
    public Token getToken() {
        return this.token;
    }
}
