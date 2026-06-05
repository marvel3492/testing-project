package testtools;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CaptureStdOut {
    private final PrintStream originalOut;
    private ByteArrayOutputStream byteBuffer;

    public CaptureStdOut() {
        originalOut = System.out;
        byteBuffer = new ByteArrayOutputStream();
    }

    // reallocate byteBuffer to make obj reusable
    public void redirectToBuffer() {
        byteBuffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteBuffer));
    }

    public void redirectToStdOut() {
        System.setOut(originalOut);
    }

    public void compare(String[] strings) {
        if (strings == null) {
            fail("Expected strings cannot be null");
        } else {
            String[] bufferStrings = toString().split("\r\n");
            if (strings.length != bufferStrings.length) {
                fail(
                        "Expected strings length and actual strings length do not match\nAt Expected: " +
                                Arrays.toString(strings) + "\nActual: " + Arrays.toString(bufferStrings)
                );
            } else {
                for (int i = 0; i < strings.length; i++) {
                    assertEquals(strings[i].trim(), bufferStrings[i].trim());
                }
            }
        }
    }

    public void compare(String string) {
        if (string == null) {
            fail("Expected string cannot be null");
        } else {
            String[] strings = string.split("\n");
            String[] bufferStrings = toString().split("\r\n");
            if (strings.length != bufferStrings.length) {
                fail(
                        "Expected strings length and actual strings length do not match\nAt Expected: " +
                                Arrays.toString(strings) + "\nActual: " + Arrays.toString(bufferStrings)
                );
            } else {
                for (int i = 0; i < strings.length; i++) {
                    assertEquals(strings[i].trim(), bufferStrings[i].trim());
                }
            }
        }
    }

    public String toString() {
        return byteBuffer.toString();
    }
}