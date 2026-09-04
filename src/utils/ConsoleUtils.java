package utils;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleUtils {
    public static int lerInteiro(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números inteiros válidos.");
            }
        }
    }
    public static String lerStringObrigatoria(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = sc.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Erro: Este campo é obrigatório e não pode ficar em branco.");
        }
    }

    public static String lerStringOpcional(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        String entrada = sc.nextLine().trim();
        return entrada.isEmpty() ? null : entrada;
    }

    public static BigDecimal lerValorMonetario (Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valorTexto = sc.nextLine().trim().replace(",",".");
            try {
                BigDecimal valor = new BigDecimal(valorTexto);
                if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Erro: O valor deve ser maior que zero.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Valor numérico inválido. Digite no formato 100.50 ou 100,50");
            }
        }
    }
}
