package org.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryMandate {

    @JsonProperty("countryMandate")
    private String countryMandate;
}
