package com.example.demo.moeda.service;

import com.example.demo.moeda.client.BancoCentralClient;
import com.example.demo.enuns.Moeda;
import com.example.demo.moeda.client.dto.CotacaoResponse;
import com.example.demo.moeda.dto.MoedaConverterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.example.demo.enuns.Moeda.BRL;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoedaService {

    private final BancoCentralClient bancoCentralClient;

    @Cacheable("converter-moedas")
    public MoedaConverterResponse converterMoeda(final BigDecimal valor, final Moeda moedaOrigem, final Moeda moedaResultado,
                                                 final String dataCotacao) {
        if (moedaOrigem.equals(moedaResultado)) {
            return new MoedaConverterResponse(valor);
        } else if (BRL.equals(moedaOrigem)) {
            final var cotacaoResponse = getCotacaoResponse(moedaResultado, dataCotacao);
            return new MoedaConverterResponse(valor.divide(cotacaoResponse.getCotacaoCompra(), 2, RoundingMode.HALF_DOWN));
        } else if (BRL.equals(moedaResultado)) {
            final var cotacaoResponse = getCotacaoResponse(moedaOrigem, dataCotacao);
            return new MoedaConverterResponse(valor.multiply(cotacaoResponse.getCotacaoCompra())
                .setScale(2, RoundingMode.HALF_DOWN));
        }
        final var cotacaoMoedaOrigem = getCotacaoResponse(moedaOrigem, dataCotacao);
        final var cotacaoMoedaResultado = getCotacaoResponse(moedaResultado, dataCotacao);
        return new MoedaConverterResponse(cotacaoMoedaOrigem.getCotacaoCompra()
            .divide(cotacaoMoedaResultado.getCotacaoCompra(), RoundingMode.HALF_DOWN)
            .multiply(valor)
            .setScale(2, RoundingMode.HALF_DOWN));
    }

    private CotacaoResponse getCotacaoResponse(Moeda moedaResultado, String dataCotacao) {
        return bancoCentralClient.getCotacaoMoeda(formataParam(moedaResultado.name()), formataParam(dataCotacao))
            .getCotacoes().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cotacação não encontrada"));
    }

    private String formataParam(final String campo) {
        return "'".concat(campo).concat("'");
    }
}
