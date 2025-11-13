package io.github.journeycodesayush.javabhailang;

import io.github.journeycodesayush.javabhailang.interpreter.*;
import io.github.journeycodesayush.javabhailang.lexer.*;
import io.github.journeycodesayush.javabhailang.parser.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BhaiLang {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

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

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        if (hadError)
            System.exit(65);
        if (hadRuntimeError)
            System.exit(70);

        run(new String(bytes, Charset.defaultCharset()));
    }

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

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        if (hadError) {
            return;
        }
        interpreter.interpret(statements);
        // for (Token token : tokens) {
        // System.out.println(token.toString());
        // }

    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error " + where + ": " + message);
        hadError = true;
    }

    public static void error(Token token, String message) {
        if (token.getType() == TokenType.EOF) {
            report(token.getLine(), " at and", message);
        } else {
            report(token.getLine(), " at '" + token.getLexeme() + "'", message);
        }
    }

    public static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.getLine() + "]");
        hadRuntimeError = true;
    }

    public static void nallaPointerError(NallaPointerException error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.getLine()
                + "] Nalla value pe " + error.token.getLexeme() + " operation allowed nahi hai");

    }
}
