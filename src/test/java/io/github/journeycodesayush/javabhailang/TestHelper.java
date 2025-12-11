package io.github.journeycodesayush.javabhailang;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

/**
 * Utility class for invoking the BhaiLang interpreter during unit tests.
 *
 * <p>
 * This helper bypasses the private access of {@code BhaiLang.run(String)}
 * using reflection, allowing test classes to execute BhaiLang code directly.
 *
 * <p>
 * The method {@link #runAndCaptureOutput(String)} also captures anything
 * printed to {@code System.out}, enabling assertions on the interpreter's
 * console output.
 *
 * <p>
 * <strong>Usage in tests:</strong>
 * 
 * <pre>
 * String output = TestHelper.runAndCaptureOutput("bol bhai \"Hello\";");
 * assertEquals("Hello\n", output);
 * </pre>
 *
 * <p>
 * Notes:
 * <ul>
 * <li>This class is intended solely for testing.</li>
 * <li>The underlying interpreter currently exposes no public API for direct
 * execution,
 * so reflection is required.</li>
 * <li>{@code System.out} is restored after each invocation.</li>
 * </ul>
 */
public class TestHelper {

    /**
     * Executes the given BhaiLang source code and returns all printed output.
     *
     * <p>
     * This method:
     * <ol>
     * <li>Redirects {@code System.out} to capture interpreter output</li>
     * <li>Uses reflection to invoke the private {@code BhaiLang.run(String)}
     * method</li>
     * <li>Restores the original {@code System.out}</li>
     * </ol>
     *
     * @param source the BhaiLang code to execute
     * @return the text printed by the interpreter during execution
     * @throws RuntimeException if reflection or interpreter execution fails
     */
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
