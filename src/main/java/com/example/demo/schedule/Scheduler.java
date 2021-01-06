package com.example.demo.schedule;

import com.example.demo.cache.repositoy.CacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.example.demo.enuns.Status.ATIVO;
import static com.example.demo.enuns.Status.INATIVO;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final CacheManager cacheManager;

    private final CacheRepository cacheRepository;

    @Scheduled(cron = "0 0/1 * * * * ")
    public void limparCaches() {
        cacheRepository.findByDataExpiracaoCacheLessThanAndStatus(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), ATIVO)
            .forEach(item -> {
                final var dataCotacao = item.getDataCotacao().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                final var key = new SimpleKey(item.getValor(), item.getMoedaOrigem(), item.getMoedaResultado(), dataCotacao);
                cacheManager.getCache("converter-moedas").evictIfPresent(key);
                item.setStatus(INATIVO);
                cacheRepository.save(item);
            });
    }
}
