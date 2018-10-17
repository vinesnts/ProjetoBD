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
public class ProdutosInsuficientesException extends Exception{

    public ProdutosInsuficientesException() {
        super("Quantidade de Produtos Insuficiente.");
    }

    public ProdutosInsuficientesException(String msg) {
        super(msg);
    }
    
    
    
}
