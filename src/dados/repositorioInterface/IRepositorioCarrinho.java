/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositorioInterface;

import negocio.entidades.Carrinho;


/**
 *
 * @author Raquell Vieira
 */
public interface IRepositorioCarrinho {
    /**
     * 
     * @param carrinho Adiciona um carrinho ao repositorio enquanto uma venda esta sendo realizada
     */
    public void adicionar(Carrinho carrinho);
    /**
     * 
     * @param carrinho remove um carrinho do repositorio enquanto uma venda esta sendo realizada
     * @throws negocio.excecoes.QuantidadeInsuficienteException
     */
    public void remover(Carrinho carrinho);
    /**
     * 
     * @param carrinho verifica se um produto ja foi adicionada ao Array de carrinhos
     * @return true se sim, caso não retorna false
     */
    public boolean verificarExistencia(Carrinho carrinho);
    /**
     * 
     * @param carrinho Sobrescreve a quantidade de produtos no carrinho
     */
    public void atualizar(Carrinho carrinho);
    
}
