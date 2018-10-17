
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.gerenciamento;

import dados.repositorioArrayList.RepositorioVenda;
import negocio.entidades.Funcionario;
import negocio.entidades.Venda;
import negocio.excecoes.VendaInexistenteException;

import java.util.ArrayList;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class GerenciamentoVenda {

    private RepositorioVenda repositorio;
    private static GerenciamentoVenda instancia;

    private GerenciamentoVenda() {
        this.repositorio = new RepositorioVenda();
        repositorio.ler();
        
    }

    public static GerenciamentoVenda getInstance() {
        if (instancia == null) {
            instancia = new GerenciamentoVenda();
        }
        return instancia;
    }

    /**
     *
     * @param venda Adiciona uma nova venda ao repositorio
     */
    public void adicionar(Venda venda) {
        repositorio.adicionar(venda);
        repositorio.gravar();
    }

    /**
     *
     * @param venda Verifica se a venda existe, se sim remove, caso não lança
     * exceção
     * @throws negocio.excecoes.VendaInexistenteException
     */
    public void remover(Venda venda) throws VendaInexistenteException {
        boolean existe = repositorio.verificarExistencia(venda);
        if (existe == false) {
            throw new VendaInexistenteException();
        } else {
            repositorio.remover(venda);
            repositorio.gravar();
        }
    }

    /**
     *
     * @param venda
     * @throws VendaInexistenteException Verifica se a venda existe, se sim
     * atualiza, caso não lança exceção
     */
    public void atualizar(Venda venda) throws VendaInexistenteException {
        Venda existe = repositorio.buscar(venda.getId());
        if (existe == null) {
            throw new VendaInexistenteException();
        } else {
            repositorio.atualizar(venda);
            repositorio.gravar();
        }
    }

    /**
     *
     * @param id
     * @return O objeto venda passado como parametro caso exista
     */
    public Venda buscar(int id) throws VendaInexistenteException {
        return repositorio.buscar(id);
    }

    /**
     *
     * @return Array com todas as vendas cadastradas
     */
    public ArrayList<Venda> getVendas() {
        return repositorio.getVendas();
    }

    /**
     *
     * @return o indice que sera gerado no repositorioVenda para a venda.
     */
    public int getId() {
        return repositorio.getId();
    }

    /**
     *
     * @return O melhor funcionario daquele periodode tempo // implementar
     * isso(tempo)
     */
    public Funcionario getMelhorFuncionario() {
        return repositorio.getMelhorFuncionario();
    }

}
