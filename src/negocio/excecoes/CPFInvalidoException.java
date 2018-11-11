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
public class CPFInvalidoException extends Exception {

    public CPFInvalidoException() {
        super("CPF invalido");
    }

    public CPFInvalidoException(String msg) {
        super(msg);
    }
    
}
