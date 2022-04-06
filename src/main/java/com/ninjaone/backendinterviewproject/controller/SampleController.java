package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.SampleEntity;
import com.ninjaone.backendinterviewproject.service.SampleService;
import org.springframework.web.bind.annotation.*;

@RestController("/sample")
public class SampleController {
    private SampleService sampleService;

    @PostMapping
    private SampleEntity postSampleEntity(@RequestBody SampleEntity sample){
        return sampleService.saveSampleEntity(sample);
    }

    @GetMapping("/id/{id}")
    private SampleEntity getSampleEntity(@PathVariable String id){
        return sampleService.getSampleEntity(id)
                .orElseThrow();
    }
}
