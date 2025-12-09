package io.github.journeycodesayush.javabhailang;

import io.github.journeycodesayush.javabhailang.interpreter.*;
import io.github.journeycodesayush.javabhailang.lexer.*;
import io.github.journeycodesayush.javabhailang.parser.*;
import io.github.journeycodesayush.javabhailang.resolver.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The main class for the JavaBhaiLang interpreter.
 * <p>
 * Provides the entry point for running BhaiLang scripts either from files
 * or interactively via a REPL. Handles error reporting and runtime exceptions.
 * </p>
 */
public class BhaiLang {

    /** The interpreter instance that executes BhaiLang statements. */
    private static final Interpreter interpreter = new Interpreter();

    /** Indicates if a syntax or parsing error has occurred. */
    static boolean hadError = false;

    /** Indicates if a runtime error has occurred during interpretation. */
    static boolean hadRuntimeError = false;

    /** ANSI color code for cyan text in the console. */
    private static final String CYAN = "\u001B[36m";

    /** ANSI color code for green text in the console. */
    private static final String GREEN = "\u001B[32m";

    /** ANSI color code for red text in the console. */
    private static final String RED = "\u001B[1;91m";

    /** ANSI reset code to revert console text color to default. */
    private static final String RESET = "\u001B[0m";

    /**
     * The main entry point for the JavaBhaiLang interpreter.
     *
     * @param args command-line arguments; if provided, args[0] is treated as the
     *             path to a BhaiLang script (array of String)
     * @throws IOException if reading a script file fails
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    /**
     * Runs a BhaiLang script from a file.
     *
     * @param path the file path of the BhaiLang script (String)
     * @throws IOException if reading the file fails
     */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        if (hadError)
            System.exit(65);
        if (hadRuntimeError)
            System.exit(70);

        run(new String(bytes, Charset.defaultCharset()));
    }

    /**
     * Runs the interactive REPL for BhaiLang.
     *
     * @throws IOException if reading user input fails
     */
    private static void runPrompt() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(CYAN + "\n" +
                "     ██╗ █████╗ ██╗   ██╗ █████╗     ██████╗ ██╗  ██╗ █████╗ ██╗    ██╗      █████╗ ███╗   ██╗ ██████╗ \n"
                +
                "     ██║██╔══██╗██║   ██║██╔══██╗    ██╔══██╗██║  ██║██╔══██╗██║    ██║     ██╔══██╗████╗  ██║██╔════╝ \n"
                +
                "     ██║███████║██║   ██║███████║    ██████╔╝███████║███████║██║    ██║     ███████║██╔██╗ ██║██║  ███╗\n"
                +
                "██   ██║██╔══██║╚██╗ ██╔╝██╔══██║    ██╔══██╗██╔══██║██╔══██║██║    ██║     ██╔══██║██║╚██╗██║██║   ██║\n"
                +
                "╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║    ██████╔╝██║  ██║██║  ██║██║    ███████╗██║  ██║██║ ╚████║╚██████╔╝\n"
                +
                " ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝    ╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ \n"
                +
                "                                                                                                        "
                + RESET);

        System.out.println(CYAN + "Welcome to the BhaiLang REPL (Java version)!" + RESET);
        System.out.println("Type something and press Enter. Type 'exit' to quit.\n");

        while (true) {
            System.out.print(CYAN + ">> " + RESET);
            String line = reader.readLine();
            if (line == null || line.equals("exit;") || line.equals("exit")) {
                System.out.println(GREEN + "Bye bye bhai..." + RESET);
                break;
            }
            run(line);
            hadError = false;
        }
    }

    /**
     * Executes a string of BhaiLang source code.
     *
     * @param source the BhaiLang source code to execute (String)
     */
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        if (hadError) {
            return;
        }
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        if (hadError) {
            return;
        }

        interpreter.interpret(statements);
        // AstPrinter printer = new AstPrinter();
        // for (Stmt statement : statements) {
        // System.out.println(printer.print(statement));
        // }

    }

    /**
     * Reports a syntax or parsing error at a given line.
     *
     * @param line    the line number where the error occurred (int)
     * @param message the error message (String)
     */
    public static void error(int line, String message) {
        report(line, "", message);
    }

    /**
     * Helper method to format and print an error message.
     *
     * @param line    the line number (int)
     * @param where   location description (e.g., token lexeme) (String)
     * @param message the error message (String)
     */
    private static void report(int line, String where, String message) {
        System.err.println(RED + "[line " + line + "] Error " + where + ": " + message + RESET);
        hadError = true;
    }

    /**
     * Reports an error associated with a specific token.
     *
     * @param token   the token where the error occurred (Token)
     * @param message the error message (String)
     */
    public static void error(Token token, String message) {
        if (token.getType() == TokenType.EOF) {
            report(token.getLine(), " at and", message);
        } else {
            report(token.getLine(), " at '" + token.getLexeme() + "'", message);
        }
    }

    /**
     * Reports a runtime error during interpretation.
     *
     * @param error the runtime error (RuntimeError)
     */
    public static void runtimeError(RuntimeError error) {
        System.err.println(RED + error.getMessage() + RESET + "\n[line " + error.token.getLine() + "]");
        hadRuntimeError = true;
    }

    /**
     * Reports a null-pointer-like error in BhaiLang.
     *
     * @param error the null pointer exception in BhaiLang (NallaPointerException)
     */
    public static void nallaPointerError(NallaPointerException error) {
        System.err.println(RED + error.getMessage() + RESET + "\n[line " + error.token.getLine()
                + "] Nalla value pe " + error.token.getLexeme() + " operation allowed nahi hai");

    }
}
