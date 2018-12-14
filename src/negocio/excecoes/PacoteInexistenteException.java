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
public class PacoteInexistenteException extends Exception {

    public PacoteInexistenteException(String msg) {
        super(msg);
    }
    
    public PacoteInexistenteException() {
        super("Pacote nao existe");
    }
    
}
