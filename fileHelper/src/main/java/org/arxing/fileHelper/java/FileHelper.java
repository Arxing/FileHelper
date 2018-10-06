package org.arxing.fileHelper.java;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.squareup.javapoet.JavaFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;

public class FileHelper {
    private String pkg;
    private TypeHelper rootType;
    private List<CodeInfo> comments;
    private int indentSpaceSize = 4;

    private FileHelper(String pkg, TypeHelper type) {
        this.rootType = type;
        this.pkg = pkg;
        this.comments = new ArrayList<>();
    }

    public static FileHelper build(String pkg, TypeHelper type) {
        return new FileHelper(pkg, type);
    }

    public JavaFile create() {
        JavaFile.Builder builder = JavaFile.builder(pkg, rootType.create());
        builder.indent(Stream.range(0, indentSpaceSize).map(v -> " ").collect(Collectors.joining()));
        Stream.of(comments).forEach(info -> builder.addFileComment(info.template, info.args));
        return builder.build();
    }

    // comment

    public FileHelper addComment(String template, Object... templateArgs) {
        comments.add(new CodeInfo(template, templateArgs));
        return this;
    }

    public FileHelper addComment(String template, ArgsMap argsMap, Object... templateArgs) {
        comments.add(new CodeInfo(JavaUtils.renderCode(template, argsMap), templateArgs));
        return this;
    }

    // indent

    public FileHelper setIndentSpaceSize(int size) {
        indentSpaceSize = size;
        return this;
    }

    // other

    public void create(Filer filer) {
        try {
            create().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(File file) {
        try {
            create().writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(String path) {
        create(new File(path));
    }

}
