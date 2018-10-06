package org.arxing.fileHelper.java;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.lang.model.element.Modifier;

public class FieldHelper extends AbstractHelper<FieldSpec, FieldSpec.Builder> {
    String name;

    private FieldHelper(TypeName typeName, String name, Modifier... modifiers) {
        this.name = name;
        builder = FieldSpec.builder(typeName, name, modifiers);
    }

    public static FieldHelper build(TypeName typeName, String name, Modifier... modifiers) {
        return new FieldHelper(typeName, name, modifiers);
    }

    public static FieldHelper build(Type type, String name, Modifier... modifiers) {
        return new FieldHelper(TypeName.get(type), name, modifiers);
    }

    public static FieldHelper build(String type, String name, Modifier... modifiers) {
        return new FieldHelper(ClassName.bestGuess(type), name, modifiers);
    }

    @Override public FieldSpec create() {
        return builder.build();
    }


    // modifier

    public FieldHelper addModifiers(Modifier... modifiers) {
        builder.addModifiers(modifiers);
        return this;
    }

    // annotation

    public FieldHelper addAnnotation(AnnotationSpec spec) {
        builder.addAnnotation(spec);
        return this;
    }

    public FieldHelper addAnnotation(ClassName annotationType) {
        return addAnnotation(AnnotationSpec.builder(annotationType).build());
    }

    public FieldHelper addAnnotation(String annotationType) {
        return addAnnotation(ClassName.bestGuess(annotationType));
    }

    public FieldHelper addAnnotation(Class<? extends Annotation> annotationType) {
        return addAnnotation(ClassName.get(annotationType));
    }

    // java doc

    public FieldHelper addJavadoc(String template, Object... templateArgs) {
        builder.addJavadoc(CodeBlock.of(template, templateArgs));
        return this;
    }

    public FieldHelper addJavadoc(String template, ArgsMap argsMap, Object... templateArgs) {
        builder.addJavadoc(JavaUtils.renderCode(template, argsMap), templateArgs);
        return this;
    }

    // initialize

    public FieldHelper initializer(String template, Object... templateArgs) {
        builder.initializer(template, templateArgs);
        return this;
    }

    public FieldHelper initializer(String template, ArgsMap argsMap, Object... templateArgs) {
        builder.initializer(JavaUtils.renderCode(template, argsMap), templateArgs);
        return this;
    }

    // other

    void replaceBuilder(FieldSpec.Builder builder) {
        this.builder = builder;
    }
}
