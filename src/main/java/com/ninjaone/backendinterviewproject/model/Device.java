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
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String systemName;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DeviceType deviceType;

    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER)
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<DeviceServiceCost> deviceServiceCosts;
}