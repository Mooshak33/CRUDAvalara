package org.crud.controller;

import org.crud.controllers.MandateController;
import org.crud.model.MandateResponse;
import org.crud.service.MandateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MandateControllerTest {

    @Mock
    private MandateService mandateService;

    @InjectMocks
    private MandateController mandateController;

    @Test
    void getMandatesReturnsMandateResponseWhenHeadersAndFilterAreValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        MandateResponse mockResponse = new MandateResponse();

        when(mandateService.getMandates(headers, "filter", null, null)).thenReturn(mockResponse);

        ResponseEntity<MandateResponse> response = mandateController.getMandates("1.0", "client", "filter", null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getMandatesReturnsMandateResponseWhenHeadersAreValidAndFilterIsNull() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        MandateResponse mockResponse = new MandateResponse();

        when(mandateService.getMandates(headers, null, null, null)).thenReturn(mockResponse);

        ResponseEntity<MandateResponse> response = mandateController.getMandates("1.0", "client", null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getMandatesReturnsMandateResponseWhenHeadersAreValidAndPaginationIsProvided() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", "1.0");
        headers.add("X-Avalara-Client", "client");
        MandateResponse mockResponse = new MandateResponse();

        when(mandateService.getMandates(headers, null, 10, 5)).thenReturn(mockResponse);

        ResponseEntity<MandateResponse> response = mandateController.getMandates("1.0", "client", null, 10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }
}
