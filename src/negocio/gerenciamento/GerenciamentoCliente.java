
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.gerenciamento;

import dados.repositorioArrayList.RepositorioCliente;
import negocio.entidades.Cliente;
import negocio.excecoes.ClienteExistenteException;
import negocio.excecoes.ClienteInexistenteException;
import java.io.IOException;
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
        this.repositorio = new RepositorioCliente();
        repositorio.ler();
    }

    /**
     *
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
            repositorio.gravar();

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
        boolean existe = repositorio.verificarExistencia(cliente);
        if (existe == false) {
            throw new ClienteInexistenteException();
        } else {
            repositorio.remover(cliente);
            repositorio.gravar();
        }
    }

    /**
     * @param cliente
     * @throws ClienteInexistenteException Verifica se o cpf pertence a algum
     * cliente já cadastrado, se sim atualiza os dados do cliente, caso não a
     * exceção é lançada
     */
    public void atualizar(Cliente cliente) throws ClienteInexistenteException {
        Cliente clienteAux = repositorio.buscar(cliente.getCpf());
        if (clienteAux == null) {
            throw new ClienteInexistenteException();
        } else {
            repositorio.atualizar(cliente);
            repositorio.gravar();
        }
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
