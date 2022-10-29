package com.ninjaone.backendinterviewproject.model;

import javax.persistence.*;

/**
 * To hold the items choosen on device purchase
 * Allows the costumer to customize the device with specific items
 */
@Entity
public class DeviceService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    Device device;

    @ManyToOne
    ServiceCost chosenServiceCost;
}
