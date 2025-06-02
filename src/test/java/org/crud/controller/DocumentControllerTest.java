package org.crud.controller;

import org.crud.controllers.DocumentController;
import org.crud.model.*;
import org.crud.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @Test
    void getDocumentsReturnsDocumentResponseWhenHeadersAreValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        DocumentResponse mockResponse = new DocumentResponse();

        when(documentService.getDocument(headers)).thenReturn(mockResponse);

        ResponseEntity<DocumentResponse> response = documentController.getDocuments("1.0", "client");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getDocumentStatusReturnsDocumentStatusResponseWhenIdIsValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        DocumentStatusResponse mockResponse = new DocumentStatusResponse();

        when(documentService.getDocumentStatus(headers, "123")).thenReturn(mockResponse);

        ResponseEntity<DocumentStatusResponse> response = documentController.getDocumentStatus("1.0", "client", "123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getDataInputFieldsReturnsDataInputFieldResponseWhenFilterIsProvided() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        DataInputFieldResponse mockResponse = new DataInputFieldResponse();

        when(documentService.getDataInputFields(headers, "filter")).thenReturn(mockResponse);

        ResponseEntity<DataInputFieldResponse> response = documentController.getDataInputFields("1.0", "client", "filter", null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getDocumentDownloadReturnsInvoiceWhenIdIsValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        Invoice mockInvoice = new Invoice();

        when(documentService.getDataDocumentDownload(headers, "123")).thenReturn(mockInvoice);

        ResponseEntity<Invoice> response = documentController.getDocumentDownload("1.0", "client", "123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockInvoice, response.getBody());
    }

   /* @Test
    void submitDocumentsReturnsDocumentSubmitResponseWhenRequestBodyIsValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        Map<String, Object> requestBody = Map.of("key", "value");
        DocumentSubmitResponse mockResponse = new DocumentSubmitResponse();

        when(documentService.submitDocument(headers, requestBody)).thenReturn(mockResponse);

        ResponseEntity<DocumentSubmitResponse> response = documentController.submitDocuments("1.0", "client", requestBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }*/
}
