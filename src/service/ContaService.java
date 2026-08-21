package service;

import entity.Cliente;
import entity.Conta;
import entity.ContaCorrente;
import entity.ContaPoupanca;
import entity.enums.TipoConta;
import repository.ContaRepository;

public class ContaService {
    private ContaRepository contaRepository;

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

}
