package io.github.journeycodesayush.javabhailang.interpreter;

import static io.github.journeycodesayush.javabhailang.lexer.TokenType.LOGICAL_OR;

import java.util.List;

import io.github.journeycodesayush.javabhailang.BhaiLang;
import io.github.journeycodesayush.javabhailang.lexer.Token;
import io.github.journeycodesayush.javabhailang.parser.*;

/**
 * The core interpreter for BhaiLang programs.
 * <p>
 * Implements the visitor interfaces for {@link Expr} and {@link Stmt} AST
 * nodes.
 * Responsible for executing statements and evaluating expressions in a given
 * {@link Environment}, handling runtime errors, and supporting control flow.
 * </p>
 */
public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {

    /** The current environment for variable storage and scope resolution. */
    private Environment environment = new Environment();

    /**
     * Interprets a list of statements.
     * <p>
     * Executes each statement in order and handles any runtime or null-pointer
     * exceptions.
     * </p>
     *
     * @param statements the list of {@link Stmt} nodes to execute
     */
    public void interpret(List<Stmt> statements) {
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeError error) {
            BhaiLang.runtimeError(error);
        } catch (NallaPointerException error) {
            BhaiLang.nallaPointerError(error);
        }
    }

    /**
     * Executes a single statement by accepting the statement visitor.
     *
     * @param stmt the {@link Stmt} to execute
     */
    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitLogicalExpr(Expr.Logical expr) {
        Object left = evaluate(expr.left);
        if (expr.operator.getType() == LOGICAL_OR) {
            if (isTruthy(left))
                return left;
        } else {
            if (!isTruthy(left))
                return left;
        }
        return evaluate(expr.right);
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        switch (expr.operator.getType()) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double) right;
            default:
                return null;
        }
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.getType()) {
            case GREATER:
                checkNumberOperands(expr.operator, left, right);
                return (double) left > (double) right;
            case GREATER_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left >= (double) right;
            case LESS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left < (double) right;
            case LESS_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left <= (double) right;
            case MINUS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left - (double) right;
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double) left / (double) right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double) left * (double) right;
            case PLUS:
                if (left == null || right == null) {
                    throw new NallaPointerException(expr.operator,
                            "Bhai! Nalla value caught!!!");
                }
                if ((left instanceof String) && (right instanceof String)) {
                    return (String) left + (String) right;
                }
                if ((left instanceof Double) && (right instanceof Double)) {
                    return (double) left + (double) right;
                }
                if ((left instanceof String) || (right instanceof String)) {
                    return stringify(left) + stringify(right);
                }
            case EQUAL_EQUAL:
                return isEqual(left, right);
            case BANG_EQUAL:
                return !isEqual(left, right);

            default:
                return null;
        }
        // return null;
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return environment.get(expr.name);
    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        Object value = evaluate(expr.value);
        environment.assign(expr.name, value);

        return value;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        evaluate(stmt.expression);
        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        if (isTruthy(evaluate(stmt.condition))) {
            execute(stmt.thenBranch);
        } else if (stmt.elseBranch != null) {
            execute(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        executeBlock(stmt.statements, new Environment(environment));
        return null;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        Object value = null;
        if (stmt.initializer != null)
            value = evaluate(stmt.initializer);

        environment.define(stmt.name.getLexeme(), value);
        return null;
    }

    @Override
    public Void visitWhileStmt(Stmt.While stmt) {
        while (isTruthy(evaluate(stmt.condition))) {
            execute(stmt.body);
        }
        return null;
    }

    /**
     * Executes a block of statements in a new environment.
     *
     * @param statements  the list of statements to execute
     * @param environment the environment in which the block executes
     */
    private void executeBlock(List<Stmt> statements, Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;

            for (Stmt statement : statements) {
                execute(statement);
            }

        } finally {
            this.environment = previous;
        }
    }

    /**
     * Determines the truthiness of a value for conditional expressions.
     *
     * @param object the value to test
     * @return true if the value is considered truthy, false otherwise
     */
    private boolean isTruthy(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        return true;
    }

    /**
     * Checks equality between two values.
     *
     * @param a first value
     * @param b second value
     * @return true if both are equal, false otherwise
     */
    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null)
            return true;
        if (a == null)
            return false;

        return a.equals(b);
    }

    /**
     * Ensures that an operand is a number.
     *
     * @param operator the operator token
     * @param operand  the operand to check
     * @throws RuntimeError if the operand is not a number
     */

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) {
            return;
        }

        throw new RuntimeError(operator, "Operand must be a number.");
    }

    /**
     * Ensures that both operands are numbers.
     *
     * @param operator the operator token
     * @param left     the left operand
     * @param right    the right operand
     * @throws RuntimeError          if any operand is not a number
     * @throws NallaPointerException if any operand is null
     */
    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) {
            return;
        }
        if (left == null || right == null) {
            throw new NallaPointerException(operator, "Bhai! Nalla value caught!!!");
        }
        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    /**
     * Converts a value to a string for printing.
     *
     * @param object the value to convert
     * @return string representation of the value
     */

    private String stringify(Object object) {
        if (object == null)
            return "nalla";
        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0"))
                text = text.substring(0, text.length() - 2);

            return text;
        }
        return object.toString();
    }

    /**
     * Evaluates an expression by accepting the expression visitor.
     *
     * @param expr the {@link Expr} to evaluate
     * @return the result of evaluating the expression
     */
    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

}
