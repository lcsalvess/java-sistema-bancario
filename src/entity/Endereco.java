package entity;

import entity.enums.TipoLogradouro;

public class Endereco {
    private Long id;
    private TipoLogradouro tipoLogradouro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(TipoLogradouro tipoLogradouro, String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        if (!estado.trim().matches("^[A-Za-z]{2}$")){
            throw new IllegalArgumentException("Erro: Digite a sigla do estado");
        }
        if (!cep.matches("^\\d{8}$")){
            throw new IllegalArgumentException("Erro: O CEP deve conter oito digítos");
        }
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    @Override
    public String toString() {
        String comp = (complemento != null && !complemento.isEmpty()) ? " - " + complemento : "";
        return String.format("%s %s, %s%s - %s, %s/%s, CEP: %s",
                tipoLogradouro, logradouro, numero, comp, bairro, cidade, estado, cep);
    }
}
