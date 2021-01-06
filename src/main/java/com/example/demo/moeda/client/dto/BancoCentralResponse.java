package com.example.demo.moeda.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BancoCentralResponse {

    @JsonProperty("value")
    private List<CotacaoResponse> cotacoes;
}
