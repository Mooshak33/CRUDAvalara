package org.crud.controllers;

import org.crud.model.MandateResponse;
import org.crud.service.MandateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/einvoicing/mandates")
public class MandateController {

    private final MandateService mandateService;

    public MandateController(MandateService mandateService) {
        this.mandateService = mandateService;
    }

    @GetMapping
    public ResponseEntity<MandateResponse> getMandates(@RequestHeader("avalara-version") String avalaraVersion,
                                                       @RequestHeader("X-Avalara-Client") String avalaraClient,
                                                       @RequestHeader("Authorization") String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        headers.add("Authorization", authorizationHeader);
        return ResponseEntity.ok(mandateService.getMandates(headers));
    }
}