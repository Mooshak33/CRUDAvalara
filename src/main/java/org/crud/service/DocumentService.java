package org.crud.service;

import org.crud.model.DocumentResponse;
import org.crud.model.MandateResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DocumentService {
    private final RestTemplate restTemplate;
    public DocumentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public DocumentResponse getDocument(HttpHeaders headers) {
        try {
            ResponseEntity<DocumentResponse> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/documents",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    DocumentResponse.class
            );
            DocumentResponse documentResponse = response.getBody();
            if (documentResponse != null) {
                // log.info("Successfully fetched mandates: {}", mandateResponse);
                return documentResponse;
            } else {
                //  log.error("Failed to fetch mandates, response body is null");
            }
        } catch (Exception e) {
            //  log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
        }
        return null;
    }



}

