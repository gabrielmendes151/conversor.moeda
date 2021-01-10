package com.example.demo.moeda.controller;

import com.example.demo.cache.service.CacheService;
import com.example.demo.moeda.dto.MoedaConverterResponse;
import com.example.demo.moeda.service.MoedaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class MoedaControllerTest {

    private final static String URI = "/api/moeda";

    @InjectMocks
    private MoedaController controller;

    @Mock
    private MoedaService service;

    @Mock
    private CacheService cacheService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getTiposDeMoedaParaConversao() throws Exception {
        mockMvc.perform(get(URI + "/tipos")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    @Test
    public void getValorConvertido_quandoNaoPegaDoCache() throws Exception {
        when(service.converterMoeda(any(), any(), any(), any()))
            .thenReturn(new MoedaConverterResponse(new BigDecimal("2.00")));
        when(cacheService.isCacheAtivo(any(), any(), any(), any())).thenReturn(false);

        mockMvc.perform(get(URI + "/converter/10.00/BRL/USD/01-05-2021")
            .contentType("application/json"))
            .andExpect(status().isOk());

        verify(cacheService, times(1)).save(any(), any(), any(), any(), any());
    }

    @Test
    public void getValorConvertido_quandoPegaDoCache() throws Exception {
        when(service.converterMoeda(any(), any(), any(), any()))
            .thenReturn(new MoedaConverterResponse(new BigDecimal("2.00")));
        when(cacheService.isCacheAtivo(any(), any(), any(), any())).thenReturn(true);

        mockMvc.perform(get(URI + "/converter/10.00/BRL/USD/01-05-2021")
            .contentType("application/json"))
            .andExpect(status().isOk());

        verify(cacheService, never()).save(any(), any(), any(), any(), any());
    }
}
