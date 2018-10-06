package org.arxing.fileHelper.java;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import org.arxing.fileHelper.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/*
 * 一個方法由幾個資訊組成
 * 1. modifier
 * 2. return type
 * 3. method name
 * 4. method parameter
 * 5. annotation
 * 6. codes
 *
 * */
public class MethodHelper extends AbstractHelper<MethodSpec, MethodSpec.Builder> {
    List<Modifier> modifiers;
    TypeName returnType;
    String methodName;
    List<ParameterSpec> parameters;
    boolean isBound;
    boolean isConstructor;

    private MethodHelper(String methodName, boolean isConstructor) {
        this.methodName = methodName;
        this.isConstructor = isConstructor;
        this.builder = isConstructor ? MethodSpec.constructorBuilder() : MethodSpec.methodBuilder(methodName);
        this.modifiers = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    public static MethodHelper build(String methodName) {
        return new MethodHelper(methodName, false);
    }

    public static MethodHelper buildConstructor() {
        return new MethodHelper("", true);
    }

    public MethodSpec create() {
        return builder.build();
    }

    // modifier

    public MethodHelper addModifiers(Modifier... modifiers) {
        Utils.assertMessage(isBound, "You can't change a bound helper.");
        builder.addModifiers(modifiers);
        this.modifiers.addAll(Arrays.asList(modifiers));
        return this;
    }

    // parameter

    public MethodHelper addParameter(ParameterSpec spec) {
        Utils.assertMessage(isBound, "You can't change a bound helper.");
        builder.addParameter(spec);
        this.parameters.add(spec);
        return this;
    }

    public MethodHelper addParameter(TypeName type, String name, Modifier... modifiers) {
        return addParameter(ParameterSpec.builder(type, name, modifiers).build());
    }

    public MethodHelper addParameter(Type type, String name, Modifier... modifiers) {
        return addParameter(TypeName.get(type), name, modifiers);
    }

    public MethodHelper addParameter(String className, String name, Modifier... modifiers) {
        return addParameter(ClassName.bestGuess(className), name, modifiers);
    }

    public MethodHelper addParameter(VariableElement elField) {
        return addParameter(ParameterSpec.get(elField));
    }

    // statement

    public MethodHelper addStatement(String template, Object... templateArgs) {
        builder.addStatement(template, templateArgs);
        return this;
    }

    public MethodHelper addStatement(String template, ArgsMap argsMap, Object... templateArgs) {
        builder.addStatement(JavaUtils.renderCode(template, argsMap), templateArgs);
        return this;
    }

    // return

    public MethodHelper returns(TypeName type) {
        Utils.assertMessage(isBound, "You can't change a bound helper.");
        Utils.assertMessage(isConstructor, "Constructor can not returns.");
        builder.returns(type);
        this.returnType = type;
        return this;
    }

    public MethodHelper returns(Type type) {
        return returns(TypeName.get(type));
    }

    // annotation

    public MethodHelper addAnnotation(AnnotationSpec spec) {
        builder.addAnnotation(spec);
        return this;
    }

    public MethodHelper addAnnotation(ClassName annotationType) {
        return addAnnotation(AnnotationSpec.builder(annotationType).build());
    }

    public MethodHelper addAnnotation(String annotationType) {
        return addAnnotation(ClassName.bestGuess(annotationType));
    }

    public MethodHelper addAnnotation(Class<? extends Annotation> annotationType) {
        return addAnnotation(ClassName.get(annotationType));
    }

    // java doc

    public MethodHelper addJavadoc(String template, Object... templateArgs) {
        builder.addJavadoc(CodeBlock.of(template, templateArgs));
        return this;
    }

    public MethodHelper addJavadoc(String template, ArgsMap argsMap, Object... templateArgs) {
        builder.addJavadoc(JavaUtils.renderCode(template, argsMap), templateArgs);
        return this;
    }

    // other

    public MethodKey toMethodKey() {
        return MethodKey.from(this);
    }

    void replaceBuilder(MethodSpec.Builder builder) {
        this.builder = builder;
    }


}
