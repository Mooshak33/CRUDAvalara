package org.crud.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class AccountingSupplierParty {

    @XmlElement(namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    private Party party;

    // Getters and setters
}
