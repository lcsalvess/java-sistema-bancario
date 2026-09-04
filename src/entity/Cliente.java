package entity;

public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Endereco endereco;

    public Cliente(String nome, String cpf, String email, String telefone, Endereco endereco) {
        if(!nome.trim().matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$")){
            throw new IllegalArgumentException("Erro: O nome deve conter apenas letras");
        }
        if(!cpf.matches("^\\d{11}$")) {
            throw new IllegalArgumentException("Erro: CPF inválido. Deve conter exatamente 11 números.");
        }
        if(!email.trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Erro: E-mail inválido");
        }
        if(!telefone.trim().matches("^\\d{10,11}$")) {
            throw new IllegalArgumentException("Erro: O telefone deve conter 10 ou 11 dígitos numéricos");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format("""
                Nome     : %s
                CPF      : %s
                Email    : %s
                Telefone : %s
                Endereço : %s""",
                nome, cpf, email, telefone, endereco.toString());
    }
}
