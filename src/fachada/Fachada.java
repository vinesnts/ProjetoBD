
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
import negocio.excecoes.CPFInvalidoException;
import negocio.excecoes.DataInvalidaException;
import negocio.excecoes.FuncionarioExistenteException;
import negocio.excecoes.FuncionarioInexistenteException;
import negocio.excecoes.NomeInvalidoException;
import negocio.excecoes.ProdutosInsuficientesException;
import negocio.excecoes.QuantidadeInsuficienteException;
import negocio.excecoes.SenhaInvalidaException;
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
    public void adicionarCliente(String nome, String CPF, LocalDate dataAniversario) throws ClienteExistenteException, NomeInvalidoException, CPFInvalidoException {
        if (nome.equals(""))    throw new NomeInvalidoException();
        if (CPF.contains(" ") || CPF.equals("")) throw new CPFInvalidoException();
        Cliente cliente = new Cliente(nome, CPF, dataAniversario);
        clientes.adicionar(cliente);
    }

    public void removerCliente(String cpf) throws ClienteInexistenteException {
        Cliente cliente = clientes.buscar(cpf);
        if (cliente == null) {
            throw new ClienteInexistenteException();
        } else {
            clientes.remover(cliente);
        }
    }

    public void atualizarCliente(String cpf, String nome, LocalDate dataAniversario) throws ClienteInexistenteException, NomeInvalidoException {
        if (nome.equals(""))    throw new NomeInvalidoException();
        clientes.atualizar(cpf, nome, dataAniversario);
    }

    public Cliente getCliente(String cpf) throws ClienteInexistenteException {
        return clientes.buscar(cpf);
    }

    public ArrayList<Cliente> getClientes() {
        return clientes.getClientes();
    }

    //FUNCIONARIO
    public void adicionarFuncionario(String nome, String CPF, boolean eGerente, String senha) throws FuncionarioExistenteException, NomeInvalidoException, CPFInvalidoException, SenhaInvalidaException{
        if (nome.equals(""))    throw new NomeInvalidoException();
        if (CPF.contains(" ") || CPF.equals("")) throw new CPFInvalidoException();
        if (senha.equals(""))   throw new SenhaInvalidaException();
        Funcionario funcionario = new Funcionario(nome, CPF, eGerente, senha);
        funcionarios.adicionar(funcionario);
    }

    public void removerFuncionario(String cpf) throws FuncionarioInexistenteException {
        Funcionario funcionario = funcionarios.buscarFuncionario(cpf);
        funcionarios.remover(funcionario);
    }

    public void atualizarFuncionario(String cpf, String nome, boolean eGerente, String senha) throws FuncionarioInexistenteException, NomeInvalidoException, SenhaInvalidaException {
        if (nome.equals(""))    throw new NomeInvalidoException();
        if (senha.equals(""))   throw new SenhaInvalidaException();
        funcionarios.atualizar(cpf, nome, eGerente, senha);
    }

    public Funcionario getFuncionario(String cpf) throws FuncionarioInexistenteException {
        return funcionarios.buscarFuncionario(cpf);
    }

    public Funcionario getMelhorFunc() {
        return vendas.getMelhorFuncionario();
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios.getFuncionarios();
    }

    //PRODUTO
    public void adicionarProduto(String nome, double preco, String tamanho, String marca, String categoria) throws ProdutoExistenteException {
        Produto produto = new Produto(nome, preco, tamanho, marca, categoria);
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

    public ArrayList<Produto> getProdutos() {
        return produtos.getProdutos();
    }

    //Venda
    public Venda adicionarVenda(LocalDate data, Cliente cliente, Funcionario funcionario, double desconto, ArrayList<Carrinho> carrinho) throws DataInvalidaException, ProdutosInsuficientesException {
        if (data == null) {
            throw new DataInvalidaException();
        }
        if (this.getCarrinhos() == null) {
                throw new ProdutosInsuficientesException();
        } else if (this.getCarrinhos().isEmpty()) {
                throw new ProdutosInsuficientesException();
        }
            
        Venda venda = new Venda(vendas.getId(), data, cliente, funcionario, desconto, carrinho);
        return vendas.adicionar(venda);

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
    public Venda buscarVenda(int id) throws VendaInexistenteException {
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
