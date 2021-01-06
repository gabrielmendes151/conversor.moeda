package com.example.demo.moeda.client.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CotacaoResponse {

    private BigDecimal cotacaoCompra;

    private String dataHoraCotacao;

    private String tipoBoletim;
}
