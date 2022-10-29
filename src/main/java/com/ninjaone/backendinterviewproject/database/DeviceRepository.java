package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DeviceRepository  extends CrudRepository<Device, Integer> {

    @Query(value = "SELECT SUM(SC.VALUE) totalCost \n" +
                   "  FROM DEVICE D\n" +
                   " INNER JOIN DEVICE_TYPE DT\n" +
                   "         ON DT.ID = D.DEVICE_TYPE_ID\n" +
                   "INNER JOIN SERVICE SC\n" +
                   "        ON SC.DEVICE_TYPE_ID = DT.ID", nativeQuery = true)
    BigDecimal getTotalCost();
}