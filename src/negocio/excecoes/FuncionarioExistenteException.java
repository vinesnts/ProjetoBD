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
public class FuncionarioExistenteException extends Exception{

    public FuncionarioExistenteException() {
        super("Matricula ou CPF já cadastrados");
    }

    public FuncionarioExistenteException(String message) {
        super(message);
    }
    
    
    
}
