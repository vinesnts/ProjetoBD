
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.gerenciamento;

import dados.repositoriobd.RepositorioFuncionario;
import negocio.entidades.Funcionario;
import java.util.ArrayList;
import negocio.excecoes.FuncionarioExistenteException;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class GerenciamentoFuncionario {

    private RepositorioFuncionario repositorio;
    private static GerenciamentoFuncionario instancia;

    private GerenciamentoFuncionario() {
        this.repositorio = RepositorioFuncionario.getInstance();
        //this.repositorio.ler();

    }

    public static GerenciamentoFuncionario getInstance() {
        if (instancia == null) {
            instancia = new GerenciamentoFuncionario();
        }
        instancia = new GerenciamentoFuncionario();
        return instancia;
    }

    /**
     *
     * @param funcionario Verifica se o funcionario passado pelo parametro
     * existe, se sim lança esceção, caso não o cadastro é realizado
     * @throws negocio.excecoes.FuncionarioExistenteException
     */
    public void adicionar(Funcionario funcionario) throws FuncionarioExistenteException {
        if (repositorio.verificarExistencia(funcionario)) {
            throw new FuncionarioExistenteException();
        } else {
            repositorio.adicionar(funcionario);
            //repositorio.gravar();
        }
    }

    /**
     *
     * @param funcionario Verifica se o funcionario passado como parâmetro
     * existe, se não lança exceção, se existir a remoção é realizada
     * @throws negocio.excecoes.FuncionarioInexistenteException
     */
    public void remover(Funcionario funcionario) throws FuncionarioInexistenteException {
        boolean existe = repositorio.verificarExistencia(funcionario);
        if (existe == false) {
            throw new FuncionarioInexistenteException();
        } else {
            repositorio.remover(funcionario);
            //repositorio.gravar();
        }
    }

    /**
     *
     * @param funcionario verifica se o cpf pertence a algum cliente já
     * cadastrado, se sim a atualização é realizada, caso não lança exceção
     * @throws negocio.excecoes.FuncionarioInexistenteException
     */
    public void atualizar(String cpf, String nome, boolean eGerente, String senha) throws FuncionarioInexistenteException {
        repositorio.atualizar(cpf, nome, eGerente, senha);
        //repositorio.gravar();
    }

    /**
     *
     * @param matricula
     * @return Retorna o objeto funcionario se ele ja estiver cadastrado no
     * repositorio, caso não lança exceção
     */
    public Funcionario buscarFuncionario(String matricula) throws FuncionarioInexistenteException {
        return repositorio.buscar(matricula);
    }

    /**
     *
     * @return Array com todos os funcionarios cadastrados
     */
    public ArrayList<Funcionario> getFuncionarios() {
        return repositorio.getFuncionarios();
    }

    /**
     *
     * @return Array com funcionarios que são vendedores que foram cadastrados
     * no repositorio de funcionarios
     */
    public ArrayList<Funcionario> listarVendedores() {
        return repositorio.getVendedores();

    }

    /**
     *
     * @return Array com os funcionarios que são gerentes que foram cadastrados
     * no repositorio funcionarios
     */
    public ArrayList<Funcionario> listarGerentes() {
        return repositorio.getGerentes();
    }

}
