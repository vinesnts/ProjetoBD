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
public class DataInvalidaException extends Exception{

    public DataInvalidaException() {
        super("Data Invalida.");
    }

    public DataInvalidaException(String msg) {
        super(msg);
    }
    
}
