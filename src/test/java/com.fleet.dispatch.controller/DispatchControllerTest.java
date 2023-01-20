package com.fleet.dispatch.controller;

import com.fleet.CustomUtils;
import com.fleet.dispatch.dto.DispatchDto;
import com.fleet.dispatch.service.DispatchService;
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
public class DispatchControllerTest {
    private static final String ENDPOINT_URL = "/api/dispatch";
    @InjectMocks
    private DispatchController dispatchController;
    @Mock
    private DispatchService dispatchService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(dispatchController)
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                //.addFilter(CustomFilter::doFilter)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<DispatchDto> page = new PageImpl<>(Collections.singletonList(DispatchBuilder.getDto()));

        Mockito.when(dispatchService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(dispatchService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(dispatchService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(dispatchService.findById(ArgumentMatchers.anyInt())).thenReturn(DispatchBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(dispatchService, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(dispatchService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(dispatchService.save(ArgumentMatchers.any(DispatchDto.class))).thenReturn(DispatchBuilder.getDto());

        mockMvc.perform(
                MockMvcRequestBuilders.post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(DispatchBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(dispatchService, Mockito.times(1)).save(ArgumentMatchers.any(DispatchDto.class));
        Mockito.verifyNoMoreInteractions(dispatchService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(dispatchService.update(ArgumentMatchers.any(), ArgumentMatchers.anyInt())).thenReturn(DispatchBuilder.getDto());

        mockMvc.perform(
                MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(DispatchBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(dispatchService, Mockito.times(1)).update(ArgumentMatchers.any(DispatchDto.class), ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(dispatchService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(dispatchService).deleteById(ArgumentMatchers.anyInt());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(DispatchBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(dispatchService, Mockito.times(1)).deleteById(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(dispatchService);
    }
}