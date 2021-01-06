package com.example.demo.cache.repositoy;

import com.example.demo.cache.model.Cache;
import com.example.demo.enuns.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CacheRepository extends CrudRepository<Cache, Long> {

    List<Cache> findByDataExpiracaoCacheLessThanAndStatus(final LocalDateTime dataAtual, final Status status);
}
