package com.example.demo.mapper;

import com.example.demo.cache.model.Cache;
import com.example.demo.enuns.Moeda;
import com.example.demo.moeda.dto.MoedaConverterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CacheMapper {

    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "moedaConverterResponse.valor", target = "valorConvertido")
    @Mapping(constant = "ATIVO", target = "status")
    Cache toCache(final BigDecimal valor, final Moeda moedaOrigem, final Moeda moedaResultado, final LocalDate dataCotacao,
                  final MoedaConverterResponse moedaConverterResponse, final LocalDateTime dataExpiracaoCache);
}
