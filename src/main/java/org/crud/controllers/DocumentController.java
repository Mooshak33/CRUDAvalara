package org.crud.controllers;

import org.crud.model.DocumentResponse;
import org.crud.service.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/einvoicing/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<DocumentResponse> getMandates(@RequestHeader("avalara-version") String avalaraVersion,
                                                        @RequestHeader("X-Avalara-Client") String avalaraClient,
                                                        @RequestHeader("Authorization") String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        headers.add("Authorization", authorizationHeader);
        return ResponseEntity.ok(documentService.getDocument(headers));
    }

}
