package service;

import entity.Cliente;
import entity.Conta;
import entity.ContaCorrente;
import entity.ContaPoupanca;
import entity.enums.SituacaoConta;
import entity.enums.TipoConta;
import repository.ContaRepository;

import java.math.BigDecimal;

public class ContaService {
    private final ContaRepository contaRepository;

    private int contadorNumeroConta = 1000;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta criarConta(Cliente cliente, TipoConta tipoConta) {
        String numeroConta = String.valueOf(contadorNumeroConta);
        contadorNumeroConta++;
        Conta novaConta;
        switch (tipoConta) {
            case CORRENTE:
                novaConta = new ContaCorrente(cliente, numeroConta);
                break;
            case POUPANCA:
                novaConta = new ContaPoupanca(cliente, numeroConta);
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta inválido");
        }
        contaRepository.salvar(novaConta);
        return novaConta;
    }

    public void cancelarConta (String numeroConta) {
        Conta conta = contaRepository.buscarPorNumero(numeroConta);
        if (conta == null) {
            throw new IllegalArgumentException("Número de conta inválido.");
        }
        if(conta.getSituacaoConta() == SituacaoConta.CANCELADA) {
            throw new IllegalArgumentException("A conta informada já está cancelada.");
        }
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("A conta não pode ser cancelada com saldo.");
        }
        conta.cancelarConta();
    }

}
