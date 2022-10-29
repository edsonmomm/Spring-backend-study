package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.ServiceCost;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServiceCostRepository extends CrudRepository<ServiceCost, Integer> {

    Optional<ServiceCost> findByDescriptionAndDeviceTypeId(String description, Integer deviceTypeId);

    Optional<ServiceCost> findByIdNotAndDescriptionAndDeviceTypeId(Integer id, String description, Integer deviceTypeId);
}