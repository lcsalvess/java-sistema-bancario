package service;

import entity.Conta;
import entity.ContaPoupanca;
import entity.enums.SituacaoConta;
import repository.ContaRepository;

import java.math.BigDecimal;

public class TransacaoService {
    private final ContaRepository contaRepository;

    public TransacaoService(ContaRepository contaRepository){
        this.contaRepository = contaRepository;
    }

    public void depositar(String numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.buscarPorNumero(numeroConta);
        if(isValorInvalido(valor)) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero");
        }
        if (isContaInvalida(conta)) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        conta.depositar(valor);
    }

    public void sacar(String numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.buscarPorNumero(numeroConta);
        if (isContaInvalida(conta)) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        if (isValorInvalido(valor)) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero");
        }
        if(conta.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Não é possível ficar com saldo negativo.");
        }
        conta.sacar(valor);
    }

    public void transferencia(String numeroContaOrigem, String numeroContaDestino, BigDecimal valor) {
        Conta contaOrigem = contaRepository.buscarPorNumero(numeroContaOrigem);
        Conta contaDestino = contaRepository.buscarPorNumero(numeroContaDestino);
        if (isContaInvalida(contaOrigem)) {
            throw new IllegalArgumentException("Número da conta de origem inválido!");
        }
        if (isContaInvalida(contaDestino)) {
            throw new IllegalArgumentException("Número da conta de destino inválido!");
        }
        if(numeroContaOrigem.equals(numeroContaDestino)){
            throw new IllegalArgumentException("Não é possível fazer transferências entre a mesma conta");
        }
        if (isValorInvalido(valor)) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
        }
        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Não é possível ficar com saldo negativo.");
        }
        contaOrigem.debitarTransferencia(valor);
        contaDestino.creditarTransferencia(valor);
    }

    public void aplicarRendimento (String numeroConta) {
        Conta conta = contaRepository.buscarPorNumero(numeroConta);
        if (isContaInvalida(conta)) {
            throw new IllegalArgumentException("Conta não encontrada ou cancelada");
        }
        if (!(conta instanceof ContaPoupanca contaPoupanca)) {
            throw new IllegalArgumentException("Rendimento só pode ser aplicado em contas poupança.");
        }
        contaPoupanca.aplicarRendimento();
    }

    private boolean isContaInvalida(Conta conta) {
        return conta == null || conta.getSituacaoConta() == SituacaoConta.CANCELADA;
    }

    private boolean isValorInvalido(BigDecimal valor) {
        return valor == null || valor.compareTo(BigDecimal.ZERO) <= 0;
    }
}


