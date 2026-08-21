package repository;

import entity.Conta;

import java.util.HashMap;
import java.util.Map;

public class ContaRepository {
    private Map<String, Conta> contas = new HashMap<>();

    public Conta buscarPorNumero(String numero) {
        return contas.get(numero);
    }

    public void salvar (Conta conta) {
        contas.put(conta.getNumeroConta(), conta);
    }
}
