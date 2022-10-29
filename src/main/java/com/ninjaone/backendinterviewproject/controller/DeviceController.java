package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.model.dto.TotalCostDTO;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    private ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Integer id) {
        DeviceDTO deviceDTO = deviceService.getDeviceById(id);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    /**
     * return the cost of all devices
     * considering that all items available would be selected.
     */
    @GetMapping("/completeDeviceTotalCost")
    private ResponseEntity<TotalCostDTO> getCompleteDeviceTotalCost() {
        TotalCostDTO totalCostDTO = new TotalCostDTO();

        BigDecimal totalCost = deviceService.getCompleteDeviceTotalCost();
        totalCostDTO.setTotalCost(totalCost);

        return new ResponseEntity<>(totalCostDTO, HttpStatus.OK);
    }

    /**
     * return the cost for all devices
     * considering only the services selected.
     */
    @GetMapping("/totalCost")
    private ResponseEntity<TotalCostDTO> getTotalCost() {
        TotalCostDTO totalCostDTO = new TotalCostDTO(deviceService.getDeviceTotalCost());

        return new ResponseEntity<>(totalCostDTO, HttpStatus.OK);
    }
}
