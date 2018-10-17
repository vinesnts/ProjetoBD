/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.excecoes;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class PrecoInvalidoException extends Exception {

    public PrecoInvalidoException() {
        super("Preco nao suportado!");
    }

    public PrecoInvalidoException(String msg) {
        super(msg);
    }

}
