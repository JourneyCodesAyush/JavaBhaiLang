package io.github.journeycodesayush.javabhailang.output;

public class StringCollectingOutput implements Output {

    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void print(String s) {
        stringBuilder.append(s);
    }

    @Override
    public void println(String s) {
        stringBuilder.append(s).append("\n");
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
