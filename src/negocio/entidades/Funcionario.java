package negocio.entidades;

import java.io.Serializable;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class Funcionario extends Pessoa implements Serializable {

    private boolean eGerente;
    private String senha;

    public Funcionario(String nome, String cpf, boolean eGerente, String senha) {
        super(nome, cpf);
        this.eGerente = eGerente;
        this.senha = senha;

    }

    public Funcionario(String nome, String cpf) {
        super(nome, cpf);
    }

    public void setEGerente(boolean eGerente) {
        this.eGerente = eGerente;
    }

    public boolean eGerente() {
        return eGerente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        String tipo;
        if(eGerente) tipo = "Gerente";
        else tipo = "Vendedor";
        return super.toString() + " | Tipo: " + tipo;
    }

}
