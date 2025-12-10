package io.github.journeycodesayush.javabhailang;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class TestHelper {

    public static String runAndCaptureOutput(String source) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            Method runMethod = BhaiLang.class.getDeclaredMethod("run", String.class);
            runMethod.setAccessible(true);
            runMethod.invoke(null, source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(oldOut);
        }
        String outputStr = output.toString();
        return outputStr;
    }

}
