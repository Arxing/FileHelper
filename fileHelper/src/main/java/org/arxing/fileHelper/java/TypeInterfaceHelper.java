package org.arxing.fileHelper.java;

import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class TypeInterfaceHelper extends TypeHelper<TypeInterfaceHelper> {

    TypeInterfaceHelper(TypeSpec.Kind kind, String name) {
        super(kind, name);
    }

    @Override public TypeInterfaceHelper bindMethod(MethodHelper method) {
        method.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
        return super.bindMethod(method);
    }
}
