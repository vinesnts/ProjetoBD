/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositoriobd;

import java.util.ArrayList;
import negocio.entidades.Carrinho;
import dados.interfacerepositorio.IRepositorioCarrinho;
import negocio.excecoes.ProdutoInexistenteException;


/**
 *
 * @author Raquell Vieira
 */
public class RepositorioCarrinho implements IRepositorioCarrinho {
    
    private ArrayList<Carrinho> carrinhos;
    
    public RepositorioCarrinho() {
        this.carrinhos = new ArrayList();
    }
    
    @Override
    public void adicionar(Carrinho carrinho) {
        boolean existe = false;
        
        for (int i = 0; i < carrinhos.size(); i++) {
            if (carrinhos.get(i).getProduto().equals(carrinho.getProduto())) {
                carrinhos.get(i).setQuantidade(carrinhos.get(i).getQuantidade() + carrinho.getQuantidade());
                existe = true;
                
            }
            
        }
        if (!existe) {
            carrinhos.add(carrinho);
        }
        
    }
    
    @Override
    public void remover(Carrinho carrinho) {
        carrinhos.remove(carrinho);
    }

    /**
     *
     * @param carrinho Verifica a posicao do produto e sobrescreve a sua
     * quantidade
     */
    @Override
    public void atualizar(Carrinho carrinho) {
        for (int i = 0; i < carrinhos.size(); i++) {
            if (carrinhos.get(i).equals(carrinho)) {
                carrinhos.get(i).setQuantidade(carrinho.getQuantidade());
            }
        }
        
    }

    /**
     *
     * @param carrinho verifica se existe o objeto carrinho no repositorio.
     * @return true se ele existir, caso não, false.
     */
    @Override
    public boolean verificarExistencia(Carrinho carrinho) {
        boolean existe = false;
        
        for (int i = 0; i < carrinhos.size(); i++) {
            if (carrinhos.get(i).getProduto().equals(carrinho.getProduto())) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    /**
     *
     * @return Array com todos os carrinhos
     */
    public ArrayList<Carrinho> getCarrinhos() {
        return carrinhos;
    }

    /**
     *
     * @param id
     * @return O objeto carrinho se ele existir
     * @throws ProdutoInexistenteException
     */
    public Carrinho getCarrinho(int id) throws ProdutoInexistenteException {
        
        for (int i = 0; i < carrinhos.size(); i++) {
            if (carrinhos.get(i).getProduto().getId() == (id)) {
                return carrinhos.get(i);
                
            }
            
        }
        throw new ProdutoInexistenteException();
        
    }
}
