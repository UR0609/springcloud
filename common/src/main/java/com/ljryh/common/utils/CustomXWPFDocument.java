package com.ljryh.common.utils;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ljryh
 * @version 1.0
 * @date 2023/7/7 10:40
 */
public class CustomXWPFDocument extends XWPFDocument {

    public CustomXWPFDocument(InputStream in) throws IOException {
        super(in);
    }

    public CustomXWPFDocument() {
        super();
    }

    public CustomXWPFDocument(OPCPackage pkg) throws IOException {
        super(pkg);
    }
}