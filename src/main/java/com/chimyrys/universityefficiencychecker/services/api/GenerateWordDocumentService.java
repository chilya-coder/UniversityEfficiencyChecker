package com.chimyrys.universityefficiencychecker.services.api;

public interface GenerateWordDocumentService {
    byte[] generateReport(int yearSince, int yearTo);
    byte[] generateActivityReport();
    byte[] generateInfoReport();
}
