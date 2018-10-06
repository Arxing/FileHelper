package org.arxing.fileHelper.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Type;

import javax.lang.model.element.Modifier;

public class TypeAnnotationHelper extends TypeHelper<TypeAnnotationHelper> {

    TypeAnnotationHelper(TypeSpec.Kind kind, String name) {
        super(kind, name);
    }

    @Deprecated @Override public TypeAnnotationHelper bindMethods(Iterable<MethodHelper> methods) {
        return super.bindMethods(methods);
    }

    @Deprecated @Override public TypeAnnotationHelper bindMethod(MethodHelper method) {
        return super.bindMethod(method);
    }

    public TypeAnnotationHelper addMember(TypeName type, String name) {
        return bindMethod(MethodHelper.build(name).returns(type).addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT));
    }

    public TypeAnnotationHelper addMember(Type type, String name) {
        return addMember(TypeName.get(type), name);
    }

    public TypeAnnotationHelper addMember(String type, String name) {
        return addMember(ClassName.bestGuess(type), name);
    }
}
