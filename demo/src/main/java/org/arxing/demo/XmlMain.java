package org.arxing.demo;

import org.arxing.fileHelper.xml.DomHelper;
import org.arxing.fileHelper.xml.ElementHelper;


public class XmlMain {

    public static void main(String[] args) {
        String dir = XmlMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String fileName = "ab.xml";


        ElementHelper root = ElementHelper.buildElement("LinearLayout").addNamespace("android", "android.com");
        root.addContent(ElementHelper.buildElement("CustomView").setAttribute("layout_width", "match_parent", "android"));
        DomHelper domHelper = DomHelper.build(root);






        domHelper.create(dir, fileName);


    }
}
