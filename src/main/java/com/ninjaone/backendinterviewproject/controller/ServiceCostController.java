package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.dto.*;
import com.ninjaone.backendinterviewproject.service.ServiceCostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints to handle crud of service costs
 */

@RestController
@RequestMapping("/service")
public class ServiceCostController {
    private final ServiceCostService serviceCostService;

    public ServiceCostController(ServiceCostService serviceCostService) {
        this.serviceCostService = serviceCostService;
    }

    /**
     * return the service by its id or
     * http with no content status when not found
     */
    @GetMapping("/{id}")
    private ResponseEntity<ServiceCostDTO> getServiceCostById(@PathVariable Integer id) {
        ServiceCostDTO serviceCostDTO = serviceCostService.getServiceCostById(id);
        return new ResponseEntity<>(serviceCostDTO, HttpStatus.OK);
    }

    /**
     * Create a service cost type
     * @param newServiceCostRequest
     * @return
     */
    @PostMapping("/create")
    private ResponseEntity<ServiceCostDTO> createServiceCost(@RequestBody NewServiceCostRequest newServiceCostRequest) {
        ServiceCostDTO serviceCostDTO = serviceCostService.createServiceCost(newServiceCostRequest);
        return new ResponseEntity<>(serviceCostDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    private ResponseEntity<ServiceCostDTO> updateServiceCost(@RequestBody UpdateServiceCostRequest updateServiceCostRequest) {
        ServiceCostDTO serviceCostDTO = serviceCostService.updateServiceCost(updateServiceCostRequest);
        return new ResponseEntity<>(serviceCostDTO, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteServiceCost(@PathVariable Integer id) {
        serviceCostService.deleteServiceCost(id);
    }
}