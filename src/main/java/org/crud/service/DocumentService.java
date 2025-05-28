package org.crud.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.crud.config.OAuth2ClientProperties;
import org.crud.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.Collections;
import java.util.Map;

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
    /**
     * Get all documents
     *
     * @param headers the HTTP headers
     * @return the document response
     */
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

    /**
     * Get document status by ID
     *
     * @param headers the HTTP headers
     * @param id      the document ID
     * @return the document status response
     */
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

    public DocumentSubmitResponse submitDocument(HttpHeaders headers, Map<String, Object> submitDocument) {
        try {
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<DocumentSubmitResponse> response = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/documents",
                    HttpMethod.POST,
                    new HttpEntity<>(submitDocument, headers),
                    DocumentSubmitResponse.class
            );
            DocumentSubmitResponse documentResponse = response.getBody();
            if (documentResponse != null) {
                 log.info("Successfully submitted document: {}", documentResponse);
                return documentResponse;
            } else {
                  log.error("Failed to submit document, response body is null");
            }
        } catch (Exception e) {
              log.error("Error submitting document to Avalara API: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Get data input fields
     *
     * @param headers the HTTP headers
     * @return the data input field response
     */
    public DataInputFieldResponse getDataInputFields(HttpHeaders headers,String filter) {
        try {
            headers.setAccept(Collections.singletonList(MediaType.valueOf(APPLICATION_JSON_VALUE)));
            // Get the access token
            String accessToken = tokenService.getAccessToken(oAuth2ClientProperties.getTokenUrl(), oAuth2ClientProperties.getClientId(), oAuth2ClientProperties.getClientSecret(),oAuth2ClientProperties.getGrantType());
            // Set the Authorization header
            headers.setBearerAuth(accessToken);
            ResponseEntity<DataInputFieldResponse> dataInputFieldResponse = restTemplate.exchange(
                    "https://api.sbx.avalara.com/einvoicing/data-input-fields?$filter=" + filter,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    DataInputFieldResponse.class
            );
            if (dataInputFieldResponse.getStatusCode() == HttpStatus.OK) {
                DataInputFieldResponse responseBody = dataInputFieldResponse.getBody();
                log.info("Raw response body: {}", responseBody);

                if (responseBody != null) {
                    log.info("Successfully fetched Invoice: {}", responseBody);
                    return responseBody;
                } else {
                    log.error("Failed to deserialize Invoice, response body is null");
                }
            } else {
                log.error("Failed to fetch Invoice, HTTP status: {}", dataInputFieldResponse.getStatusCode());
            }
        } catch (Exception e) {
              log.error("Error fetching document Input Fields from Avalara API: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Get data document download
     *
     * @param headers the HTTP headers
     * @param id      the document ID
     * @return the invoice object
     */
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

