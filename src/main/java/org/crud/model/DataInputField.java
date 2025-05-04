package org.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class DataInputField {
    @JsonProperty("id")
    private String id;
    @JsonProperty("fieldId")
    private int fieldId;
    @JsonProperty("pathType")
    private String pathType;
    @JsonProperty("applicableDocumentRoots")
    private List<String> applicableDocumentRoots;
    @JsonProperty("path")
    private String path;
    @JsonProperty("nameSpace")
    private String nameSpace;
    @JsonProperty("fieldName")
    private String fieldName;
    @JsonProperty("enumsFixedOrText")
    private String enumsFixedOrText;
    @JsonProperty("exampleOrFixedValue")
    private String exampleOrFixedValue;
    @JsonProperty("acceptedValues")
    private Map<String, String> acceptedValues;
    @JsonProperty("documentationLink")
    private String documentationLink;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isSegment")
    private boolean isSegment;
    @JsonProperty("requiredFor")
    private List<Response> requiredFor;
    @JsonProperty("conditionalForField")
    private List<ConditionalForField> conditionalForField;
    @JsonProperty("notUsedFor")
    private List<Response> notUsedFor;
    @JsonProperty("optionalFor")
    private List<Response> optionalFor;
}
