package org.crud.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemIdentificationType", propOrder = {
        "id"
})
@Data
public class ItemIdentificationType {

    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    private String id;

    @XmlAttribute
    private String schemeID;

    @XmlAttribute
    private String schemeName;

    // Getters, setters, toString
}
