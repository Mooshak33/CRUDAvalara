package org.crud.controllers;

import org.crud.model.Mandate;
import org.crud.model.MandateResponse;
import org.crud.service.MandateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestParam(value = "$filter", required = false) String filter,
@RequestParam(value = "$top", required = false) Integer top,
@RequestParam(value = "$skip", required = false) Integer skip) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        return ResponseEntity.ok(mandateService.getMandates(headers,filter, top, skip));
    }
}