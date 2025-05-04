package org.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequiredWhenField {
    @JsonProperty("scenario")
    private String scenario;
}
