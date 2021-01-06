package com.example.demo.moeda.controller;

import com.example.demo.cache.service.CacheService;
import com.example.demo.enuns.Moeda;
import com.example.demo.moeda.dto.MoedaConverterResponse;
import com.example.demo.moeda.service.MoedaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moeda")
public class MoedaController {

    private final MoedaService moedaService;

    private final CacheService cacheService;

    @GetMapping
    public Map<String, String> getMoedas() {
        return Arrays.stream(Moeda.values()).collect(Collectors.toMap(Enum::name, Moeda::getDescricao));
    }

    @GetMapping("converter/{valor}/{moedaOrigem}/{moedaResultado}/{dataCotacao}")
    public MoedaConverterResponse converterMoeda(@PathVariable("valor") final BigDecimal valor,
                                                 @PathVariable("moedaOrigem") final Moeda moedaOrigem,
                                                 @PathVariable("moedaResultado") final Moeda moedaResultado,
                                                 @PathVariable("dataCotacao") final String dataCotacao) {
        final var moedaConverterResponse = moedaService.converterMoeda(valor, moedaOrigem,
            moedaResultado, dataCotacao);
        if (moedaConverterResponse.isvalorCacheado()) {
            moedaConverterResponse.setCacheado(true);
        }else{
            cacheService.save(valor, moedaOrigem, moedaResultado, dataCotacao, moedaConverterResponse);
        }
        return moedaConverterResponse;
    }
}
