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
    /**
     * Get all documents
     *
     * @param avalaraVersion the Avalara version
     * @param avalaraClient  the Avalara client
     * @return the document response
     */
    @GetMapping
    public ResponseEntity<DocumentResponse> getDocuments(@RequestHeader("avalara-version") String avalaraVersion,
                                                        @RequestHeader("X-Avalara-Client") String avalaraClient) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(documentService.getDocument(headers));
    }

    /**
     * Get document status by ID
     *
     * @param avalaraVersion the Avalara version
     * @param avalaraClient  the Avalara client
     * @param id             the document ID
     * @return the document status response
     */
    @GetMapping("{id}/status")
    public ResponseEntity<DocumentStatusResponse> getDocumentStatus(@RequestHeader("avalara-version") String avalaraVersion,
                                                                    @RequestHeader("X-Avalara-Client") String avalaraClient,
                                                                    @PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(documentService.getDocumentStatus(headers, id));
    }

    /**
     * Get data input fields
     *
     * @param avalaraVersion the Avalara version
     * @param avalaraClient  the Avalara client
     * @return the data input field response
     */
    @GetMapping("/data-input-fields")
    public ResponseEntity<DataInputFieldResponse> getDataInputFields(@RequestHeader("avalara-version") String avalaraVersion,
                                                                     @RequestHeader("X-Avalara-Client") String avalaraClient,
                                                                     @RequestParam(value = "$filter", required = false) String filter,
                                                                     @RequestParam(value = "$top", required = false) Integer top,
                                                                     @RequestParam(value = "$skip", required = false) Integer skip
                                                                     ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(documentService.getDataInputFields(headers,filter));
    }

    /**
     * Get document download by ID
     *
     * @param avalaraVersion the Avalara version
     * @param avalaraClient  the Avalara client
     * @param id             the document ID
     * @return the invoice
     */
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
