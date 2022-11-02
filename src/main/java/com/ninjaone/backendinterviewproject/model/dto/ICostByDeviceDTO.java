package com.ninjaone.backendinterviewproject.model.dto;

import java.math.BigDecimal;

public interface ICostByDeviceDTO {
    String getSystemName();
    String getDeviceTypeName();
    BigDecimal getCostValue();
    Integer getCustomerId();
}
