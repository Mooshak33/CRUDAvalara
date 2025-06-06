package org.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConditionalForField {
    @JsonProperty("countryMandate")
    private String countryMandate;
    @JsonProperty("requiredWhenField")
    private List<RequiredWhenField> requiredWhen;
}
