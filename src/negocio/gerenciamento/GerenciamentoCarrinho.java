package negocio.gerenciamento;

import dados.repositoriobd.RepositorioCarrinho;
import java.util.ArrayList;
import negocio.entidades.Carrinho;
import negocio.excecoes.ProdutoInexistenteException;
import negocio.excecoes.QuantidadeInsuficienteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class GerenciamentoCarrinho {

    private RepositorioCarrinho repositorio;
    private static GerenciamentoCarrinho instancia;

    private GerenciamentoCarrinho() {
        this.repositorio = new RepositorioCarrinho();

    }

    public static GerenciamentoCarrinho getInstancia() {
        if (instancia == null) {
            instancia = new GerenciamentoCarrinho();
        }
        return instancia;
    }

    /**
     *
     * @param carrinho Adiciona uma nova venda ao repositorio
     */
    public void adicionar(Carrinho carrinho) {
        repositorio.adicionar(carrinho);

    }

    /**
     *
     * @param carrinho Verifica se a venda existe, se sim remove, caso não lança
     * exceção
     * @throws negocio.excecoes.QuantidadeInsuficienteException
     *
     */
    public void remover(Carrinho carrinho) throws QuantidadeInsuficienteException {
        boolean existe = repositorio.verificarExistencia(carrinho);
        if (existe == false) {

        } else {
            for (int i = 0; i < repositorio.getCarrinhos().size(); i++) {
                if (repositorio.getCarrinhos().get(i).getProduto().equals(carrinho.getProduto())) {
                    if (repositorio.getCarrinhos().get(i).getQuantidade() > carrinho.getQuantidade()) {
                        repositorio.getCarrinhos().get(i).setQuantidade(repositorio.getCarrinhos().get(i).getQuantidade() - carrinho.getQuantidade());
                    } else if (repositorio.getCarrinhos().get(i).getQuantidade() == carrinho.getQuantidade()) {
                        repositorio.remover(repositorio.getCarrinhos().get(i));
                    } else {
                        throw new QuantidadeInsuficienteException();
                    }
                }

            }
        }
    }

    /**
     *
     * @return ArrayList com todos os carrinhos ja cadastrados durante compra
     */
    public ArrayList<Carrinho> getCarrinhos() {
        return repositorio.getCarrinhos();
    }

    /**
     *
     * @param id
     * @return O obejto carrinho que foi passado o id como parametro de busca
     * @throws ProdutoInexistenteException Se o objeto nao existir lança exceção
     */
    public Carrinho getCarrinho(int id) throws ProdutoInexistenteException {
        return repositorio.getCarrinho(id);
    }

}
