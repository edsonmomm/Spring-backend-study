package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.SampleRepository;
import com.ninjaone.backendinterviewproject.model.SampleEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SampleService {
    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public SampleEntity saveSampleEntity(SampleEntity sample){
        return sampleRepository.save(sample);
    }

    public Optional<SampleEntity> getSampleEntity(String id){
        return sampleRepository.findById(id);
    }
    public void deleteSampleEntity(String id) {
        sampleRepository.deleteById(id);
    }
}
