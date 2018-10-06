package org.arxing.fileHelper.java;

import com.annimon.stream.Stream;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;

public class TypeHelper<TSelf> extends AbstractHelper<TypeSpec, TypeSpec.Builder> {
    private Map<MethodKey, MethodHelper> methodMap = new HashMap<>();
    private Map<String, TypeHelper> typeMap = new HashMap<>();
    private String name;

    TypeHelper(TypeSpec.Kind kind, String name) {
        this.name = name;
        switch (kind) {
            case ENUM:
                builder = TypeSpec.enumBuilder(name);
                break;
            case CLASS:
                builder = TypeSpec.classBuilder(name);
                break;
            case INTERFACE:
                builder = TypeSpec.interfaceBuilder(name);
                break;
            case ANNOTATION:
                builder = TypeSpec.annotationBuilder(name);
                break;
        }
    }

    public static TypeClassHelper buildClass(String name) {
        return new TypeClassHelper(TypeSpec.Kind.CLASS, name);
    }

    public static TypeEnumHelper buildEnum(String name) {
        return new TypeEnumHelper(TypeSpec.Kind.ENUM, name);
    }

    public static TypeInterfaceHelper buildInterface(String name) {
        return new TypeInterfaceHelper(TypeSpec.Kind.INTERFACE, name);
    }

    public static TypeAnnotationHelper buildAnnotation(String name) {
        return new TypeAnnotationHelper(TypeSpec.Kind.ANNOTATION, name);
    }

    @Override public TypeSpec create() {
        Stream.of(methodMap).map(Map.Entry::getValue).forEach(methodHelper -> builder.addMethod(methodHelper.create()));
        Stream.of(typeMap).map(Map.Entry::getValue).forEach(typeHelper -> builder.addType(typeHelper.create()));
        return builder.build();
    }

    // modifier

    public TSelf addModifiers(Modifier... modifiers) {
        builder.addModifiers(modifiers);
        return (TSelf) this;
    }

    // method

    public TSelf bindMethods(Iterable<MethodHelper> methods) {
        Stream.of(methods).forEach(this::bindMethod);
        return (TSelf) this;
    }

    public TSelf bindMethod(MethodHelper method) {
        MethodKey key = method.toMethodKey();
        if (!methodMap.containsKey(key)) {
            methodMap.put(key, method);
        }
        MethodHelper binder = methodMap.get(key);
        method.replaceBuilder(binder.builder);
        method.isBound = true;
        return (TSelf) this;
    }

    public MethodHelper findMethod(MethodKey key) {
        return methodMap.get(key);
    }

    // java doc

    public TSelf addJavadoc(String template, Object... templateArgs) {
        builder.addJavadoc(CodeBlock.of(template, templateArgs));
        return (TSelf) this;
    }

    public TSelf addJavadoc(String template, ArgsMap paramMap, Object... templateArgs) {
        builder.addJavadoc(JavaUtils.renderCode(template, paramMap), templateArgs);
        return (TSelf) this;
    }

    // annotation

    public TSelf addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
        Stream.of(annotationSpecs).forEach(a -> addAnnotation(a));
        return (TSelf) this;
    }

    public TSelf addAnnotation(AnnotationSpec annotation) {
        builder.addAnnotation(annotation);
        return (TSelf) this;
    }

    public TSelf addAnnotation(Class<? extends Annotation> annotation, String memberFormat, Object... memberArgs) {
        return addAnnotation(AnnotationSpec.builder(annotation).addMember("value", memberFormat, memberArgs).build());
    }

    public TSelf addAnnotation(Class<? extends Annotation> annotation) {
        return addAnnotation(AnnotationSpec.builder(annotation).build());
    }

    public TSelf addAnnotation(String className, String memberFormat, Object... memberArgs) {
        return addAnnotation(ClassName.bestGuess(className), memberFormat, memberArgs);
    }

    public TSelf addAnnotation(String className) {
        return addAnnotation(ClassName.bestGuess(className));
    }

    public TSelf addAnnotation(ClassName annotation, String memberTemplate, Object... memberArgs) {
        return addAnnotation(AnnotationSpec.builder(annotation).addMember("value", memberTemplate, memberArgs).build());
    }

    public TSelf addAnnotation(ClassName annotation) {
        return addAnnotation(AnnotationSpec.builder(annotation).build());
    }

    // type

    public TSelf bindTypes(Iterable<TypeHelper> typeSpecs) {
        Stream.of(typeSpecs).forEach(this::bindType);
        return (TSelf) this;
    }

    public TSelf bindType(TypeHelper type) {
        String key = type.name;
        if (!typeMap.containsKey(key)) {
            typeMap.put(key, type);
        }
        TypeHelper binder = typeMap.get(key);
        type.replaceBuilder((TypeSpec.Builder) binder.builder);
        return (TSelf) this;
    }

    // other

    public TypeClassHelper toClass() {
        return (TypeClassHelper) this;
    }

    public TypeEnumHelper toEnum() {
        return (TypeEnumHelper) this;
    }

    public TypeInterfaceHelper toInterface() {
        return (TypeInterfaceHelper) this;
    }

    public TypeAnnotationHelper toAnnotation() {
        return (TypeAnnotationHelper) this;
    }

    void replaceBuilder(TypeSpec.Builder builder) {
        this.builder = builder;
    }
}
