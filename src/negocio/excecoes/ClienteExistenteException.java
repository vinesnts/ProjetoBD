
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
public class ClienteExistenteException extends Exception {

    public ClienteExistenteException() {
        super("CPF ja cadastrado");
    }

    public ClienteExistenteException(String msg) {
        super(msg);
    }

}
