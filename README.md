# FileHelper

![](https://img.shields.io/badge/language-java-orange.svg) 
![](https://img.shields.io/badge/latest-1.0.0-blue.svg)
![](https://img.shields.io/badge/jdk-1.8-yellow.svg)

FileHelper is a library for quickly building java file, xml file. Based on [JavaPoet](https://github.com/square/javapoet)

## Dependency


## Usage

### Java

#### Structure

+ JavaFileHelper
+ TypeHelper
  + TypeClassHelper
  + TypeEnumHelper
  + TypeInterfaceHelper
  + TypeAnnotationHelper
+ MethodHelper
  + MethodKey
+ FieldHelper
+ ArgsMap

#### Example

```java
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
```
The example will create a java file like this:
```java
package org.arxing.demo;

import java.lang.Deprecated;
import java.lang.Object;

public class Demo {
    public int a = 1000;

    @Deprecated @JavaMain.DemoAnnotation(testValue = "Sandy") void demoMethod() {
        int a = 0;
    }

    @interface Anno {
        int id();

        Mode mode();
    }

    public interface Listener {
        boolean onClick(Object target);
    }

    public enum Mode {
        A(100),
        B(101),
        C(102);

        public int val;

        Mode(int val) {
            this.val = val;
        }
    }

    public static class Nested extends JavaMain.DemoSuperClass {
    }
}
```

### Xml(Building)

#### Structure

#### Resources

+ declare-styleable
+ color
+ integer
+ string
+ string-array
+ dimen
+ id




