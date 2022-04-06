package com.ninjaone.backendinterviewproject.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SampleEntity {
    @Id
    String id;
    String value;
}
