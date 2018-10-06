package org.arxing.fileHelper.java;

import com.annimon.stream.Stream;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;

public class TypeEnumHelper extends TypeHelper<TypeEnumHelper> {
    private Map<String, FieldHelper> fieldMap = new HashMap<>();

    TypeEnumHelper(TypeSpec.Kind kind, String name) {
        super(kind, name);
    }

    @Override public TypeSpec create() {
        Stream.of(fieldMap).map(Map.Entry::getValue).forEach(fieldHelper -> builder.addField(fieldHelper.create()));
        return super.create();
    }

    // field

    public TypeEnumHelper bindFields(Iterable<FieldHelper> fields) {
        Stream.of(fields).forEach(this::bindField);
        return this;
    }

    public TypeEnumHelper bindField(FieldHelper field) {
        String key = field.name;
        if (!fieldMap.containsKey(key)) {
            fieldMap.put(key, field);
        }
        FieldHelper binder = fieldMap.get(key);
        field.replaceBuilder(binder.builder);
        return this;
    }

    public TypeEnumHelper bindField(Type type, String name, Modifier... modifiers) {
        return bindField(FieldHelper.build(type, name, modifiers));
    }

    public TypeEnumHelper bindField(TypeName type, String name, Modifier... modifiers) {
        return bindField(FieldHelper.build(type, name, modifiers));
    }

    public TypeEnumHelper bindField(String type, String name, Modifier... modifiers) {
        return bindField(FieldHelper.build(type, name, modifiers));
    }

    // constant

    public TypeEnumHelper addConstant(String name) {
        builder.addEnumConstant(name);
        return this;
    }

    public TypeEnumHelper addConstant(String name, String template, Object... templateArgs) {
        builder.addEnumConstant(name, TypeSpec.anonymousClassBuilder(template, templateArgs).build());
        return this;
    }

    public TypeEnumHelper addConstant(String name, String template, ArgsMap argsMap, Object... templateArgs) {
        builder.addEnumConstant(name, TypeSpec.anonymousClassBuilder(JavaUtils.renderCode(template, argsMap), templateArgs).build());
        return this;
    }
}
