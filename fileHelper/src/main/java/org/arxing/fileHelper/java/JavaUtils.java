package org.arxing.fileHelper.java;

import com.annimon.stream.Stream;

import org.arxing.fileHelper.java.ArgsMap;
import org.stringtemplate.v4.ST;

class JavaUtils {

    public static String renderCode(String template, ArgsMap params) {
        ST st = new ST(template);
        Stream.of(params.map).forEach(entry -> st.add(entry.getKey(), entry.getValue()));
        return st.render();
    }
}
