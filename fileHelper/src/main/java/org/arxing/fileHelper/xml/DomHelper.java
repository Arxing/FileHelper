package org.arxing.fileHelper.xml;


import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;


public class DomHelper {
    Document doc = new Document();
    ElementHelper root;
    String fileName;
    int indentSpaceSize = 4;

    private DomHelper(ElementHelper root) {
        this.root = root;
        this.doc.setRootElement(root);
        root.domHelper = this;
    }

    public static DomHelper build(ElementHelper rootHelper) {
        return new DomHelper(rootHelper);
    }

    private XMLOutputter buildOutputter() {
        XMLOutputter outputter = new XMLOutputter();
        Format outputFormat = Format.getPrettyFormat();
        outputFormat.setIndent(Stream.range(0, indentSpaceSize).map(v -> " ").collect(Collectors.joining()));
        outputter.setFormat(outputFormat);
        return outputter;
    }

    public void create(Filer filer) {
        try {
            XMLOutputter outputter = buildOutputter();
            JavaFileObject javaFileObject = filer.createSourceFile(fileName);
            OutputStream os = javaFileObject.openOutputStream();
            outputter.output(doc, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(File file) {
        try {
            XMLOutputter outputter = buildOutputter();
            outputter.output(doc, new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(String dir, String fileName) {
        create(new File(dir, fileName));
    }


}
