package com.example.demo.moeda.controller;

import com.example.demo.cache.service.CacheService;
import com.example.demo.enuns.Moeda;
import com.example.demo.moeda.dto.MoedaConverterResponse;
import com.example.demo.moeda.service.MoedaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @GetMapping("tipos")
    @ApiOperation("Retorna os tipos de moeda disponíveis para conversão")
    public Map<String, String> getMoedas() {
        return Arrays.stream(Moeda.values()).collect(Collectors.toMap(Enum::name, Moeda::getDescricao));
    }

    @GetMapping("converter/{valor}/{moedaOrigem}/{moedaResultado}/{dataCotacao}")
    public MoedaConverterResponse converterMoeda(@ApiParam(name = "valor", value = "Valor para conversao", required = true)
                                                 @PathVariable("valor") final BigDecimal valor,
                                                 @ApiParam(name = "moedaOrigem", value = "Moeda de origin", required = true)
                                                 @PathVariable("moedaOrigem") final Moeda moedaOrigem,
                                                 @ApiParam(name = "moedaResultado", value = "Moeda do resultado", required = true)
                                                 @PathVariable("moedaResultado") final Moeda moedaResultado,
                                                 @ApiParam(name = "dataCotacao", value = "Data cotação da moeda", format = "mm-DD-yyyy",
                                                     required = true, example = "05-30-2020")
                                                 @PathVariable("dataCotacao") final String dataCotacao) {
        final var moedaConverterResponse = moedaService.converterMoeda(valor, moedaOrigem,
            moedaResultado, dataCotacao);
        if (cacheService.isCacheAtivo(valor, moedaOrigem, moedaResultado, dataCotacao)) {
            moedaConverterResponse.setCacheado(true);
        } else {
            cacheService.save(valor, moedaOrigem, moedaResultado, dataCotacao, moedaConverterResponse);
        }
        return moedaConverterResponse;
    }
}
