package com.example.demo.cache.service;

import com.example.demo.cache.repositoy.CacheRepository;
import com.example.demo.mapper.CacheMapper;
import com.example.demo.enuns.Moeda;
import com.example.demo.moeda.dto.MoedaConverterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final static String PATTERN_MM_DD_YYYY = "MM-dd-yyyy";

    private final CacheRepository repository;

    private final CacheMapper mapper;

    public void save(final BigDecimal valor, final Moeda moedaOrigem, final Moeda moedaResultado, final String dataCotacao,
                     final MoedaConverterResponse moedaConverterResponse) {
        final var cotacao = LocalDate.parse(dataCotacao, DateTimeFormatter.ofPattern(PATTERN_MM_DD_YYYY));
        final var cache = mapper.toCache(valor, moedaOrigem, moedaResultado, cotacao, moedaConverterResponse,
            LocalDate.now().atTime(moedaConverterResponse.getHoraTerminoCache()));
        repository.save(cache);
    }
}
