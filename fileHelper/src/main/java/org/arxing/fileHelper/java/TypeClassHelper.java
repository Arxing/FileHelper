package org.arxing.fileHelper.java;

import com.annimon.stream.Stream;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;

@SuppressWarnings("all")
public class TypeClassHelper extends TypeHelper<TypeClassHelper> {
    private Map<String, FieldHelper> fieldMap = new HashMap<>();

    TypeClassHelper(TypeSpec.Kind kind, String name) {
        super(kind, name);
    }

    @Override public TypeSpec create() {
        Stream.of(fieldMap).map(Map.Entry::getValue).forEach(fieldHelper -> builder.addField(fieldHelper.create()));
        return super.create();
    }

    // generic

    public TypeClassHelper addTypeVariables(Iterable<TypeVariableName> typeVariables) {
        builder.addTypeVariables(typeVariables);
        return this;
    }

    public TypeClassHelper addTypeVariable(TypeVariableName typeVariable) {
        builder.addTypeVariable(typeVariable);
        return this;
    }

    // super class

    public TypeClassHelper superClass(TypeName typeName) {
        builder.superclass(typeName);
        return this;
    }

    public TypeClassHelper superClass(String className) {
        return superClass(ClassName.bestGuess(className));
    }

    public TypeClassHelper superClass(Type type) {
        return superClass(TypeName.get(type));
    }

    // implements interface

    public TypeClassHelper addSuperInterfaces(Iterable<? extends TypeName> superinterfaces) {
        builder.addSuperinterfaces(superinterfaces);
        return this;
    }

    public TypeClassHelper addSuperInterface(TypeName superinterface) {
        builder.addSuperinterface(superinterface);
        return this;
    }

    public TypeClassHelper addSuperInterface(Type superinterface) {
        return addSuperInterface(TypeName.get(superinterface));
    }

    public TypeClassHelper addSuperInterface(String superinterface) {
        return addSuperInterface(ClassName.bestGuess(superinterface));
    }

    // field

    public TypeClassHelper bindFields(Iterable<FieldHelper> fields) {
        Stream.of(fields).forEach(this::bindField);
        return this;
    }

    public TypeClassHelper bindField(FieldHelper field) {
        String key = field.name;
        if (!fieldMap.containsKey(key)) {
            fieldMap.put(key, field);
        }
        FieldHelper binder = fieldMap.get(key);
        field.replaceBuilder(binder.builder);
        return this;
    }

    public TypeClassHelper bindField(Type type, String name, Modifier... modifiers) {
        return bindField(FieldHelper.build(type, name, modifiers));
    }

    public TypeClassHelper bindField(TypeName type, String name, Modifier... modifiers) {
        return bindField(FieldHelper.build(type, name, modifiers));
    }

    public TypeClassHelper bindField(String type, String name, Modifier... modifiers) {
        return bindField(FieldHelper.build(type, name, modifiers));
    }

    // static block

    public TypeClassHelper addStaticBlock(String template, Object... templateArgs) {
        builder.addStaticBlock(CodeBlock.of(template, templateArgs));
        builder.addStaticBlock(CodeBlock.of("\n"));
        return this;
    }

    public TypeClassHelper addStaticBlock(String template, ArgsMap argsMap, Object... templateArgs) {
        builder.addStaticBlock(CodeBlock.of(JavaUtils.renderCode(template, argsMap), templateArgs));
        builder.addStaticBlock(CodeBlock.of("\n"));
        return this;
    }

    // initial block

    public TypeClassHelper addInitializerBlock(String template, Object... templateArgs) {
        builder.addInitializerBlock(CodeBlock.of(template, templateArgs));
        builder.addInitializerBlock(CodeBlock.of("\n"));
        return this;
    }

    public TypeClassHelper addInitializerBlock(String template, ArgsMap argsMap, Object... templateArgs) {
        builder.addInitializerBlock(CodeBlock.of(JavaUtils.renderCode(template, argsMap), templateArgs));
        builder.addInitializerBlock(CodeBlock.of("\n"));
        return this;
    }
}
