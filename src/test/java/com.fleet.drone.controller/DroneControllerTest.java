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

@Transactional
public class DroneControllerTest {
    private static final String ENDPOINT_URL = "/api/drone";
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
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                //.addFilter(CustomFilter::doFilter)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<DroneDto> page = new PageImpl<>(Collections.singletonList(DroneBuilder.getDto()));

        Mockito.when(droneService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(droneService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(droneService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(droneService.findById(ArgumentMatchers.anyString())).thenReturn(DroneBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(droneService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(droneService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(droneService.save(ArgumentMatchers.any(DroneDto.class))).thenReturn(DroneBuilder.getDto());

        mockMvc.perform(
                MockMvcRequestBuilders.post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(DroneBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(droneService, Mockito.times(1)).save(ArgumentMatchers.any(DroneDto.class));
        Mockito.verifyNoMoreInteractions(droneService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(droneService.update(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(DroneBuilder.getDto());

        mockMvc.perform(
                MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(DroneBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(droneService, Mockito.times(1)).update(ArgumentMatchers.any(DroneDto.class), ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(droneService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(droneService).deleteById(ArgumentMatchers.anyString());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(DroneBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(droneService, Mockito.times(1)).deleteById(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(droneService);
    }
}