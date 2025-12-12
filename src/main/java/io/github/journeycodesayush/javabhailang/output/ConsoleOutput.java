package io.github.journeycodesayush.javabhailang.output;

public class ConsoleOutput implements Output {
    @Override
    public void print(String s) {
        System.out.print(s);
    }

    @Override
    public void println(String s) {
        System.out.println(s);

    }
}
