package io.github.journeycodesayush.javabhailang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterTest {

        @Test
        public void testPrint() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai a = 10; bol bhai a;");

                assertEquals("10" + System.lineSeparator(), output);
        }

        @Test
        public void testSingleQuote() {
                String output = TestHelper.runAndCaptureOutput("bol bhai 'hello bhai log!';");

                assertEquals("hello bhai log!" + System.lineSeparator(), output);
        }

        @Test
        public void testDoubleQuote() {
                String output = TestHelper.runAndCaptureOutput("bol bhai \"hello bhai log!\";");

                assertEquals("hello bhai log!" + System.lineSeparator(), output);
        }

        @Test
        public void testMultiLineComment() {
                String output = TestHelper
                                .runAndCaptureOutput("/*Multiline string\n was here */ bol bhai 'hello bhai log!';");

                assertEquals("hello bhai log!" + System.lineSeparator(), output);

        }

        @Test
        public void testVariableAssignment() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai a = 10; bol bhai a;");

                assertEquals("10" + System.lineSeparator(), output);
        }

        @Test
        public void testWhileLoopWithContinue() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai i = 0;" +
                                "jab tak bhai (i < 5) {" +
                                "    i = i + 1;" +
                                "    agar bhai (i == 3) { agla dekh bhai; }" +
                                "    bol bhai i;" +
                                "}");

                assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "4" + System.lineSeparator() +
                                "5" + System.lineSeparator(), output);

        }

        @Test
        public void testWhileLoopWithBreak() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai i = 0;" +
                                "jab tak bhai (i < 5) {" +
                                "    i = i + 1;" +
                                "    agar bhai (i == 3) { bas kar bhai; }" +
                                "    bol bhai i;" +
                                "}");

                assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator(), output);

        }

        @Test
        public void testIfElse() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai x = 5;" +
                                                "agar bhai (x > 3) {" +
                                                "bol bhai 10;" +
                                                "} warna bhai {" +
                                                "bol bhai 20;" +
                                                "}");

                assertEquals("10" + System.lineSeparator(), output);
        }

        @Test
        public void testIfElseIf() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai score = 75;\r\n" + //
                                "\r\n" + //
                                "agar bhai (score >= 90) {" +
                                "    bol bhai \"Topper bhai!\";" +
                                "} nahi to bhai (score >= 60) {" +
                                "    bol bhai \"Pass hogaya bhai!\";" +
                                "} warna bhai {" +
                                "    bol bhai \"Thoda padh le bhai.\";" +
                                "}");

                assertEquals("Pass hogaya bhai!" + System.lineSeparator(), output);
        }

        @Test
        public void testNestedIfElse() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai i = 1;" +
                                "jab tak bhai (i <= 5) {" +
                                "    agar bhai (i == 2) {" +
                                "        bol bhai \"Even\";" +
                                "    } nahi to bhai (i == 4) {" +
                                "        bol bhai \"Even\";" +
                                "    } warna bhai {" +
                                "        bol bhai \"Odd\";" +
                                "    }" +
                                "    i = i + 1;" +
                                "}");
                assertEquals(
                                "Odd" + System.lineSeparator() + "Even" + System.lineSeparator() + "Odd"
                                                + System.lineSeparator()
                                                + "Even" + System.lineSeparator() + "Odd" + System.lineSeparator(),
                                output);
        }

        @Test
        public void testNestedWhileIf() {
                String output = TestHelper.runAndCaptureOutput("bhai ye hai i = 1;" +
                                "jab tak bhai (i <= 3) {" +
                                "    bhai ye hai j = 1;" +
                                "    jab tak bhai (j <= 2) {" +
                                "        agar bhai (i == j) {" +
                                "            bol bhai \"Match\";" +
                                "        } warna bhai {" +
                                "            bol bhai \"No Match\";" +
                                "        }" +
                                "        j = j + 1;" +
                                "    }" +
                                "    i = i + 1;" +
                                "}");

                assertEquals("Match" + System.lineSeparator() + "No Match" + System.lineSeparator() + "No Match"
                                + System.lineSeparator() + "Match"
                                + System.lineSeparator() + "No Match"
                                + System.lineSeparator() + "No Match"
                                + System.lineSeparator(), output);

        }

        @Test
        public void testComplexOperators() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = 10;" +
                                                "bhai ye hai b = 5;" +
                                                "a += 3;" + // a = 13
                                                "b -= 2;" + // b = 3
                                                "a *= b;" + // a = 39
                                                "b /= 3;" + // b = 1
                                                "bol bhai a, b;");

                assertEquals("391" + System.lineSeparator(), output);
        }

        @Test
        public void testMultiplePrintArguments() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai a = 5;" +
                                                "bhai ye hai b = 10;" +
                                                "bol bhai \"The values are:\", a, b;");

                assertEquals("The values are:510" + System.lineSeparator(), output);
        }

        @Test
        public void testBooleansAndNull() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai t = sahi;" +
                                                "bhai ye hai f = galat;" +
                                                "bhai ye hai n = nalla;" +
                                                "bol bhai t, f, n;");

                assertEquals("sahigalatnalla" + System.lineSeparator(), output);
        }

        @Test
        public void testLoopWithComplexAssignments() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai sum = 0;" +
                                                "bhai ye hai i = 1;" +
                                                "jab tak bhai (i <= 5) {" +
                                                "    sum += i;" +
                                                "    i += 1;" +
                                                "}" +
                                                "bol bhai sum;");

                assertEquals("15" + System.lineSeparator(), output); // 1+2+3+4+5
        }

        @Test
        public void testStringConcatenation() {
                String output = TestHelper.runAndCaptureOutput(
                                "bhai ye hai str = \"Hello\";" +
                                                "str += \" Bhai\";" +
                                                "bol bhai str;");

                assertEquals("Hello Bhai" + System.lineSeparator(), output);
        }

}
