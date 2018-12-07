
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidades;

import java.io.Serializable;
import java.time.LocalDate;


/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class Cliente extends Pessoa implements Serializable{

    private LocalDate dataAniversario;

    public Cliente(String nome, String cpf, LocalDate dataAniversario) {
        super(nome, cpf);
        this.dataAniversario = dataAniversario;
    }

    public Cliente(String nome, String cpf) {
        super(nome, cpf);
    }

       
    public LocalDate getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(LocalDate dataAniversario) {
        this.dataAniversario = dataAniversario;
    }

    @Override
    public String toString() {
        return super.toString() + " | Data de Aniversario: " + dataAniversario.getDayOfMonth() +
                " / " + dataAniversario.getMonthValue() + " / " + dataAniversario.getYear();
    }

}
