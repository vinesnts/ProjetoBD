package negocio.gerenciamento;

import dados.repositoriobd.RepositorioPacote;
import java.util.ArrayList;
import negocio.entidades.Pacote;
import negocio.excecoes.PacoteInexistenteException;
import negocio.excecoes.QuantidadeInsuficienteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class GerenciamentoPacote {

    private RepositorioPacote repositorio;
    private static GerenciamentoPacote instancia;

    private GerenciamentoPacote() {
        this.repositorio = RepositorioPacote.getInstance();

    }

    public static GerenciamentoPacote getInstancia() {
        if (instancia == null) {
            instancia = new GerenciamentoPacote();
        }
        return instancia;
    }

    /**
     *
     * @param pacote Adiciona uma nova venda ao repositorio
     */
    public void adicionar(Pacote pacote) {
        repositorio.adicionar(pacote);

    }

    /**
     *
     * @param pacote Verifica se a venda existe, se sim remove, caso não lança
     * exceção
     * @throws negocio.excecoes.QuantidadeInsuficienteException
     *
     */
    public void remover(Pacote pacote) throws QuantidadeInsuficienteException {
        for (int i = 0; i < repositorio.getPacotes().size(); i++) {
            if (repositorio.getPacotes().get(i).getProduto() == pacote.getProduto()) {
                if (repositorio.getPacotes().get(i).getQuantidade() > pacote.getQuantidade()) {
                    repositorio.getPacotes().get(i).setQuantidade(repositorio.getPacotes().get(i).getQuantidade() - pacote.getQuantidade());
                } else if (repositorio.getPacotes().get(i).getQuantidade() == pacote.getQuantidade()) {
                    repositorio.remover(repositorio.getPacotes().get(i));
                } else {
                    throw new QuantidadeInsuficienteException();
                }
            }

        }
    }

    /**
     *
     * @return ArrayList com todos os pacotes ja cadastrados durante compra
     */
    public ArrayList<Pacote> getPacotes() {
        return repositorio.getPacotes();
    }

    /**
     *
     * @param id
     * @return O obejto pacote que foi passado o id como parametro de busca
     * @throws PacoteInexistenteException Se o objeto nao existir lança exceção
     */
    public Pacote getPacote(int id) throws PacoteInexistenteException {
        return repositorio.buscar(id);
    }

}
