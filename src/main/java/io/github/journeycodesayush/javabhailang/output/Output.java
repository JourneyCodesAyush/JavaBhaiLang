package io.github.journeycodesayush.javabhailang.output;

public interface Output {

    void print(String s);

    void println(String s);

    default void println() {
        println("");
    }

}
