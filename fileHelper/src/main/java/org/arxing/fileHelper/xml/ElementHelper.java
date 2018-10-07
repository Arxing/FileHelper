package org.arxing.fileHelper.xml;

import com.sun.org.apache.xml.internal.utils.NameSpace;

import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;

import java.util.Collection;

public class ElementHelper extends Element {
    DomHelper domHelper;

    private ElementHelper(String name) {
        super(name);
    }

    public static ElementHelper buildElement(String name) {
        return new ElementHelper(name);
    }

    // namespace

    public ElementHelper addNamespace(String prefix, String uri) {
        Namespace ns = Namespace.getNamespace(prefix, uri);
        addNamespaceDeclaration(ns);
        return this;
    }

    // attr

    public ElementHelper setAttribute(String name, String value, String prefix, String uri) {
        super.setAttribute(name, value, Namespace.getNamespace(prefix,uri));
        return this;
    }

}
