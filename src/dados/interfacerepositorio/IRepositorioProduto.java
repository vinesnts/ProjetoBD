/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.interfacerepositorio;

import negocio.entidades.Produto;
import negocio.excecoes.ProdutoInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public interface IRepositorioProduto {

    public void adicionar(Produto produto);

    public void remover(Produto produto);

    public void atualizar(Produto produto);

    public Produto buscar(int id) throws ProdutoInexistenteException;
    
    public boolean verificarExistencia(Produto produto);
}
