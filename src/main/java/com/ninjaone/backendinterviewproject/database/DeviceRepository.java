package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.ICostByDeviceDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {

    Optional<Device> findBySystemName(String systemName);

    @Query(value = "SELECT D.SYSTEM_NAME as systemName \n" +
                   "      ,DT.DEVICE_NAME as deviceTypeName \n" +
                   "      ,SUM(SC.VALUE) as costValue \n" +
                   "  FROM DEVICE D\n" +
                   " INNER JOIN DEVICE_TYPE DT\n" +
                   "         ON DT.ID = D.DEVICE_TYPE_ID\n" +
                   "INNER JOIN SERVICE_COST SC\n" +
                   "        ON SC.DEVICE_TYPE_ID = DT.ID\n" +
                   "INNER JOIN DEVICE_SERVICE_COST DI\n" +
                   "         ON DI.DEVICE_ID = D.ID\n" +
                   "     AND DI.CHOSEN_SERVICE_COST_ID = SC.ID\n" +
                   "GROUP BY D.SYSTEM_NAME\n" +
                   "        ,DT.DEVICE_NAME" ,nativeQuery = true)
    List<ICostByDeviceDTO> getCostByDevice();

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
            "INNER JOIN SERVICE_COST SC\n" +
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
            "INNER JOIN SERVICE_COST SC\n" +
            "        ON SC.DEVICE_TYPE_ID = DT.ID\n" +
            "INNER JOIN DEVICE_SERVICE_COST DI\n" +
            "        ON DI.DEVICE_ID = D.ID\n" +
            "       AND DI.CHOSEN_SERVICE_COST_ID = SC.ID", nativeQuery = true)
    BigDecimal getDeviceTotalCost();
}