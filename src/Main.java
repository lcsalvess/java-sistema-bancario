import entity.Cliente;
import entity.Conta;
import entity.Endereco;
import entity.enums.TipoConta;
import entity.enums.TipoLogradouro;
import repository.ContaRepository;
import service.ContaService;
import service.TransacaoService;
import utils.ConsoleUtils;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ContaRepository contaRepository = new ContaRepository();
        ContaService contaService = new ContaService(contaRepository);
        inicializarDadosDeTeste(contaService);
        TransacaoService transacaoService = new TransacaoService(contaRepository);

        int opcao;
        do {
            exibirMenu();
            opcao = ConsoleUtils.lerInteiro(sc, "Digite a opção desejada: ");
            try {
                switch (opcao) {
                    case 1:
                        criarConta(sc, contaService);
                        break;
                    case 2:
                        depositar(sc, transacaoService);
                        break;
                    case 3:
                        sacar(sc, transacaoService);
                        break;
                    case 4:
                        transferir(sc, transacaoService);
                        break;
                    case 5:
                        aplicarRendimento(sc, transacaoService);
                        break;
                    case 6:
                        consultarConta(sc, contaRepository);
                        break;
                    case 7:
                        consultarTransacoes(sc, contaRepository);
                        break;
                    case 8:
                        cancelarConta(sc, contaService);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== SISTEMA BANCÁRIO =====");
        System.out.println("1 - Criar conta");
        System.out.println("2 - Depositar");
        System.out.println("3 - Sacar");
        System.out.println("4 - Transferir");
        System.out.println("5 - Aplicar rendimento");
        System.out.println("6 - Consultar conta");
        System.out.println("7 - Consultar transações");
        System.out.println("8 - Cancelar conta");
        System.out.println("0 - Sair");
    }

    private static void criarConta(Scanner sc, ContaService contaService) {
        System.out.println("=== CRIAR CONTA ===");
        Cliente cliente = criarCliente(sc);
        TipoConta tipoConta = escolherTipoConta(sc);

        Conta conta = contaService.criarConta(cliente, tipoConta);
        System.out.println("Conta criada com sucesso! ");
        System.out.println("Número da Conta: " + conta.getNumeroConta());
    }

    private static Cliente criarCliente(Scanner sc) {
        String nome = ConsoleUtils.lerStringObrigatoria(sc, "Nome: ");
        String cpf = ConsoleUtils.lerStringObrigatoria(sc, "CPF: ");
        String email = ConsoleUtils.lerStringObrigatoria(sc, "Email: ");
        String telefone = ConsoleUtils.lerStringObrigatoria(sc, "Telefone: ");

        Endereco endereco = criarEndereco(sc);
        return new Cliente(nome, cpf, email, telefone, endereco);
    }

    private static Endereco criarEndereco(Scanner sc) {
        System.out.println("Tipo de logradouro:");
        System.out.println("1 - Rua");
        System.out.println("2 - Avenida");
        System.out.println("3 - Travessa");
        System.out.println("4 - Alameda");
        System.out.println("5 - Rodovia");
        System.out.println("6 - Estrada");
        System.out.println("7 - Praça");
        System.out.println("8 - Viela");
        TipoLogradouro tipoLogradouro = null;
        while (tipoLogradouro == null) {
            int opcaoLogradouro = ConsoleUtils.lerInteiro(sc, "Digite a opção do lagradouro: ");
            tipoLogradouro = switch (opcaoLogradouro) {
                case 1 -> TipoLogradouro.RUA;
                case 2 -> TipoLogradouro.AVENIDA;
                case 3 -> TipoLogradouro.TRAVESSA;
                case 4 -> TipoLogradouro.ALAMEDA;
                case 5 -> TipoLogradouro.RODOVIA;
                case 6 -> TipoLogradouro.ESTRADA;
                case 7 -> TipoLogradouro.PRACA;
                case 8 -> TipoLogradouro.VIELA;
                default -> {
                    System.out.println("Erro: Opção inválida. Escolha de 1 a 8.");
                    yield null;
                }
            };
        }

        String logradouro = ConsoleUtils.lerStringObrigatoria(sc, "Logradouro: ");
        String numero = ConsoleUtils.lerStringObrigatoria(sc, "Número: ");
        String complemento = ConsoleUtils.lerStringOpcional(sc, "Complemento: ");
        String bairro = ConsoleUtils.lerStringObrigatoria(sc, "Bairro: ");
        String cidade = ConsoleUtils.lerStringObrigatoria(sc, "Cidade: ");
        String estado = ConsoleUtils.lerStringObrigatoria(sc, "Estado: ");
        String cep = ConsoleUtils.lerStringObrigatoria(sc, "CEP: ");
        return new Endereco(tipoLogradouro, logradouro, numero, complemento, bairro, cidade, estado, cep);
    }

    private static TipoConta escolherTipoConta(Scanner sc) {
        System.out.println("Tipo de Conta:");
        System.out.println("1 - Corrente");
        System.out.println("2 - Poupança");
        while (true) {
            int opcaoConta = ConsoleUtils.lerInteiro(sc, "Digite a opção da conta: ");
            switch (opcaoConta) {
                case 1:
                    return TipoConta.CORRENTE;
                case 2:
                    return TipoConta.POUPANCA;
                default:
                    System.out.println("Erro: Opção inválida. Escolha entre 1 e 2.");
            }
        }
    }

    private static void depositar(Scanner sc, TransacaoService transacaoService) {
        System.out.println("==== DEPÓSITO ====");
        String numeroConta = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta: ");
        BigDecimal valor = ConsoleUtils.lerValorMonetario(sc, "Insira o valor: R$ ");
        try {
            transacaoService.depositar(numeroConta, valor);
            System.out.println("Depósito realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void sacar(Scanner sc, TransacaoService transacaoService) {
        System.out.println("==== SAQUE ====");
        String numeroConta = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta: ");
        BigDecimal valor = ConsoleUtils.lerValorMonetario(sc, "Insira o valor: R$ ");
        try {
            transacaoService.sacar(numeroConta, valor);
            System.out.println("Saque realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void transferir(Scanner sc, TransacaoService transacaoService) {
        System.out.println("==== TRANSFERÊNCIA ====");
        String numeroContaOrigem = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta de origem: ");
        String numeroContaDestino = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta de destino: ");
        BigDecimal valor = ConsoleUtils.lerValorMonetario(sc, "Insira o valor: R$ ");
        try {
            transacaoService.transferencia(numeroContaOrigem, numeroContaDestino, valor);
            System.out.println("Transferência realizada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void aplicarRendimento(Scanner sc, TransacaoService transacaoService) {
        System.out.println("==== RENDIMENTOS ====");
        String numeroConta = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta: ");
        transacaoService.aplicarRendimento(numeroConta);
        System.out.println("Rendimento aplicado com sucesso!");
    }

    private static void consultarConta(Scanner sc, ContaRepository contaRepository) {
        System.out.println("==== CONSULTAR CONTA ====");
        String numeroConta = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta: ");
        Conta conta = contaRepository.buscarPorNumero(numeroConta);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        System.out.println(conta);
    }

    private static void consultarTransacoes(Scanner sc, ContaRepository contaRepository) {
        System.out.println("==== CONSULTAR TRANSAÇÕES ====");
        String numeroConta = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta: ");
        Conta conta = contaRepository.buscarPorNumero(numeroConta);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        if (conta.getTransacoes().isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
            return;
        }
        conta.getTransacoes().forEach(System.out::println);
    }

    private static void cancelarConta(Scanner sc, ContaService contaService) {
        System.out.println("==== CANCELAMENTO DE CONTAS ====");
        String numeroConta = ConsoleUtils.lerStringObrigatoria(sc, "Insira o número da conta: ");
        contaService.cancelarConta(numeroConta);
        System.out.println("Conta cancelada com sucesso.");
    }

    private static void inicializarDadosDeTeste(ContaService contaService) {
        try {
            System.out.println("\n[SISTEMA] Inicializando carga de dados de teste...");

            // Arrays de dados fictícios para as 6 contas
            String[] nomes = {"Lucas Silva", "Ana Beatriz", "Carlos Eduardo", "Mariana Costa", "Rodrigo Souza", "Juliana Lima"};
            String[] cpfs = {"12345678998", "98765432112", "45678912334", "78912345656", "32165498778", "65498732111"};
            String[] emails = {"lucas@teste.com", "ana@teste.com", "carlos@teste.com", "mariana@teste.com", "rodrigo@teste.com", "juliana@teste.com"};
            String[] telefones = {"11975483258", "21988887777", "31999991111", "41955554444", "51966663333", "61922221111"};

            // Dados de endereço variados
            TipoLogradouro[] tiposLogradouro = {TipoLogradouro.RUA, TipoLogradouro.AVENIDA, TipoLogradouro.TRAVESSA, TipoLogradouro.ALAMEDA, TipoLogradouro.PRACA, TipoLogradouro.VIELA};
            String[] nomesLogradouro = {"da Paz", "Paulista", "Sete de Setembro", "das Flores", "da Matriz", "dos Esquecidos"};
            String[] cidades = {"Monte Mor", "Belo Horizonte", "São Paulo", "Curitiba", "Porto Alegre", "Brasília"};
            String[] estados = {"MG", "MG", "SP", "PR", "RS", "DF"};

            for (int i = 0; i < 6; i++) {
                // 1. Cria o endereço passando o enum TipoLogradouro correto
                Endereco endereco = new Endereco(
                        tiposLogradouro[i],
                        nomesLogradouro[i],
                        String.valueOf(100 + i * 53),
                        null,
                        "Bairro Centro",
                        cidades[i],
                        estados[i],
                        "0789510" + i
                );

                // 2. Cria o cliente
                Cliente cliente = new Cliente(nomes[i], cpfs[i], emails[i], telefones[i], endereco);

                // 3. Alterna o tipo de conta (3 correntes e 3 poupanças)
                TipoConta tipo = (i % 2 == 0) ? TipoConta.CORRENTE : TipoConta.POUPANCA;

                // 4. Cria a conta chamando o seu service
                Conta contaTeste = contaService.criarConta(cliente, tipo);

                // 5. Adiciona saldo inicial (R$ 500, R$ 1000, R$ 1500, etc.)
                BigDecimal saldoInicial = new BigDecimal((i + 1) * 500);
                contaTeste.depositar(saldoInicial);

                System.out.println(" > Conta '" + contaTeste.getNumeroConta() + "' (" + tipo + ") criada para " + nomes[i] + " | Saldo: R$" + saldoInicial);
            }

            System.out.println("[SISTEMA] Carga inicial concluída com sucesso!\n");

        } catch (Exception e) {
            System.out.println("[SISTEMA] Erro ao carregar dados de teste: " + e.getMessage());
        }
    }

}
