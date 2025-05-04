package org.crud.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryType", propOrder = {
        "identificationCode"
})
@Data
public class Country {

    @XmlElement(name = "IdentificationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String identificationCode;

    // Constructors
    public Country() {
    }

    public Country(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    // toString() for debugging
    @Override
    public String toString() {
        return "Country{" +
                "identificationCode='" + identificationCode + '\'' +
                '}';
    }
}
