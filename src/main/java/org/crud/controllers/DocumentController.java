package org.crud.controllers;

import org.crud.model.DataInputFieldResponse;
import org.crud.model.DocumentResponse;
import org.crud.model.DocumentStatusResponse;
import org.crud.model.Invoice;
import org.crud.service.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/einvoicing/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<DocumentResponse> getDocuments(@RequestHeader("avalara-version") String avalaraVersion,
                                                        @RequestHeader("X-Avalara-Client") String avalaraClient) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(documentService.getDocument(headers));
    }

    @GetMapping("{id}/status")
    public ResponseEntity<DocumentStatusResponse> getDocumentStatus(@RequestHeader("avalara-version") String avalaraVersion,
                                                                    @RequestHeader("X-Avalara-Client") String avalaraClient,
                                                                    @PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(documentService.getDocumentStatus(headers, id));
    }

    @GetMapping("/data-input-fields")
    public ResponseEntity<DataInputFieldResponse> getDataInputFields(@RequestHeader("avalara-version") String avalaraVersion,
                                                                     @RequestHeader("X-Avalara-Client") String avalaraClient) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(documentService.getDataInputFields(headers));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Invoice> getDocumentDownload(@RequestHeader("avalara-version") String avalaraVersion,
                                                       @RequestHeader("X-Avalara-Client") String avalaraClient,
                                                       @PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(documentService.getDataDocumentDownload(headers, id));
    }

}
