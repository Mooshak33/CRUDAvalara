package org.crud.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Data;

import java.math.BigDecimal;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MonetaryAmount {

    @XmlAttribute(name = "currencyID")
    private String currencyID;

    @XmlValue
    private BigDecimal value;
}
