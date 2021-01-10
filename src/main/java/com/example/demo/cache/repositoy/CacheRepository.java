package com.example.demo.cache.repositoy;

import com.example.demo.cache.model.Cache;
import com.example.demo.enuns.Moeda;
import com.example.demo.enuns.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CacheRepository extends CrudRepository<Cache, Long> {

    List<Cache> findByDataExpiracaoCacheLessThanAndStatus(final LocalDateTime dataAtual, final Status status);

    Optional<Cache> findByValorAndMoedaOrigemAndMoedaResultadoAndDataCotacaoAndStatus(final BigDecimal valor,
                                                                                      final Moeda moedaOrigem,
                                                                                      final Moeda moedaResultado,
                                                                                      final LocalDate dataCotacao,
                                                                                      final Status status);
}
