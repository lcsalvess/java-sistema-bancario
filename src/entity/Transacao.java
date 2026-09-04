package entity;

import entity.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private Long id;
    private TipoTransacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;

    public Transacao(TipoTransacao tipo, BigDecimal valor, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = this.dataHora != null ? this.dataHora.format(formatter) : "Data Indisponível";
        String tipoTransacao = this.tipo.toString().replace("_"," ");
        String sinal = switch(this.tipo.toString()) {
          case "SAQUE", "TRANSFERENCIA_ENVIADA" -> "-";
          case "DEPOSITO", "TRANSFERENCIA_RECEBIDA", "RENDIMENTO" -> "+";
            default -> " ";
        };
        return String.format("[%s] %-25s -> %s R$ %.2f",
                dataFormatada,
                tipoTransacao,
                sinal,
                this.valor);
    }
}
