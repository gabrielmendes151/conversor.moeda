package com.example.demo.moeda.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MoedaConverterResponse {

    private BigDecimal valor;

    private Boolean cacheado;

    private String horaConsulta;

    public MoedaConverterResponse(final BigDecimal valor) {
        this.valor = valor;
        this.cacheado = false;
        this.horaConsulta = LocalTime.now().truncatedTo(ChronoUnit.MILLIS).toString();
    }

    @JsonIgnore
    public LocalTime getHoraTerminoCache() {
        final var consulta = LocalTime.parse(horaConsulta);
        return consulta.plusMinutes(30).truncatedTo(ChronoUnit.MILLIS);
    }
}
