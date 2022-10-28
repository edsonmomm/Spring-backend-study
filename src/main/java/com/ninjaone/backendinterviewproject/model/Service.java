package com.ninjaone.backendinterviewproject.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * To define services that the customer can choose when buying an device
 * Holds services or products the device offers
 */
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String description;

    BigDecimal value;

    /**
     * Means that the item must exists on the device package
     */
    Boolean mandatory;

    @ManyToOne
    DeviceType deviceType;

}
