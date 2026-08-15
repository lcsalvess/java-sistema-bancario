package entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private Long id;
    private Cliente titular;
    private String numeroConta;
    private BigDecimal saldo = BigDecimal.ZERO;
    private SituacaoConta situacaoConta = SituacaoConta.ATIVA;
    private List<Transacao> transacoes = new ArrayList<>();

    protected Conta(Cliente titular, String numeroConta) {
        this.titular = titular;
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public SituacaoConta getSituacaoConta() {
        return situacaoConta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public String getNumeroConta() {
        return numeroConta;
    }
}
