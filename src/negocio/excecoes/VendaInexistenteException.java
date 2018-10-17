
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
public class VendaInexistenteException extends Exception {

    public VendaInexistenteException() {
        super("Venda Inexistente!");
    }

    public VendaInexistenteException(String msg) {
        super(msg);
    }

}
