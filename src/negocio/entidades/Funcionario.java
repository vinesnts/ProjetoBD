package negocio.entidades;

import java.io.Serializable;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class Funcionario extends Pessoa implements Serializable {

    private String matricula;
    private boolean eGerente;
    private String senha;

    public Funcionario(String nome, String cpf, boolean eGerente, String matricula, String senha) {
        super(nome, cpf);
        this.eGerente = eGerente;
        this.matricula = matricula;
        this.senha = senha;

    }

    public void setEGerente(boolean eGerente) {
        this.eGerente = eGerente;
    }

    public boolean eGerente() {
        return eGerente;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
        return super.toString() + " | Tipo: " + tipo + " | Matricula: " + matricula;
    }

}
