package org.arxing.fileHelper.java;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.squareup.javapoet.TypeName;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.lang.model.element.Modifier;

public class MethodKey {
    private String[] modifiers;
    private String returnType;
    private String methodName;
    private String[] params;

    private class Compare implements Comparator<String> {

        @Override public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    public static MethodKey from(MethodHelper methodHelper) {
        List<TypeName> params = Stream.of(methodHelper.parameters).map(spec -> spec.type).toList();
        return new MethodKey(methodHelper.modifiers, methodHelper.returnType, methodHelper.methodName, params);
    }

    private MethodKey(List<Modifier> modifiers, TypeName returnType, String methodName, List<TypeName> params) {
        this.modifiers = Stream.of(modifiers)
                               .map(Modifier::toString)
                               .sorted(new Compare())
                               .collect(Collectors.toList())
                               .toArray(new String[0]);

        this.returnType = returnType == null ? "" : returnType.toString();
        this.methodName = methodName;
        this.params = Stream.of(params).map(TypeName::toString).sorted(new Compare()).collect(Collectors.toList()).toArray(new String[0]);
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MethodKey methodKey = (MethodKey) o;
        return Arrays.equals(modifiers, methodKey.modifiers) && Objects.equals(returnType, methodKey.returnType) && Objects.equals(
                methodName,
                methodKey.methodName) && Arrays.equals(params, methodKey.params);
    }

    @Override public int hashCode() {
        int result = Objects.hash(returnType, methodName);
        result = 31 * result + Arrays.hashCode(modifiers);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }

    @Override public String toString() {
        return "MethodKey{" + "modifiers=" + Arrays.toString(modifiers) + ", returnType='" + returnType + '\'' + ", methodName='" +
                methodName + '\'' + ", params=" + Arrays
                .toString(params) + '}';
    }
}
