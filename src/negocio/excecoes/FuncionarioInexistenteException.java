/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.excecoes;

/**
 *
 * @author Raquell Vieira
 */
public class FuncionarioInexistenteException extends Exception{

    public FuncionarioInexistenteException() {
        super("Matricula não cadastrada.");
    }

    public FuncionarioInexistenteException(String message) {
        super(message);
    }
    
    
    
}
