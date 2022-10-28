package com.ninjaone.backendinterviewproject.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class DeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String deviceName;
}
