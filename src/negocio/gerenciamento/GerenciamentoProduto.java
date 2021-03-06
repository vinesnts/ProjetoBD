
package negocio.gerenciamento;

import dados.interfacerepositorio.IRepositorioProduto;
import dados.repositoriobd.RepositorioProduto;
import negocio.entidades.Produto;
import negocio.excecoes.ProdutoExistenteException;
import negocio.excecoes.ProdutoInexistenteException;
import java.util.ArrayList;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class GerenciamentoProduto {

    private IRepositorioProduto repositorio;
    private static GerenciamentoProduto instancia;

    private GerenciamentoProduto() {
        this.repositorio = new RepositorioProduto();
        // repositorio.ler();

    }

    public static GerenciamentoProduto getInstance() {
        if (instancia == null) {
            instancia = new GerenciamentoProduto();

        }
        return instancia;
    }

    /**
     *
     * @param produto Verifica se o produto passado por parametro existe, se sim
     * lança exceção, caso não o cadastro é realizado
     */
    public void adicionar(Produto produto) throws ProdutoExistenteException {
        //boolean existe = repositorio.verificarExistencia(produto);
        //if (existe == true) {
        //    throw new ProdutoExistenteException();
        //} else {
            repositorio.adicionar(produto);
            // repositorio.gravar();
        //}
    }

    /**
     *
     * @param produto Verifica se o produto passado como parâmetro existe, se
     * sim a remoção é realizada, caso não uma exceção é lançada
     * @throws ProdutoInexistenteException
     */
    public void remover(Produto produto) throws ProdutoInexistenteException {
        Produto existe = repositorio.buscar(produto.getId());
        if (existe == null) {
            throw new ProdutoInexistenteException();
        } else {
            repositorio.remover(produto);
            // repositorio.gravar();

        }
    }

    /**
     *
     * @param produto Verifica se o id do produto ja existe, se sim a
     * atualização e realizada com os novos dados passados, caso não uma exceção
     * é lançada
     * @throws ProdutoInexistenteException
     */
    public void atualizar(Produto produto) throws ProdutoInexistenteException {
        Produto existe = repositorio.buscar(produto.getId());
        if (existe == null) {
            throw new ProdutoInexistenteException();
        } else {
            repositorio.atualizar(produto);
            // repositorio.gravar();
        }
    }

    /**
     *
     * @param id
     * @return O produto com o mesmo id se ele existir no repositorio de
     * produtos, caso não uma exceção é lançada
     * @throws ProdutoInexistenteException
     */
    public Produto buscar(int id) throws ProdutoInexistenteException {
        return repositorio.buscar(id);
    }

    /**
     *
     * @return Array com todos os produtos cadastrados no repositorio
     */
    public ArrayList<Produto> getProdutos() {
        return repositorio.getProdutos();
    }
}
