package com.example.demo.moeda.service;

import com.example.demo.moeda.client.BancoCentralClient;
import com.example.demo.moeda.client.dto.BancoCentralResponse;
import com.example.demo.moeda.client.dto.CotacaoResponse;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.example.demo.enuns.Moeda.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MoedaServiceTest {

    private MoedaService service;

    @Mock
    private BancoCentralClient client;

    @Before
    public void setup() {
        service = new MoedaService(client);
    }

    @Test
    public void deveConverterMoedaRealParaDolar_quandoMoedaOrigimForBRL() {
        when(client.getCotacaoMoeda("'" + USD.name() + "'", "'01-10-2021'"))
            .thenReturn(getBancoCentralResponse(new BigDecimal("5.00")));

        final var moedaConverterResponse =
            service.converterMoeda(new BigDecimal("10.00"), BRL, USD, "01-10-2021");

        assertThat(moedaConverterResponse.getValor()).isEqualTo(new BigDecimal("2.00"));

        verify(client, times(1)).getCotacaoMoeda(any(), any());
    }

    @Test
    public void deveConverterMoedaDolarParaReal_quandoMoedaResultadoForBRL() {
        when(client.getCotacaoMoeda("'" + USD.name() + "'", "'01-10-2021'"))
            .thenReturn(getBancoCentralResponse(new BigDecimal("5.00")));

        final var moedaConverterResponse =
            service.converterMoeda(new BigDecimal("10.00"), USD, BRL, "01-10-2021");

        assertThat(moedaConverterResponse.getValor()).isEqualTo(new BigDecimal("50.00"));

        verify(client, times(1)).getCotacaoMoeda(any(), any());
    }

    @Test
    public void deveConverterMoedaEuroParaDolar_quandoMoedaResultadoNaoForBRL() {
        when(client.getCotacaoMoeda("'" + EUR.name() + "'", "'01-10-2021'"))
            .thenReturn(getBancoCentralResponse(new BigDecimal("6.00")));

        when(client.getCotacaoMoeda("'" + USD.name() + "'", "'01-10-2021'"))
            .thenReturn(getBancoCentralResponse(new BigDecimal("5.00")));

        final var moedaConverterResponse =
            service.converterMoeda(new BigDecimal("1.00"), EUR, USD, "01-10-2021");

        assertThat(moedaConverterResponse.getValor()).isEqualTo(new BigDecimal("1.20"));

        verify(client, times(2)).getCotacaoMoeda(any(), any());
    }

    @Test
    public void naoDeveConverterMoeda_quandoMoedaOrigimEMoedaResultadoForemIguais() {
        final var moedaConverterResponse =
            service.converterMoeda(new BigDecimal("1.00"), EUR, EUR, "01-10-2021");

        assertThat(moedaConverterResponse.getValor()).isEqualTo(new BigDecimal("1.00"));

        verify(client, never()).getCotacaoMoeda(any(), any());
    }

    private BancoCentralResponse getBancoCentralResponse(final BigDecimal valor) {
        final var build = CotacaoResponse.builder()
            .cotacaoCompra(valor)
            .build();
        final var bancoCentralResponse = new BancoCentralResponse();
        bancoCentralResponse.setCotacoes(Lists.newArrayList(build));
        return bancoCentralResponse;
    }
}
