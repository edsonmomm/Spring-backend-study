package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.ICostByDeviceDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {

    Optional<Device> findBySystemName(String systemName);

    @Query(value = "SELECT D.SYSTEM_NAME as systemName \n" +
                   "      ,DT.DEVICE_NAME as deviceTypeName \n" +
                   "      ,D.CUSTOMER_ID as customerId \n" +
                   "      ,SUM(SC.VALUE) as costValue \n" +
                   "  FROM DEVICE D \n" +
                   " INNER JOIN DEVICE_TYPE DT \n" +
                   "         ON DT.ID = D.DEVICE_TYPE_ID \n" +
                   "INNER JOIN SERVICE_COST SC \n" +
                   "        ON SC.DEVICE_TYPE_ID = DT.ID \n" +
                   "INNER JOIN DEVICE_SERVICE_COST DI \n" +
                   "         ON DI.DEVICE_ID = D.ID \n" +
                   "     AND DI.CHOSEN_SERVICE_COST_ID = SC.ID \n" +
                   "GROUP BY D.SYSTEM_NAME \n" +
                   "        ,DT.DEVICE_NAME \n" +
                   "        ,D.CUSTOMER_ID",nativeQuery = true)
    List<ICostByDeviceDTO> getCostByDevice();
}