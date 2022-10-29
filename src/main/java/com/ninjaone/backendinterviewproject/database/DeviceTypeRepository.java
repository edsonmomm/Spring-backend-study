package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.DeviceType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeviceTypeRepository extends CrudRepository<DeviceType, Integer> {

    /**
     * Find and return de device type by name
     *
     * @param deviceName
     * @return
     */
    Optional<DeviceType> findByDeviceName(String deviceName);
}
