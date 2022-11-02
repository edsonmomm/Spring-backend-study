package com.ninjaone.backendinterviewproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    /**
     * nationalIdentificationNumber is a national identification and allows to not
     * register the same person twice based on its national ID.
     * As each country has different rules for this number, some accept letters, the atribute was defined as string.
     */
    private String nationalIdentificationNumber;
}