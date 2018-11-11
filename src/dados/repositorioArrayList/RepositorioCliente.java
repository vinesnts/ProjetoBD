/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositorioArrayList;

import negocio.entidades.Cliente;
import java.util.ArrayList;
import dados.repositorioInterface.IRepositorioCliente;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import negocio.excecoes.ClienteInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioCliente implements IRepositorioCliente {

    private ArrayList<Cliente> clientes;
    FileOutputStream arquivoParaGravar;
    FileInputStream recuperadorDeArquivos;
    ObjectOutputStream escritor;
    ObjectInputStream leitor;

    public RepositorioCliente() {
        clientes = new ArrayList();
    }

    /**
     *
     * @param cliente Adiciona o objeto cliente no repositorio de cliente
     */

    @Override
    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     *
     * @param cliente Remove o cliente passado como parametro do repositorio
     */
    @Override
    public void remover(Cliente cliente) {
        clientes.remove(cliente);
    }

    /**
     *
     * @param cliente Atualizara os dados do cliente ja existente com os novos
     * dados passados
     */
    @Override
    public void atualizar(String cpf, String nome, LocalDate dataAniversario) throws ClienteInexistenteException {
        Cliente cliente = this.buscar(cpf);
        cliente.setNome(nome);
        cliente.setDataAniversario(dataAniversario);
    }

    /**
     *
     * @param cpf
     * @return Retorna o objeto cliente.
     * @throws negocio.excecoes.ClienteInexistenteException
     */
    @Override
    public Cliente buscar(String cpf) throws ClienteInexistenteException {
        Cliente cliente;
        for (int i = 0; i < clientes.size(); i++) {
            if (cpf.equals(clientes.get(i).getCpf())) {
                return cliente = this.clientes.get(i);
            }
        }
        throw new ClienteInexistenteException();
    }

    /**
     *
     * @param cliente
     * @return Verifica se o cliente ja foi cadastrado, se sim retorna true,
     * caso não retorna false
     */
    @Override
    public boolean verificarExistencia(Cliente cliente) {
        boolean existe = false;

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cliente.getCpf())) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     *
     * @return o Array de Clientes.
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Metoso para gravar o Array de clientes do repositorio em um arquivo
     * binario
     *
     * @throws IOException
     */

    public void gravar() {

        try {
            this.arquivoParaGravar = new FileOutputStream("Dados/ArquivoClientes.dat");
            this.escritor = new ObjectOutputStream(arquivoParaGravar);
            this.escritor.writeObject(clientes);
            this.escritor.flush();
            this.escritor.close();
            this.arquivoParaGravar.flush();
            this.arquivoParaGravar.close();

        } catch (IOException erro) {
            erro.printStackTrace();
        }

    }

    /**
     * Metodo para recuperar de um arquivo binario os Array de clientes
     * previamente cadastrados, e carregalos no repositorio de clientes
     */

    public void ler() {

        try {
            recuperadorDeArquivos = new FileInputStream("Dados/ArquivoClientes.dat");
            this.leitor = new ObjectInputStream(recuperadorDeArquivos);
            clientes = (ArrayList<Cliente>) leitor.readObject();
            this.leitor.close();
            recuperadorDeArquivos.close();

        } catch (IOException | ClassNotFoundException error) {
        	System.out.println("Nada nos repositorios!");
        }
    }

}
