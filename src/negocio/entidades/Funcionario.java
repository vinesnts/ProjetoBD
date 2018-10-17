package negocio.entidades;

import java.io.Serializable;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class Funcionario extends Pessoa implements Serializable {

    private String matricula;
    private String tipo;
    private String senha;

    public Funcionario(String nome, String cpf, String tipo, String matricula, String senha) {
        super(nome, cpf);
        this.tipo = tipo;
        this.matricula = matricula;
        this.senha = senha;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        return super.toString() + "\tTipo: " + tipo + "\tMatricula: " + matricula;
    }

}
