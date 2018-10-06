package org.arxing.fileHelper;


import com.annimon.stream.Stream;

public class Utils {

    public static void assertMessage(boolean condition, String format, Object... args) {
        assertMessage(condition, 2, format, args);
    }

    public static void assertMessage(boolean condition, int back, String format, Object... args) {
        Error error = new Error(String.format(format, args));
        StackTraceElement[] newStack = Stream.of(error.getStackTrace()).skip(back).toList().toArray(new StackTraceElement[0]);
        error.setStackTrace(newStack);
        if (condition)
            throw error;
    }
}
