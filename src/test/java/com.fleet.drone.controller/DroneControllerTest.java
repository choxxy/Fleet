package com.fleet.drone.controller;

import com.fleet.CustomUtils;
import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.service.DroneService;
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
public class DroneControllerTest {
    private static final String ENDPOINT_URL = "/api/drone";
    private static final String REGISTER_ENDPOINT_URL = "/api/drone/register";
    private static final String FIND_ALL_ENDPOINT_URL = "/api/drone/";

    @InjectMocks
    private DroneController droneController;
    @Mock
    private DroneService droneService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(droneController)
                .build();
    }

    @Test
    public void findAll() throws Exception {
        List<DroneDto> list = Collections.singletonList(DroneBuilder.getDto());

        Mockito.when(droneService.findAll()).thenReturn(list);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(FIND_ALL_ENDPOINT_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

        Mockito.verify(droneService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(droneService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(droneService.findBySerialNumber(ArgumentMatchers.anyString())).thenReturn(DroneBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber", Is.is("1")));
        Mockito.verify(droneService, Mockito.times(1)).findBySerialNumber("1");
        Mockito.verifyNoMoreInteractions(droneService);
    }

    @Test
    public void register() throws Exception {
        Mockito.when(droneService.register(ArgumentMatchers.any(DroneDto.class))).thenReturn(DroneBuilder.getDto());

        mockMvc.perform(
                MockMvcRequestBuilders.post(REGISTER_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(DroneBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(droneService, Mockito.times(1)).register(ArgumentMatchers.any(DroneDto.class));
        Mockito.verifyNoMoreInteractions(droneService);
    }

}