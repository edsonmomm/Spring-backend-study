package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.SampleRepository;
import com.ninjaone.backendinterviewproject.model.Sample;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SampleService {
    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public Sample saveSampleEntity(Sample sample){
        return sampleRepository.save(sample);
    }

    public Optional<Sample> getSampleEntity(String id){
        return sampleRepository.findById(id);
    }
    public void deleteSampleEntity(String id) {
        sampleRepository.deleteById(id);
    }
}
