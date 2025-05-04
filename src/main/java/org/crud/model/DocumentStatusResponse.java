package org.crud.model;

import lombok.Data;

import java.util.List;

@Data
public class DocumentStatusResponse {
    private String id;
    private String companyId;
    private String status;
    private List<Event> events;
}
