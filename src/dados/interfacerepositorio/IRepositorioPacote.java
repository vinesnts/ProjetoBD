
package dados.interfacerepositorio;

import java.util.ArrayList;
import negocio.entidades.Pacote;


/**
 *
 * @author Raquell Vieira
 */
public interface IRepositorioPacote {
    /**
     * 
     * @param carrinho Adiciona um carrinho ao repositorio enquanto uma venda esta sendo realizada
     */
    public int adicionar(Pacote carrinho);
    /**
     * 
     * @param carrinho remove um carrinho do repositorio enquanto uma venda esta sendo realizada
     */
    public void remover(Pacote carrinho);
    /**
     * 
     * @param carrinho verifica se um produto ja foi adicionada ao Array de carrinhos
     * @return true se sim, caso não retorna false
     */
    public boolean verificarExistencia(Pacote carrinho);
    /**
     * 
     * @param carrinho Sobrescreve a quantidade de produtos no carrinho
     */
    public void atualizar(Pacote carrinho);
    
    public ArrayList<Pacote> getPacotes(int idVenda);
}
