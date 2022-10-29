package com.ninjaone.backendinterviewproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * To hold the items choosen on device purchase
 * Allows the costumer to customize the device with specific items
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceServiceCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Device device;

    @ManyToOne
    private ServiceCost chosenServiceCost;
}
