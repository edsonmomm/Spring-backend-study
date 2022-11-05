package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.BackendInterviewProjectApplication;
import com.ninjaone.backendinterviewproject.model.Sample;
import com.ninjaone.backendinterviewproject.security.BasicAuthWebSecurityConfiguration;
import com.ninjaone.backendinterviewproject.service.SampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BackendInterviewProjectApplication.class})
@WebMvcTest(SampleController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Import(BasicAuthWebSecurityConfiguration.class)
public class SampleControllerTest {
    public static final String ID = "12345";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private SampleService sampleService;

    private Sample sampleEntity;
    HttpHeaders httpHeaders= new HttpHeaders();

    @BeforeEach
    void setup(){
        sampleEntity = new Sample(ID, "value");
        httpHeaders.add("Authorization","Basic " + ControllerUtils.getEncodedPassword());
    }

    @Test
    void getSampleData() throws Exception {
        when(sampleService.getSampleEntity(ID)).thenReturn(Optional.of(sampleEntity));

        mockMvc.perform(get("/sample/" + ID).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(sampleEntity)));
    }

    @Test
    void postSampleData() throws Exception {
        when(sampleService.saveSampleEntity(any())).thenReturn(sampleEntity);

        String sampleEntityString = objectMapper.writeValueAsString(sampleEntity);
        mockMvc.perform(post("/sample")
                        .headers(httpHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEntityString))
                .andExpect(status().isCreated())
                .andExpect(content().string(sampleEntityString));
    }

    @Test
    void deleteSampleData() throws Exception {
        doNothing().when(sampleService).deleteSampleEntity(ID);

        mockMvc.perform(delete("/sample/" + ID).headers(httpHeaders))
                .andExpect(status().isNoContent());
    }
}
