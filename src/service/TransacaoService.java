package service;

import entity.Conta;
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
        if (isContaInvalida(conta)) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero");
        }
        conta.depositar(valor);
    }

    public void sacar(String numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.buscarPorNumero(numeroConta);
        if (isContaInvalida(conta)) {
            throw new IllegalArgumentException("Conta não encontrada");
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
        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Não é possível ficar com saldo negativo.");
        }
        if(numeroContaOrigem.equals(numeroContaDestino)){
            throw new IllegalArgumentException("Não é possível fazer transferências entre a mesma conta");
        }
        contaOrigem.debitarTransferencia(valor);
        contaDestino.creditarTransferencia(valor);
    }

    private boolean isContaInvalida(Conta conta) {
        return conta == null || conta.getSituacaoConta() == SituacaoConta.CANCELADA;
    }
}


