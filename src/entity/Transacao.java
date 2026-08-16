package entity;

import entity.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private Long id;
    private TipoTransacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;

    public Transacao(TipoTransacao tipo, BigDecimal valor, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
