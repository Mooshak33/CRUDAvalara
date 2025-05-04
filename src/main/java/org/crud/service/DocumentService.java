package org.crud.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.crud.config.OAuth2ClientProperties;
import org.crud.model.DataInputFieldResponse;
import org.crud.model.DocumentResponse;
import org.crud.model.DocumentStatusResponse;
import org.crud.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class DocumentService {
    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    private final RestTemplate restTemplate;
    private final TokenService tokenService;
    private final OAuth2ClientProperties oAuth2ClientProperties;
    public DocumentService(RestTemplate restTemplate, TokenService tokenService,
                           OAuth2ClientProperties oAuth2ClientProperties) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
        this.oAuth2ClientProperties = oAuth2ClientProperties;
    }
    public DocumentResponse getDocument(HttpHeaders headers) {
        try {
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            ResponseEntity<DocumentResponse> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/documents",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    DocumentResponse.class
            );
            DocumentResponse documentResponse = response.getBody();
            if (documentResponse != null) {
                 log.info("Successfully fetched mandates: {}", documentResponse);
                return documentResponse;
            } else {
                  log.error("Failed to fetch mandates, response body is null");
            }
        } catch (Exception e) {
              log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
        }
        return null;
    }

    public DocumentStatusResponse getDocumentStatus(HttpHeaders headers, String id) {
        try {
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            ResponseEntity<DocumentStatusResponse> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/documents/" + id + "/status",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    DocumentStatusResponse.class
            );
            DocumentStatusResponse documentResponse = response.getBody();
            if (documentResponse != null) {
                 log.info("Successfully fetched mandates: {}", documentResponse);
                return documentResponse;
            } else {
                  log.error("Failed to fetch mandates, response body is null");
            }
        } catch (Exception e) {
              log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
        }
        return null;
    }

    public DataInputFieldResponse getDataInputFields(HttpHeaders headers) {
        try {
            headers.setAccept(Collections.singletonList(MediaType.valueOf("application/json")));
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/data-input-fields",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
            String dataInputFieldResponse = response.getBody();
            if (dataInputFieldResponse != null) {
                 log.info("Successfully fetched mandates: {}", dataInputFieldResponse);
                return null;
            } else {
                  log.error("Failed to fetch mandates, response body is null");
            }
        } catch (Exception e) {
              log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
        }
        return null;
    }

    public Invoice getDataDocumentDownload(HttpHeaders headers,String id) {
        try {
            // Set the Accept header to a supported value
            headers.setAccept(Collections.singletonList(MediaType.valueOf("application/vnd.oasis.ubl+xml")));
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/documents/" + id + "/$download",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                log.info("Raw response body: {}", responseBody);

                // Deserialize the XML response into the Invoice object using JAXB
                JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(responseBody);
                Invoice invoice = (Invoice) unmarshaller.unmarshal(reader);

                if (invoice != null) {
                    log.info("Successfully fetched Invoice: {}", invoice);
                    return invoice;
                } else {
                    log.error("Failed to deserialize Invoice, response body is null");
                }
            } else {
                log.error("Failed to fetch Invoice, HTTP status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error fetching Invoice from Avalara API: {}", e.getMessage(), e);
        }
        return null;
    }



}

