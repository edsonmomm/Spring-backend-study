package com.ninjaone.backendinterviewproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    /**
     * nationalIdentificationNumber is a national identification and allows to not
     * register the same person twice based on its national ID.
     * As each country has different rules for this number, some accept letters, the atribute was defined as string.
     */
    private String nationalIdentificationNumber;
}