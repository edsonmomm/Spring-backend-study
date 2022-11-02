package com.ninjaone.backendinterviewproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Device> devices;
}