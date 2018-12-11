
package negocio.gerenciamento;

import dados.repositoriobd.RepositorioCliente;
import negocio.entidades.Cliente;
import negocio.excecoes.ClienteExistenteException;
import negocio.excecoes.ClienteInexistenteException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class GerenciamentoCliente {

    private RepositorioCliente repositorio;
    private static GerenciamentoCliente instancia;

    /**
     * @param repositorio
     */
    private GerenciamentoCliente() {
        this.repositorio = RepositorioCliente.getInstance();
        // repositorio.ler();
    }

    /**
     *
     * @return Retorna a instancia do GerenciamentoCliente
     */
    public static GerenciamentoCliente getInstance() {
        if (instancia == null) {
            instancia = new GerenciamentoCliente();

        }
        return instancia;
    }

    /**
     * @param cliente
     * @throws ClienteExistenteException verifica se o cliente passado como
     * parametro existe, se existir lança exceção, caso não efetua o cadastro.
     */
    public void adicionar(Cliente cliente) throws ClienteExistenteException {
        boolean existe = repositorio.verificarExistencia(cliente);
        if (existe == true) {
            throw new ClienteExistenteException();
        } else {
            repositorio.adicionar(cliente);

        }
    }

    /**
     *
     * @param cliente
     *
     * Verifica se o cliente passado como parametro existe, se existir remove,
     * caso não lança exceção.
     */
    public void remover(Cliente cliente) throws ClienteInexistenteException {
        repositorio.remover(cliente);
    }

    /**
     * @param cliente
     * @throws ClienteInexistenteException Verifica se o cpf pertence a algum
     * cliente já cadastrado, se sim atualiza os dados do cliente, caso não a
     * exceção é lançada
     */
    public void atualizar(String cpf, String nome, LocalDate dataAniversario) throws ClienteInexistenteException {
        repositorio.atualizar(cpf, nome, dataAniversario);
        //repositorio.gravar();
    }

    /**
     * @param cpf
     * @return O objeto cliente procurado pelo cpf passado como parametro
     */
    public Cliente buscar(String cpf) throws ClienteInexistenteException {
        return repositorio.buscar(cpf);

    }

    /**
     *
     * @return ArrayList com todos os clientes cadastrados
     */
    public ArrayList<Cliente> getClientes() {
        return repositorio.getClientes();
    }

}
