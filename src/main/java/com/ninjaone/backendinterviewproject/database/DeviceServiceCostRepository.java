package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.DeviceServiceCost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceServiceCostRepository extends CrudRepository<DeviceServiceCost, Integer> {

    List<DeviceServiceCost> findByDeviceId(Integer deviceId);
}
