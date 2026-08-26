package entity;

import entity.enums.SituacaoConta;
import entity.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public void depositar(BigDecimal valor){
        this.saldo = this.saldo.add(valor);
        Transacao transacao = new Transacao(TipoTransacao.DEPOSITO, valor, LocalDateTime.now());
        this.adicionarTransacao(transacao);
    }

    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
        Transacao transacao = new Transacao(TipoTransacao.SAQUE, valor, LocalDateTime.now());
        this.adicionarTransacao(transacao);
    }

    public void debitarTransferencia(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
        Transacao transacao = new Transacao(TipoTransacao.TRANSFERENCIA_ENVIADA, valor, LocalDateTime.now());
        this.adicionarTransacao(transacao);
    }

    public void creditarTransferencia(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
        Transacao transacao = new Transacao(TipoTransacao.TRANSFERENCIA_RECEBIDA, valor, LocalDateTime.now());
        this.adicionarTransacao(transacao);
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
