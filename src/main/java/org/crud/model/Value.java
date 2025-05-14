package org.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Value {
    private String id;
    private int fieldId;
    private String pathType;
    private List<String> applicableDocumentRoots;
    private String path;
    private String enumsFixedOrText;
    private String exampleOrFixedValue;
   // @JsonProperty("acceptedValues")
 //   private Object acceptedValues; // Changed from Map to HashMap
    private boolean isSegment;

    @JsonProperty("requiredFor")
    private List<CountryMandate> requiredFor; // Changed from Map to HashMap

    @JsonProperty("conditionalFor")
    private List<CountryMandate> conditionalFor; // Changed from Map to HashMap

    @JsonProperty("notUsedFor")
    private List<CountryMandate> notUsedFor; // Changed from Map to HashMap

    @JsonProperty("optionalFor")
    private List<CountryMandate> optionalFor; // Changed from Map to HashMap
}