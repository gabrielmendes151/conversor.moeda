package com.example.demo.moeda.client;

import com.example.demo.moeda.client.dto.BancoCentralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "banco-central-client", url = "${client.banco-central.base-url}")
public interface BancoCentralClient {

    @GetMapping(value = "${client.banco-central.cotacao}", consumes = "application/json")
    BancoCentralResponse getCotacaoMoeda(@RequestParam("@moeda") final String moeda,
                                         @RequestParam("@dataCotacao") final String dataCotacao);
}
