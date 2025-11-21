package io.github.journeycodesayush.javabhailang.interpreter;

import java.util.HashMap;
import java.util.Map;

import io.github.journeycodesayush.javabhailang.lexer.Token;

/**
 * Represents a variable environment for the BhaiLang interpreter.
 * <p>
 * Each environment stores a mapping of variable names to their values.
 * It may optionally reference an enclosing environment to support nested
 * scopes.
 * </p>
 */
public class Environment {

    /** Optional enclosing environment for nested scopes. */
    final Environment enclosing;

    /** The mapping of variable names to their values in this environment. */
    private final Map<String, Object> values = new HashMap<>();

    /**
     * Creates a new global environment with no enclosing environment.
     */
    Environment() {
        enclosing = null;
    }

    /**
     * Creates a new environment enclosed within another environment.
     *
     * @param enclosing the outer environment to which this environment is nested
     */
    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    /**
     * Retrieves the value of a variable from this environment or its enclosing
     * environments.
     *
     * @param name the {@link Token} representing the variable name
     * @return the value of the variable
     * @throws RuntimeError if the variable is undefined in this environment chain
     */
    Object get(Token name) {
        if (values.containsKey(name.getLexeme())) {
            return values.get(name.getLexeme());
        }

        if (enclosing != null)
            return enclosing.get(name);

        throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'.");
    }

    /**
     * Defines a new variable in the current environment.
     * <p>
     * If the variable already exists in this environment, its value will be
     * overwritten.
     * </p>
     *
     * @param name  the variable name
     * @param value the value to assign
     */
    void define(String name, Object value) {
        values.put(name, value);
    }

    /**
     * Assigns a value to an existing variable in this environment or any enclosing
     * environment.
     *
     * @param name  the {@link Token} representing the variable name
     * @param value the value to assign
     * @throws RuntimeError if the variable is undefined in this environment chain
     */
    void assign(Token name, Object value) {
        if (values.containsKey(name.getLexeme())) {
            values.put(name.getLexeme(), value);
            return;
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'.");
    }
}
