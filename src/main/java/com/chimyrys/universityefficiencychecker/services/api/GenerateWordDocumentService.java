package com.chimyrys.universityefficiencychecker.services.api;


import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface GenerateWordDocumentService {
    byte[] createTableWord(int yearSince, int yearTo);
}
