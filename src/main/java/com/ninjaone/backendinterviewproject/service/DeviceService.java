package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.*;
import com.ninjaone.backendinterviewproject.exception.*;
import com.ninjaone.backendinterviewproject.model.*;
import com.ninjaone.backendinterviewproject.model.dto.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final ModelMapper modelMapper;

    private final DeviceRepository deviceRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    private final ServiceCostRepository serviceCostRepository;

    private final DeviceServiceCostRepository deviceServiceCostRepository;
    private final CustomerRepository customerRepository;

    public DeviceService(ModelMapper modelMapper, DeviceRepository deviceRepository,
                         DeviceTypeRepository deviceTypeRepository, ServiceCostRepository serviceCostRepository,
                         DeviceServiceCostRepository deviceServiceCostRepository,
                         CustomerRepository customerRepository) {

        this.deviceRepository = deviceRepository;
        this.deviceTypeRepository = deviceTypeRepository;
        this.serviceCostRepository = serviceCostRepository;
        this.deviceServiceCostRepository = deviceServiceCostRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        if (modelMapper.getConfiguration()!= null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    /**
     * Return device by id.
     * Return exception if not found
     *
     * @param id  Device Id
     * @return DeviceDTO
     */
    public DeviceDTO getDeviceById(Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id) );

        DeviceDTO deviceDTO = modelMapper.map(device, DeviceDTO.class);

        deviceDTO.setServices(new ArrayList<>());
        if (device.getDeviceServiceCosts() != null && !device.getDeviceServiceCosts().isEmpty()) {
            for (DeviceServiceCost deviceServiceCost : device.getDeviceServiceCosts()) {
                if (deviceServiceCost.getChosenServiceCost() != null) {
                    ServiceCostDTO serviceCostDTO = modelMapper.map(deviceServiceCost.getChosenServiceCost(), ServiceCostDTO.class);
                    deviceDTO.getServices().add(serviceCostDTO);
                }
            }
        }
        return deviceDTO;
    }

    /**
     * Return the cost value by device
     *
     * @return List ICostByDeviceDTO 
     */
    public List<CostByDeviceDTO> getCostByDevice() {
        List<CostByDeviceDTO> costByDeviceDTOList = new ArrayList<>();
        List<ICostByDeviceDTO> iCostByDeviceDTOList = deviceRepository.getCostByDevice();

        for (ICostByDeviceDTO iCostByDeviceDTO : iCostByDeviceDTOList) {
            CostByDeviceDTO costByDeviceDTO = modelMapper.map(iCostByDeviceDTO, CostByDeviceDTO.class);
            Customer customer = customerRepository.findById(iCostByDeviceDTO.getCustomerId()).get();
            costByDeviceDTO.setCustomer(modelMapper.map(customer, CustomerDTO.class));

            costByDeviceDTOList.add(costByDeviceDTO);
        }
        return costByDeviceDTOList;
    }

    // Create
    public DeviceDTO createDevice(@RequestBody NewDeviceRequest newDeviceRequest) {
        // Verify if the device type exists
        DeviceType deviceType = deviceTypeRepository.findById(newDeviceRequest.getDeviceTypeId())
                .orElseThrow(() -> new DeviceTypeNotFoundException(newDeviceRequest.getDeviceTypeId()));

        // Verify if customer exists
        Customer customer = customerRepository.findById(newDeviceRequest.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(newDeviceRequest.getCustomerId()));

        // Verify if exists another device with same name
        Optional<Device> deviceExists = deviceRepository.findBySystemName(newDeviceRequest.getSystemName());
        if (deviceExists.isPresent())
            throw new BusinessException("Device " + newDeviceRequest.getSystemName() + " already exists");

        Device device = modelMapper.map(newDeviceRequest, Device.class);
        device.setDeviceType(deviceType);
        device.setCustomer(customer);

        device = deviceRepository.save(device);
        // Get services chosen
        if (newDeviceRequest.getDeviceServiceRequests() != null && !newDeviceRequest.getDeviceServiceRequests().isEmpty()) {
            device.setDeviceServiceCosts(new ArrayList<>());
            for (NewDeviceServiceRequest newDeviceServiceRequest : newDeviceRequest.getDeviceServiceRequests()) {
                // verify if service exists
                ServiceCost serviceCost = serviceCostRepository.findByIdAndDeviceTypeId(newDeviceServiceRequest.getServiceId(), deviceType.getId())
                        .orElseThrow(() -> new ServiceCostNotFoundException(newDeviceServiceRequest.getServiceId()));

                /* TODO find a better way to treat the connection table */
                // Create the services
                DeviceServiceCost deviceServiceCost = new DeviceServiceCost();
                deviceServiceCost.setDevice(device);
                deviceServiceCost.setChosenServiceCost(serviceCost);
                deviceServiceCost = deviceServiceCostRepository.save(deviceServiceCost);
                device.getDeviceServiceCosts().add(deviceServiceCost);
            }
            device = deviceRepository.save(device);
        }
        // Check if mandatory items were added to the list of chosen services


        // return the dto complete with the items
        return this.getDeviceById(device.getId());
    }

    public DeviceDTO updateDevice(@RequestBody UpdateDeviceRequest updateDeviceRequest) {
        // Verify if the device type exists
        DeviceType deviceType = deviceTypeRepository.findById(updateDeviceRequest.getDeviceTypeId())
                .orElseThrow(() -> new DeviceTypeNotFoundException(updateDeviceRequest.getDeviceTypeId()));

        // Verify if customer exists
        Customer customer = customerRepository.findById(updateDeviceRequest.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(updateDeviceRequest.getCustomerId()));

        Device device = modelMapper.map(updateDeviceRequest, Device.class);
        device.setDeviceType(deviceType);
        device.setCustomer(customer);
        device = deviceRepository.save(device);

        // delete previous services
        deleteAllDeviceServices(device.getId());
        // Get services chosen
        if (updateDeviceRequest.getDeviceServiceRequests() != null && !updateDeviceRequest.getDeviceServiceRequests().isEmpty()) {
            device.setDeviceServiceCosts(new ArrayList<>());
            for (NewDeviceServiceRequest newDeviceServiceRequest : updateDeviceRequest.getDeviceServiceRequests()) {
                // verify if service exists
                ServiceCost serviceCost = serviceCostRepository.findByIdAndDeviceTypeId(newDeviceServiceRequest.getServiceId(), deviceType.getId())
                        .orElseThrow(() -> new ServiceCostNotFoundException(newDeviceServiceRequest.getServiceId()));

                /* TODO find a better way to treat the connection table */
                // Create the services
                DeviceServiceCost deviceServiceCost = new DeviceServiceCost();
                deviceServiceCost.setDevice(device);
                deviceServiceCost.setChosenServiceCost(serviceCost);
                deviceServiceCost = deviceServiceCostRepository.save(deviceServiceCost);
                device.getDeviceServiceCosts().add(deviceServiceCost);
            }
            deviceRepository.save(device);
        }
        return this.getDeviceById(updateDeviceRequest.getId());
    }

    private void deleteAllDeviceServices(Integer deviceId) {
        List<DeviceServiceCost> deviceServiceCostList = deviceServiceCostRepository.findByDeviceId(deviceId);

        for (DeviceServiceCost deviceServiceCost : deviceServiceCostList) {
            deviceServiceCostRepository.deleteById(deviceServiceCost.getId());
        }
    }

    /**
     * Delete the device by its id.
     * If not found, raise exception
     *
     * @param id Device Id
     */
    public void deleteDevice(Integer id) {
        final Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
        try {
            deviceRepository.deleteById(device.getId());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(String.format("Device with ID %s is been used. Cannot be deleted.", device.getId()));
        }
    }
}
