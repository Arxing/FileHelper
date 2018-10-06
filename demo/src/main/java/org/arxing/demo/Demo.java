package org.arxing.demo;

import java.lang.Deprecated;
import java.lang.Object;

public class Demo {
    public int a = 1000;

    @Deprecated @Main.DemoAnnotation(testValue = "Sandy") void demoMethod() {
        int a = 0;
    }

    @interface Anno {
        int id();

        Mode mode();
    }

    public interface Listener {
        boolean onClick(Object target);
    }

    public enum Mode {
        A(100),

        B(101),

        C(102);

        public int val;

        Mode(int val) {
            this.val = val;
        }
    }

    public static class Nested extends Main.DemoSuperClass {
    }
}
