/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.excecoes;

/**
 *
 * @author vinesnts
 */
public class SenhaInvalidaException extends Exception {

    public SenhaInvalidaException() {
        super("Senha invalida");
    }

    public SenhaInvalidaException(String msg) {
        super(msg);
    }
    
}
