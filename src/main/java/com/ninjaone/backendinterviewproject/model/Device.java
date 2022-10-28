package com.ninjaone.backendinterviewproject.model;

import javax.persistence.*;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String systemName;

    @ManyToOne
    DeviceType deviceType;


}