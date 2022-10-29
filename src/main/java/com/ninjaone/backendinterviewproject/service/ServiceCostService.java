package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.database.ServiceCostRepository;
import com.ninjaone.backendinterviewproject.exception.BusinessException;
import com.ninjaone.backendinterviewproject.exception.DeviceTypeNotFoundException;
import com.ninjaone.backendinterviewproject.exception.ServiceCostNotFoundException;
import com.ninjaone.backendinterviewproject.model.DeviceType;
import com.ninjaone.backendinterviewproject.model.ServiceCost;
import com.ninjaone.backendinterviewproject.model.dto.NewServiceCostRequest;
import com.ninjaone.backendinterviewproject.model.dto.ServiceCostDTO;
import com.ninjaone.backendinterviewproject.model.dto.UpdateServiceCostRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceCostService {
    private final ModelMapper modelMapper;

    private final ServiceCostRepository serviceCostRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    public ServiceCostService(ModelMapper modelMapper, ServiceCostRepository serviceCostRepository, DeviceTypeRepository deviceTypeRepository) {
        this.serviceCostRepository = serviceCostRepository;
        this.deviceTypeRepository = deviceTypeRepository;
        this.modelMapper = modelMapper;
        if (modelMapper.getConfiguration()!= null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    /**
     * Return the service cost by ID
     * @param id
     * @return
     */
    public ServiceCostDTO getServiceCostById(Integer id){
        ServiceCost serviceCost = serviceCostRepository.findById(id).orElseThrow(() -> new ServiceCostNotFoundException(id) );

        return modelMapper.map(serviceCost, ServiceCostDTO.class);
    }

    /**
     * Creates a service cost
     * throw exception if the service already exists with same description and device type
     */
    public ServiceCostDTO createServiceCost(NewServiceCostRequest newServiceCostRequest) {
        // Verify if the chosen device type exists
        DeviceType deviceType = deviceTypeRepository.findById(newServiceCostRequest.getDeviceTypeId())
                .orElseThrow(() -> new DeviceTypeNotFoundException(newServiceCostRequest.getDeviceTypeId()));


        // Verify if already exists a service with same name for a device type
        Optional<ServiceCost> serviceCostExists = serviceCostRepository.findByDescriptionAndDeviceTypeId(
                newServiceCostRequest.getDescription(), newServiceCostRequest.getDeviceTypeId());

        if (serviceCostExists.isPresent())
            throw new BusinessException("Service Cost " + newServiceCostRequest.getDescription() + " already exists for this device");

        ServiceCost serviceCost = modelMapper.map(newServiceCostRequest, ServiceCost.class);
        serviceCost.setDeviceType(deviceType);
        serviceCost = serviceCostRepository.save(serviceCost);

        return modelMapper.map(serviceCost, ServiceCostDTO.class);
    }

    /**
     * Creates the service cost
     * throw exception if the service already exists with same description and device type
     */
    public ServiceCostDTO updateServiceCost(UpdateServiceCostRequest updateServiceCostRequest) {
        // Verify if the chosen device type exists
        DeviceType deviceType = deviceTypeRepository.findById(updateServiceCostRequest.getDeviceTypeId())
                .orElseThrow(() -> new DeviceTypeNotFoundException(updateServiceCostRequest.getDeviceTypeId()));

        // Verify if the Service Cost exists
        ServiceCost serviceCostExists = serviceCostRepository.findById(updateServiceCostRequest.getId())
                .orElseThrow(() -> new ServiceCostNotFoundException(updateServiceCostRequest.getId()) );

        // Verify if already exists another service with same description for the device type
        Optional<ServiceCost> serviceCostDescriptionExists = serviceCostRepository.findByIdNotAndDescriptionAndDeviceTypeId(
                updateServiceCostRequest.getId(), updateServiceCostRequest.getDescription(), updateServiceCostRequest.getDeviceTypeId());

        // Don't update if already exists another service cost with same name for the device
        if (serviceCostDescriptionExists.isPresent())
            throw new BusinessException("Service Cost " + updateServiceCostRequest.getDescription() + " already exists for this device");

        ServiceCost serviceCost = modelMapper.map(updateServiceCostRequest, ServiceCost.class);
        serviceCost.setDeviceType(deviceType);
        serviceCost = serviceCostRepository.save(serviceCost);

        return modelMapper.map(serviceCost, ServiceCostDTO.class);
    }

    /**
     * Delete a service cost
     * As the Service Cost ID is sequential, can delete by its id
     * It won't repeat for different device types
     *
     * @param serviceCostId
     */
    public void deleteServiceCost(Integer serviceCostId) {
        ServiceCost serviceCostExists = serviceCostRepository.findById(serviceCostId).orElseThrow(() -> new ServiceCostNotFoundException(serviceCostId) );
        try {
            serviceCostRepository.deleteById(serviceCostExists.getId());
        } catch (DataIntegrityViolationException e){
            throw new BusinessException(String.format("Service Cost with ID %s is been used. Cannot be deleted.", serviceCostId));
        }
    }
}