package com.example.demo.cache.model;

import com.example.demo.enuns.Moeda;
import com.example.demo.enuns.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CACHE")
public class Cache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "VALOR_CONVERTIDO")
    private BigDecimal valorConvertido;

    @Column(name = "MOEDA_ORIGEM")
    @Enumerated(value = EnumType.STRING)
    private Moeda moedaOrigem;

    @Column(name = "MOEDA_RESULTADO")
    @Enumerated(value = EnumType.STRING)
    private Moeda moedaResultado;

    @Column(name = "DATA_COTACAO")
    private LocalDate dataCotacao;

    @Column(name = "DATA_EXPIRACAO_CACHE")
    private LocalDateTime dataExpiracaoCache;

    @Column(name = "STATUS")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;

    @PrePersist
    public void preSave() {
        this.dataCadastro = LocalDateTime.now();
    }
}
