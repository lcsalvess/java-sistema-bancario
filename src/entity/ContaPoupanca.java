package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ContaPoupanca extends Conta{
    private LocalDate dataUltimoRendimento;
    public ContaPoupanca(Cliente titular, String numeroConta) {
        super(titular, numeroConta);
    }

    public void aplicarRendimento() {
        LocalDate dataAtual = LocalDate.now();
        if (dataUltimoRendimento != null) {
            if (dataUltimoRendimento.getMonth() == dataAtual.getMonth()
            && dataUltimoRendimento.getYear() == dataAtual.getYear()) {
                throw new IllegalStateException("O rendimento já foi aplicado neste mês.");
            }
        }
        BigDecimal saldoBase = getSaldo();
        BigDecimal taxaDecimal = new BigDecimal("0.005");
        BigDecimal rendimento = saldoBase.multiply(taxaDecimal).setScale(2, RoundingMode.HALF_UP);
        creditarRendimento(rendimento);
        dataUltimoRendimento = dataAtual;
    }
}
