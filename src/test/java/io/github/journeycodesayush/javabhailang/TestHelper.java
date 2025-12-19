package io.github.journeycodesayush.javabhailang;

import java.lang.reflect.Method;

import io.github.journeycodesayush.javabhailang.output.*;

/**
 * Utility class for running BhaiLang code in tests and capturing output.
 */
public class TestHelper {

    /**
     * Executes the given BhaiLang source code and returns all printed output.
     *
     * @param source the BhaiLang code to execute
     * @return the text printed by the interpreter during execution
     * @throws RuntimeException if reflection or interpreter execution fails
     */
    public static String runAndCaptureOutput(String source) {

        BhaiLang.hadError = false;
        BhaiLang.hadRuntimeError = false;
        boolean isOriginalRepl = BhaiLang.isRepl;
        BhaiLang.isRepl = true; // Tests always run in REPL mode

        StringCollectingOutput output = new StringCollectingOutput();

        try {
            Method runMethod = BhaiLang.class.getDeclaredMethod("run", String.class, Output.class);
            runMethod.setAccessible(true);
            runMethod.invoke(null, source, output);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run BhaiLang code", e);
        } finally {
            // Restore original isRepl value
            BhaiLang.isRepl = isOriginalRepl;
        }

        return output.toString().replace("\r\n", "\n").replace("\n", System.lineSeparator());
    }
}
