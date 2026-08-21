package entity;

import entity.enums.SituacaoConta;

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

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> getTransacoes() {
        return new ArrayList<>(transacoes);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", titular=" + titular +
                ", numeroConta='" + numeroConta + '\'' +
                ", saldo=" + saldo +
                ", situacaoConta=" + situacaoConta +
                ", transacoes=" + transacoes +
                '}';
    }
}
