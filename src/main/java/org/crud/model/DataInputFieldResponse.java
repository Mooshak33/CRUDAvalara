package org.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DataInputFieldResponse {

    @JsonProperty("@nextLink")
    private String nextLink;

    @JsonProperty("value")
    private List<Value> value;
}