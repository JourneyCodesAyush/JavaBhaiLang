package io.github.journeycodesayush.javabhailang.resolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import io.github.journeycodesayush.javabhailang.BhaiLang;
import io.github.journeycodesayush.javabhailang.lexer.Token;
import io.github.journeycodesayush.javabhailang.parser.Expr;
import io.github.journeycodesayush.javabhailang.parser.Stmt;
import io.github.journeycodesayush.javabhailang.interpreter.Interpreter;

/**
 * Handles static analysis for BhaiLang scripts, including variable scoping,
 * resolution, and early error detection.
 * <p>
 * The Resolver traverses the AST (statements and expressions) before
 * interpretation, keeping track of variable scopes and informing the
 * interpreter how many environments to hop to resolve a variable.
 * </p>
 */
public class Resolver implements Expr.Visitor<Void>, Stmt.Visitor<Void> {

    /** The interpreter instance that will be informed of resolved variables. */
    private final Interpreter interpreter;

    /**
     * Stack of scopes, where each scope maps variable names to a boolean indicating
     * if it's defined.
     */
    private final Stack<Map<String, Boolean>> scopes = new Stack<>();

    /**
     * Constructs a Resolver with the given interpreter.
     *
     * @param interpreter the interpreter that will be informed of resolved
     *                    variables
     */
    public Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        beginScope();
        resolve(stmt.statements);
        endScope();
        return null;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        declare(stmt.name);
        if (stmt.initializer != null) {
            resolve(stmt.initializer);
        }
        define(stmt.name);
        return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        resolve(stmt.expression);
        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        resolve(stmt.condition);
        resolve(stmt.thenBranch);

        for (int i = 0; i < stmt.elseIfConditions.size(); i++) {
            resolve(stmt.elseIfConditions.get(i));
            resolve(stmt.elseIfBranches.get(i));
        }

        if (stmt.elseBranch != null) {
            resolve(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        for (Expr expr : stmt.expressions) {
            resolve(expr);
        }
        return null;
    }

    @Override
    public Void visitWhileStmt(Stmt.While stmt) {
        resolve(stmt.condition);
        resolve(stmt.body);
        return null;
    }

    @Override
    public Void visitBreakStmt(Stmt.Break stmt) {
        return null;
    }

    @Override
    public Void visitContinueStmt(Stmt.Continue stmt) {
        return null;
    }

    @Override
    public Void visitAssignExpr(Expr.Assign expr) {
        resolve(expr.value);
        resolveLocal(expr, expr.name);
        return null;
    }

    @Override
    public Void visitBinaryExpr(Expr.Binary expr) {
        resolve(expr.left);
        resolve(expr.right);

        return null;
    }

    @Override
    public Void visitUnaryExpr(Expr.Unary expr) {
        resolve(expr.right);

        return null;
    }

    @Override
    public Void visitLogicalExpr(Expr.Logical expr) {
        resolve(expr.left);
        resolve(expr.right);

        return null;
    }

    @Override
    public Void visitGroupingExpr(Expr.Grouping expr) {
        resolve(expr.expression);
        return null;
    }

    @Override
    public Void visitLiteralExpr(Expr.Literal expr) {
        return null;
    }

    @Override
    public Void visitVariableExpr(Expr.Variable expr) {
        if (!scopes.isEmpty() && scopes.peek().get(expr.name.getLexeme()) == Boolean.FALSE) {
            BhaiLang.error(expr.name, "Can't read local variable in its own initializer.");
        }

        resolveLocal(expr, expr.name);
        return null;
    }

    /**
     * Begins a new local scope.
     * <p>
     * Pushes a new, empty map onto the scope stack. All variables
     * declared after this call will belong to this scope until
     * {@link #endScope()} is called.
     * </p>
     */
    private void beginScope() {
        scopes.push(new HashMap<String, Boolean>());
    }

    /**
     * Ends the current local scope.
     * <p>
     * Pops the top scope from the stack. Variables declared in this scope
     * are no longer accessible.
     * </p>
     */
    private void endScope() {
        scopes.pop();
    }

    /**
     * Declares a new variable in the current scope.
     * <p>
     * Marks the variable as not yet ready for use. If a variable with the
     * same name already exists in the current scope, reports an error.
     * </p>
     *
     * @param name the token representing the variable name
     */
    private void declare(Token name) {
        if (scopes.isEmpty())
            return;

        Map<String, Boolean> scope = scopes.peek();

        if (scope.containsKey(name.getLexeme())) {
            BhaiLang.error(name, "Already a variable with this name in the scope.");
        }

        scope.put(name.getLexeme(), false);
    }

    /**
     * Defines a variable in the current scope.
     * <p>
     * Marks the variable as ready for use. Must be called after any
     * initializer expression has been resolved.
     * </p>
     *
     * @param name the token representing the variable name
     */
    private void define(Token name) {
        if (scopes.isEmpty())
            return;
        scopes.peek().put(name.getLexeme(), true);
    }

    /**
     * Resolves a variable reference to a specific scope.
     * <p>
     * Searches the scope stack from innermost to outermost to find the
     * variable and tells the interpreter how many environments to traverse
     * to access it.
     * </p>
     *
     * @param expr the variable expression
     * @param name the token representing the variable name
     */
    private void resolveLocal(Expr expr, Token name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name.getLexeme())) {
                interpreter.resolve(expr, scopes.size() - 1 - i);
                return;
            }
        }
    }

    /**
     * Resolves a list of statements.
     * <p>
     * Traverses each statement in order and applies resolution.
     * </p>
     *
     * @param statements the list of statements to resolve
     */
    public void resolve(List<Stmt> statements) {
        for (Stmt statement : statements) {
            resolve(statement);
        }
    }

    /**
     * Resolves a single statement by visiting it.
     *
     * @param stmt the statement to resolve
     */
    private void resolve(Stmt stmt) {
        stmt.accept(this);
    }

    /**
     * Resolves a single expression by visiting it.
     *
     * @param expr the expression to resolve
     */
    private void resolve(Expr expr) {
        expr.accept(this);
    }

}
