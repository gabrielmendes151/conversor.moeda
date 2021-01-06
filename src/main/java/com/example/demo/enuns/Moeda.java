package com.example.demo.enuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Moeda {

    BRL("Real"),
    AUD("Dólar australiano"),
    CAD("Dólar canadense"),
    CHF("Franco suíço"),
    DKK("Coroa dinamarquesa"),
    EUR("Euro"),
    GBP("Libra Esterlina"),
    JPY("Iene"),
    NOK("Coroa norueguesa"),
    SEK("Coroa sueca"),
    USD("Dólar dos Estados Unidos");

    private final String descricao;
}
