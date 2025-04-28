package org.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
        private String id;
        private String companyId;
        private LocalDateTime processDateTime;
        private String status;
        private String documentNumber;
        private LocalDateTime documentDate;
        private String flow;
        private String countryCode;
        private String countryMandate;
        private String interfaceName; // "interface" is a reserved word in Java
        private String receiver;
        private String supplierName;
        private String customerName;
}
