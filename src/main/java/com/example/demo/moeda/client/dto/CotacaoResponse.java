package com.example.demo.moeda.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class CotacaoResponse {

    private BigDecimal cotacaoCompra;

    private String dataHoraCotacao;

    private String tipoBoletim;
}
