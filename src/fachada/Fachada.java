
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import negocio.entidades.Cliente;
import negocio.entidades.Funcionario;
import negocio.entidades.Produto;
import negocio.entidades.Venda;
import negocio.excecoes.ClienteExistenteException;
import negocio.excecoes.ClienteInexistenteException;
import negocio.excecoes.ProdutoExistenteException;
import negocio.excecoes.ProdutoInexistenteException;
import negocio.excecoes.VendaInexistenteException;
import negocio.gerenciamento.GerenciamentoCliente;
import negocio.gerenciamento.GerenciamentoFuncionario;
import negocio.gerenciamento.GerenciamentoProduto;
import negocio.gerenciamento.GerenciamentoVenda;
import java.time.LocalDate;
import java.util.ArrayList;
import negocio.entidades.Carrinho;
import negocio.excecoes.FuncionarioExistenteException;
import negocio.excecoes.FuncionarioInexistenteException;
import negocio.excecoes.QuantidadeInsuficienteException;
import negocio.gerenciamento.GerenciamentoCarrinho;

/**
 *
 * @author Raquell Vieira
 */
public class Fachada {

    private static Fachada instancia;
    private Funcionario logado;
    private GerenciamentoCliente clientes;
    private GerenciamentoFuncionario funcionarios;
    private GerenciamentoProduto produtos;
    private GerenciamentoVenda vendas;
    private GerenciamentoCarrinho carrinhos;

    private Fachada() {
        clientes = GerenciamentoCliente.getInstance();
        funcionarios = GerenciamentoFuncionario.getInstance();
        produtos = GerenciamentoProduto.getInstance();
        vendas = GerenciamentoVenda.getInstance();
        carrinhos = GerenciamentoCarrinho.getInstancia();
    }
    
    public static Fachada getInstance() {
        if (instancia == null) instancia = new Fachada();
        return instancia;
    }
    
    public Funcionario getLogado() {
        return this.logado;
    }
    
    public void setLogado(Funcionario logado) {
        this.logado = logado;
    }
    //CLIENTE
    public void adicionarCliente(String nome, String CPF, LocalDate dataAniversario) throws ClienteExistenteException {
        Cliente cliente = new Cliente(nome, CPF, dataAniversario);
        clientes.adicionar(cliente);
    }

    public void removerCliente(String cpf) throws ClienteInexistenteException {
        Cliente cliente = clientes.buscar(cpf);
        clientes.remover(cliente);
    }

    public void atualizarCliente(Cliente cliente) throws ClienteInexistenteException {
        Cliente c = clientes.buscar(cliente.getCpf());
        clientes.atualizar(cliente);
    }

    public Cliente getCliente(String cpf) throws ClienteInexistenteException {
        return clientes.buscar(cpf);
    }

    public ArrayList<Cliente> getClientes() {
        return clientes.getClientes();
    }

    //FUNCIONARIO
    public void adicionarFuncionario(String nome, String CPF, boolean eGerente, String matricula, String senha) throws FuncionarioExistenteException {
        matricula = matricula.toUpperCase();
        Funcionario funcionario = new Funcionario(nome, CPF, eGerente, matricula, senha);
        funcionarios.adicionar(funcionario);
    }

    public void removerFuncionario(String matricula) throws FuncionarioInexistenteException {
        matricula = matricula.toUpperCase();
        Funcionario funcionario = funcionarios.buscarFuncionario(matricula);
        funcionarios.remover(funcionario);
    }

    public void atualizarFuncionario(Funcionario funcionario) throws FuncionarioInexistenteException {
        Funcionario f = funcionarios.buscarFuncionario(funcionario.getMatricula());
        funcionarios.atualizar(funcionario);
    }

    public Funcionario getFuncionario(String matricula) throws FuncionarioInexistenteException {
        matricula = matricula.toUpperCase();
        return funcionarios.buscarFuncionario(matricula);
    }

    public Funcionario getMelhorFunc() {
        return vendas.getMelhorFuncionario();
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios.getFuncionarios();
    }

    //PRODUTO
    public void adicionarProduto(String nome, double preco, String tamanho, String marca, String categoria) throws ProdutoExistenteException {
        Produto produto = new Produto(produtos.getIdProduto(), nome, preco, tamanho, marca, categoria);
        produtos.adicionar(produto);

    }

    public void removerProduto(int id) throws ProdutoInexistenteException {
        Produto produto = produtos.buscar(id);
        produtos.remover(produto);
    }

    public void atualizarProduto(Produto produto) throws ProdutoInexistenteException {
        Produto prod = produtos.buscar(produto.getId());
        produtos.atualizar(produto);

    }

    /**
     *
     * @param id
     * @return O objeto produto se ele existir no repositorio
     * @throws ProdutoInexistenteException Se ele não for encontrado
     */
    public Produto buscarProdutoId(int id) throws ProdutoInexistenteException {
        return produtos.buscar(id);
    }

    public Produto getProdutoBuscarPeloNome(String nome) throws ProdutoInexistenteException {
        return produtos.buscaPeloNome(nome);
    }

    public int getIdProduto() {
        return produtos.getIdProduto();
    }

    public ArrayList<Produto> getProdutos() {
        return produtos.getProdutos();
    }

    //Venda
    public void adicionarVenda(LocalDate data, Cliente cliente, Funcionario funcionario, ArrayList<Carrinho> carrinho) {
        Venda venda = new Venda(vendas.getId(), data, cliente, funcionario, carrinho);
        vendas.adicionar(venda);

    }

    public void removerVenda(int id) throws VendaInexistenteException {
        Venda venda = vendas.buscar(id);
        vendas.remover(venda);
    }

    /**
     *
     * @param venda Lançara exceção se a venda não existir no repositorio
     * @throws VendaInexistenteException
     */
    public void atualizarVenda(Venda venda) throws VendaInexistenteException {
        Venda v = vendas.buscar(venda.getId());
        vendas.atualizar(venda);

    }

    /**
     *
     * @return Array com todas as vendas
     */
    public ArrayList<Venda> getVendas() {
        return vendas.getVendas();
    }

    /**
     *
     * @param id
     * @return O objeto venda se ixistir no repositorio
     * @throws VendaInexistenteException
     */
    public Venda BuscarVenda(int id) throws VendaInexistenteException {
        return vendas.buscar(id);
    }

    //CARRINHO
    /**
     *
     * @param produto
     * @param quantidade Recebe um produto e sua quantidade e adiciona num
     * carrinho Adiciona-o ao repositorio de carrinho durante uma venda
     */
    public void adicionarCarrinho(Produto produto, int quantidade) {
        Carrinho carrinho = new Carrinho(produto, quantidade);
        carrinhos.adicionar(carrinho);
    }/**
     * 
     * @param carrinho
     * @throws ProdutoInexistenteException
     * @throws QuantidadeInsuficienteException 
     */
    public void removerCarrinho(Carrinho carrinho)throws ProdutoInexistenteException,QuantidadeInsuficienteException{
        carrinhos.remover(carrinho);
    
    }

    public ArrayList<Carrinho> getCarrinhos() {
        return carrinhos.getCarrinhos();
    }
}
