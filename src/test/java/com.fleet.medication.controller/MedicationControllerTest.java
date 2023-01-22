package com.fleet.medication.controller;

import com.fleet.CustomUtils;
import com.fleet.medication.dto.MedicationDto;
import com.fleet.medication.service.MedicationService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Transactional
public class MedicationControllerTest {
    private static final String ENDPOINT_URL = "/api/medication";
    private static final String FIND_ALL_ENDPOINT_URL = "/api/medication/";
    private static final String REGISTER_ENDPOINT_URL = "/api/medication/register";
    @InjectMocks
    private MedicationController medicationController;
    @Mock
    private MedicationService medicationService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(medicationController)
                .build();
    }

    @Test
    public void findAll() throws Exception {
        List<MedicationDto> dtoList = Collections.singletonList(MedicationBuilder.getDto());

        Mockito.when(medicationService.findAll()).thenReturn(dtoList);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(FIND_ALL_ENDPOINT_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

        Mockito.verify(medicationService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(medicationService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(medicationService.findById(ArgumentMatchers.anyInt())).thenReturn(MedicationBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(medicationService, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(medicationService);
    }

    @Test
    public void register() throws Exception {
        Mockito.when(medicationService.save(ArgumentMatchers.any(MedicationDto.class))).thenReturn(MedicationBuilder.getDto());

        mockMvc.perform(
                MockMvcRequestBuilders.post(REGISTER_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(MedicationBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(medicationService, Mockito.times(1)).save(ArgumentMatchers.any(MedicationDto.class));
        Mockito.verifyNoMoreInteractions(medicationService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(medicationService.update(ArgumentMatchers.any(), ArgumentMatchers.anyInt())).thenReturn(MedicationBuilder.getDto());

        mockMvc.perform(
                MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(MedicationBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(medicationService, Mockito.times(1)).update(ArgumentMatchers.any(MedicationDto.class), ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(medicationService);
    }

}