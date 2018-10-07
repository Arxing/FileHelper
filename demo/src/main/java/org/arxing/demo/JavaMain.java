package org.arxing.demo;

import com.squareup.javapoet.AnnotationSpec;

import org.arxing.fileHelper.java.ArgsMap;
import org.arxing.fileHelper.java.FieldHelper;
import org.arxing.fileHelper.java.JavaFileHelper;
import org.arxing.fileHelper.java.MethodHelper;
import org.arxing.fileHelper.java.TypeAnnotationHelper;
import org.arxing.fileHelper.java.TypeClassHelper;
import org.arxing.fileHelper.java.TypeEnumHelper;
import org.arxing.fileHelper.java.TypeHelper;
import org.arxing.fileHelper.java.TypeInterfaceHelper;

import javax.lang.model.element.Modifier;

public class JavaMain {

    public static void main(String[] args) {
        TypeHelper rootType = TypeHelper.buildClass("Demo").addModifiers(Modifier.PUBLIC);
        JavaFileHelper fileHelper = JavaFileHelper.build("org.arxing.demo", rootType).setIndentSpaceSize(4);

        MethodHelper demoMethod = MethodHelper.build("demoMethod")
                                              .addAnnotation(Deprecated.class)
                                              .addAnnotation(AnnotationSpec.builder(DemoAnnotation.class)
                                                                           .addMember("testValue", "$S", "Sandy")
                                                                           .build());

        FieldHelper demoField = FieldHelper.build(int.class, "a", Modifier.PUBLIC)
                                           .initializer("<value>", ArgsMap.build().add("value", 1000));

        TypeClassHelper demoNestedClass = TypeHelper.buildClass("Nested")
                                                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                                    .superClass(DemoSuperClass.class);

        TypeEnumHelper demoEnum = TypeHelper.buildEnum("Mode")
                                            .bindField(int.class, "val", Modifier.PUBLIC)
                                            .bindMethod(MethodHelper.buildConstructor()
                                                                    .addParameter(int.class, "val")
                                                                    .addStatement("this.$L=$L", "val", "val"))
                                            .addModifiers(Modifier.PUBLIC)
                                            .addConstant("A", "$L", 100)
                                            .addConstant("B", "$L", 101)
                                            .addConstant("C", "$L", 102);

        TypeInterfaceHelper demoInterface = TypeHelper.buildInterface("Listener")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .bindMethod(MethodHelper.build("onClick")
                                                                              .returns(boolean.class)
                                                                              .addParameter(Object.class, "target"));

        TypeAnnotationHelper demoAnnotation = TypeHelper.buildAnnotation("Anno")
                                                        .addMember(int.class, "id")
                                                        .addMember("Mode", "mode");

        rootType.toClass()
                .bindMethod(demoMethod)
                .bindField(demoField)
                .bindType(demoNestedClass)
                .bindType(demoEnum)
                .bindType(demoInterface)
                .bindType(demoAnnotation);

        MethodHelper demoMethodInvoke = MethodHelper.build("demoMethod");
        rootType.toClass().bindMethod(demoMethodInvoke);
        demoMethodInvoke.addStatement("int a=0");

        fileHelper.create(JavaMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    public static class DemoSuperClass {

    }

    public @interface DemoAnnotation {
        String testValue();
    }
}
