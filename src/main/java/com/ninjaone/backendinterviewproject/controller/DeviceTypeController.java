package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.dto.DeviceTypeDTO;
import com.ninjaone.backendinterviewproject.model.dto.NewDeviceTypeRequest;
import com.ninjaone.backendinterviewproject.model.dto.UpdateDeviceTypeRequest;
import com.ninjaone.backendinterviewproject.service.DeviceTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deviceType")
public class DeviceTypeController {

    private final DeviceTypeService deviceTypeService;

    public DeviceTypeController(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    /**
     * return the device type by its id or
     * http with no content status when not found
     */
    @GetMapping("/{id}")
    private ResponseEntity<DeviceTypeDTO> getDeviceById(@PathVariable Integer id) {
        DeviceTypeDTO deviceTypeDTO = deviceTypeService.getDeviceTypeById(id);
        return new ResponseEntity<>(deviceTypeDTO, HttpStatus.OK);
    }

    /**
     * Create a device type
     * @param newDeviceTypeRequest
     * @return
     */
    @PostMapping("/create")
    private ResponseEntity<DeviceTypeDTO> createDeviceType(@RequestBody NewDeviceTypeRequest newDeviceTypeRequest) {
        DeviceTypeDTO deviceTypeDTO = deviceTypeService.createDeviceType(newDeviceTypeRequest);
        return new ResponseEntity<>(deviceTypeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    private ResponseEntity<DeviceTypeDTO> updateDeviceType(@RequestBody UpdateDeviceTypeRequest updateDeviceTypeRequest) {
        DeviceTypeDTO deviceTypeDTO = deviceTypeService.updateDeviceType(updateDeviceTypeRequest);
        return new ResponseEntity<>(deviceTypeDTO, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteDeviceType(@PathVariable Integer id) {
        deviceTypeService.deleteDeviceType(id);
    }
}