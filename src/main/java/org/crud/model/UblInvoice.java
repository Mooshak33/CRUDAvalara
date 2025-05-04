package org.crud.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;


@XmlRootElement(name = "Invoice", namespace = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class UblInvoice {

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String customizationID;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String profileID;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String ID;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String issueDate;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String dueDate;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String invoiceTypeCode;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String documentCurrencyCode;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private AccountingSupplierParty accountingSupplierParty;

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private AccountingCustomerParty accountingCustomerParty;

    @XmlElement(name = "InvoiceLine", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private List<InvoiceLine> invoiceLines;

    // Getters and setters for all fields
    // ...
}