package io.github.journeycodesayush.javabhailang;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TruthinessTest {

        @Test
        public void testZeroIsFalsey() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = 0;" +
                                                "agar bhai (!a) { bol bhai 'zero is falsey'; }");
                assertEquals("zero is falsey" + System.lineSeparator(), output);
        }

        @Test
        public void testNonZeroIsTruthy() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = 42;" +
                                                "agar bhai (a) { bol bhai 'non-zero is truthy'; }");
                assertEquals("non-zero is truthy" + System.lineSeparator(), output);
        }

        @Test
        public void testNegativeNumberIsTruthy() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = -5;" +
                                                "agar bhai (a) { bol bhai 'negative number is truthy'; }");
                assertEquals("negative number is truthy" + System.lineSeparator(), output);
        }

        @Test
        public void testNullIsFalsey() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = nalla;" +
                                                "agar bhai (!a) { bol bhai 'nalla is falsey'; }");
                assertEquals("nalla is falsey" + System.lineSeparator(), output);
        }

        @Test
        public void testBooleanTruthiness() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai t = sahi;" +
                                                "bhai ye hai f = galat;" +
                                                "agar bhai (t) { bol bhai 'true is truthy'; }" +
                                                "agar bhai (!f) { bol bhai 'false is falsey'; }");
                assertEquals(
                                "true is truthy" + System.lineSeparator() +
                                                "false is falsey" + System.lineSeparator(),
                                output);
        }

        @Test
        public void testEmptyStringIsTruthy() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai s = '';" +
                                                "agar bhai (s) { bol bhai 'empty string is truthy'; }");
                assertEquals("empty string is truthy" + System.lineSeparator(), output);
        }

        @Test
        public void testNonEmptyStringIsTruthy() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai s = 'hello';" +
                                                "agar bhai (s) { bol bhai 'non-empty string is truthy'; }");
                assertEquals("non-empty string is truthy" + System.lineSeparator(), output);
        }

        @Test
        public void testLogicalNot() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = 0;" +
                                                "bhai ye hai b = sahi;" +
                                                "agar bhai (!a) { bol bhai 'not zero is true'; }" +
                                                "agar bhai (!(!b)) { bol bhai 'double not returns original truthiness'; }");
                assertEquals(
                                "not zero is true" + System.lineSeparator() +
                                                "double not returns original truthiness" + System.lineSeparator(),
                                output);
        }

        @Test
        public void testOrTruthiness() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = 0;" +
                                                "bhai ye hai b = 5;" +
                                                "agar bhai (a || b) { bol bhai 'or works'; }");
                assertEquals("or works" + System.lineSeparator(), output);
        }

        @Test
        public void testAndTruthiness() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = 1;" +
                                                "bhai ye hai b = 0;" +
                                                "agar bhai (a && b) { bol bhai 'and works'; } warna bhai { bol bhai 'and short-circuits'; }"

                );
                assertEquals("and short-circuits" + System.lineSeparator(), output);
        }

        @Test
        public void testNotAndTruthiness() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai a = 0;" +
                                "bhai ye hai b = sahi;" +
                                "agar bhai (!a && b) { bol bhai 'not and works'; }");
                assertEquals("not and works" + System.lineSeparator(), output);
        }

        @Test
        public void testChainedLogicalTruthiness() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai a = 0;" +
                                "bhai ye hai b = 1;" +
                                "bhai ye hai c = 2;" +
                                "agar bhai (a || b && c) { bol bhai 'chained logic works'; }");
                assertEquals("chained logic works" + System.lineSeparator(), output);
        }
}
