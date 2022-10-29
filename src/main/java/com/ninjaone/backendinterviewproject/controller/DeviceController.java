package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.TotalCostDTO;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * return the device by its id or
     * http with no content status when not found
     */
    @GetMapping("/{id}")
    private ResponseEntity<Device> getDeviceEntity(@PathVariable Integer id) {
        Optional<Device> device = deviceService.getDeviceEntity(id);
        return device.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    @GetMapping("/totalCost")
    private ResponseEntity<TotalCostDTO> getTotalCost() {
        TotalCostDTO totalCostDTO = new TotalCostDTO();

        BigDecimal totalCost = deviceService.getTotalCost();
        totalCostDTO.setTotalCost(totalCost);

        return new ResponseEntity<>(totalCostDTO, HttpStatus.OK);
    }
}
