package io.github.journeycodesayush.javabhailang.parser;

import io.github.journeycodesayush.javabhailang.lexer.Token;
import io.github.journeycodesayush.javabhailang.lexer.TokenType;

import java.util.List;

/**
 * Pretty-printing JSON AST printer for BhaiLang.
 * <p>
 * Generates readable JSON output for Expr and Stmt nodes.
 * Useful for debugging, learning, or feeding AST to tools/AI.
 * </p>
 */
public class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {

    /** Current indentation level when printing JSON */
    private int indentLevel = 0;

    /** String used for each indentation level */
    private final String INDENT = "  ";

    /**
     * Returns a string representing the current indentation.
     *
     * @return String of spaces corresponding to the current indent level
     */
    private String indent() {
        return INDENT.repeat(indentLevel);
    }

    /**
     * Formats a JSON key-value pair with optional comma.
     *
     * @param key   JSON key
     * @param value JSON value
     * @param comma true if a comma should follow, false otherwise
     * @return formatted JSON string for this key-value pair
     */
    private String format(String key, String value, boolean comma) {
        return String.format("%s\"%s\": %s%s\n", indent(), key, value, comma ? "," : "");
    }

    /**
     * Wraps a string in quotes or returns "null" if the input is null.
     *
     * @param s input string
     * @return quoted string or "null"
     */
    private String quote(String s) {
        if (s == null)
            return "null";
        return "\"" + s + "\"";
    }

    /**
     * Entry point for printing a single expression as JSON.
     *
     * @param expr expression node to print
     * @return JSON string representing the expression
     */
    public String print(Expr expr) {
        indentLevel = 0;
        return expr.accept(this);
    }

    /**
     * Entry point for printing a single statement as JSON.
     *
     * @param stmt statement node to print
     * @return JSON string representing the statement
     */
    public String print(Stmt stmt) {
        indentLevel = 0;
        return stmt.accept(this);
    }

    /**
     * Converts a list of statements or expressions to a JSON array.
     *
     * @param list List of Expr or Stmt objects
     * @return JSON string representing the list
     */
    private String listToJson(List<?> list) {
        if (list == null || list.isEmpty())
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        indentLevel++;
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            String json;
            if (obj instanceof Expr) {
                json = ((Expr) obj).accept(this);
            } else if (obj instanceof Stmt) {
                json = ((Stmt) obj).accept(this);
            } else {
                json = quote(obj.toString());
            }
            sb.append(indent()).append(json);
            if (i < list.size() - 1)
                sb.append(",");
            sb.append("\n");
        }
        indentLevel--;
        sb.append(indent()).append("]");
        return sb.toString();
    }

    // ===== Expr Visitors =====

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        indentLevel++;
        sb.append(format("type", quote("Binary"), true));
        sb.append(format("operator", quote(expr.operator.getLexeme()), true));
        sb.append(format("left", expr.left.accept(this), true));
        sb.append(format("right", expr.right.accept(this), false));
        indentLevel--;
        sb.append(indent()).append("}");
        return sb.toString();
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        indentLevel++;
        sb.append(format("type", quote("Grouping"), true));
        sb.append(format("expression", expr.expression.accept(this), false));
        indentLevel--;
        sb.append(indent()).append("}");
        return sb.toString();
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        return "{\n" +
                INDENT + "\"type\": \"Literal\",\n" +
                INDENT + "\"value\": " + (expr.value == null ? "null" : quote(expr.value.toString())) + "\n" +
                "}";
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        indentLevel++;
        sb.append(format("type", quote("Unary"), true));
        sb.append(format("operator", quote(expr.operator.getLexeme()), true));
        sb.append(format("right", expr.right.accept(this), false));
        indentLevel--;
        sb.append(indent()).append("}");
        return sb.toString();
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return "{\n" +
                INDENT + "\"type\": \"Variable\",\n" +
                INDENT + "\"name\": " + quote(expr.name.getLexeme()) + "\n" +
                "}";
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return "{\n" +
                INDENT + "\"type\": \"Assign\",\n" +
                INDENT + "\"name\": " + quote(expr.name.getLexeme()) + ",\n" +
                INDENT + "\"value\": " + expr.value.accept(this) + "\n" +
                "}";
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        indentLevel++;
        sb.append(format("type", quote("Logical"), true));
        sb.append(format("operator", quote(expr.operator.getLexeme()), true));
        sb.append(format("left", expr.left.accept(this), true));
        sb.append(format("right", expr.right.accept(this), false));
        indentLevel--;
        sb.append(indent()).append("}");
        return sb.toString();
    }

    // ===== Stmt Visitors =====

    @Override
    public String visitExpressionStmt(Stmt.Expression stmt) {
        return "{\n" +
                INDENT + "\"type\": \"Expression\",\n" +
                INDENT + "\"expression\": " + stmt.expression.accept(this) + "\n" +
                "}";
    }

    @Override
    public String visitPrintStmt(Stmt.Print stmt) {
        return "{\n" +
                INDENT + "\"type\": \"Print\",\n" +
                INDENT + "\"expression\": " + listToJson(stmt.expressions) + "\n" +
                "}";
    }

    @Override
    public String visitVarStmt(Stmt.Var stmt) {
        String initializerJson = stmt.initializer == null ? "null" : stmt.initializer.accept(this);
        return "{\n" +
                INDENT + "\"type\": \"Variable\",\n" +
                INDENT + "\"name\": " + quote(stmt.name.getLexeme()) + ",\n" +
                INDENT + "\"initializer\": " + initializerJson + "\n" +
                "}";
    }

    @Override
    public String visitBlockStmt(Stmt.Block stmt) {
        return "{\n" +
                INDENT + "\"type\": \"Block\",\n" +
                INDENT + "\"statements\": " + listToJson(stmt.statements) + "\n" +
                "}";
    }

    @Override
    public String visitIfStmt(Stmt.If stmt) {
        return "{\n" +
                INDENT + "\"type\": \"If\",\n" +
                INDENT + "\"condition\": " + stmt.condition.accept(this) + ",\n" +
                INDENT + "\"thenBranch\": " + stmt.thenBranch.accept(this) + ",\n" +
                INDENT + "\"elseBranch\": " + (stmt.elseBranch == null ? "null" : stmt.elseBranch.accept(this)) + "\n" +
                "}";
    }

    @Override
    public String visitWhileStmt(Stmt.While stmt) {
        return "{\n" +
                INDENT + "\"type\": \"While\",\n" +
                INDENT + "\"condition\": " + stmt.condition.accept(this) + ",\n" +
                INDENT + "\"body\": " + stmt.body.accept(this) + "\n" +
                "}";
    }

    @Override
    public String visitBreakStmt(Stmt.Break stmt) {
        return "{\n" +
                INDENT + "\"type\": \"Break\"" +
                "}";
    }

    @Override
    public String visitContinueStmt(Stmt.Continue stmt) {
        return "{\n" +
                INDENT + "\"type\": \"Continue\"" +
                "}";

    }

    /**
     * Standalone main method for testing the AST printer.
     * Generates a sample expression and prints its JSON representation.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
                new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(new Expr.Literal(45.67)));

        AstPrinter printer = new AstPrinter();
        System.out.println(printer.print(expression));
    }
}
