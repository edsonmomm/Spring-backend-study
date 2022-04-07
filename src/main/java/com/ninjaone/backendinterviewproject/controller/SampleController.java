package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.Sample;
import com.ninjaone.backendinterviewproject.service.SampleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/sample")
public class SampleController {
    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Sample postSampleEntity(@RequestBody Sample sample){
        return sampleService.saveSampleEntity(sample);
    }

    @GetMapping("/{id}")
    private Sample getSampleEntity(@PathVariable String id){
        return sampleService.getSampleEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteSampleEntity(@PathVariable String id){
        sampleService.deleteSampleEntity(id);
    }
}
