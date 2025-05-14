package org.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MandateResponse {
    //@JsonProperty("nextLink")
    private String nextLink;
    private List<Mandate> value;
}
