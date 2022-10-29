package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DeviceRepository  extends CrudRepository<Device, Integer> {

    /**
     * return the cost of all devices
     * considering that all items available would be selected.
     *
     * @return BigDecimal
     */
    @Query(value =
            "SELECT ISNULL(SUM(SC.VALUE),0) totalCost \n" +
            "  FROM DEVICE D\n" +
            " INNER JOIN DEVICE_TYPE DT\n" +
            "         ON DT.ID = D.DEVICE_TYPE_ID\n" +
            "INNER JOIN SERVICE SC\n" +
            "        ON SC.DEVICE_TYPE_ID = DT.ID", nativeQuery = true)
    BigDecimal getCompleteDeviceTotalCost();

    /**
     * return the cost for all devices
     * considering only the services selected.
     *
     * @return BigDecimal
     */
    @Query(value =
            "SELECT ISNULL(SUM(SC.VALUE),0) totalCost \n" +
            "  FROM DEVICE D\n" +
            " INNER JOIN DEVICE_TYPE DT\n" +
            "         ON DT.ID = D.DEVICE_TYPE_ID\n" +
            "INNER JOIN SERVICE SC\n" +
            "        ON SC.DEVICE_TYPE_ID = DT.ID\n" +
            "INNER JOIN DEVICE_SERVICE DI\n" +
            "        ON DI.DEVICE_ID = D.ID\n" +
            "       AND DI.CHOSEN_SERVICE_ID = SC.ID", nativeQuery = true)
    BigDecimal getDeviceTotalCost();
}