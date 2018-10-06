package org.arxing.fileHelper.java;

import java.util.HashMap;
import java.util.Map;

public class ArgsMap {
    Map<String, Object> map = new HashMap<>();

    private ArgsMap() {
    }

    public static ArgsMap build() {
        return new ArgsMap();
    }

    public ArgsMap add(String name, Object val) {
        map.put(name, val);
        return this;
    }
}
