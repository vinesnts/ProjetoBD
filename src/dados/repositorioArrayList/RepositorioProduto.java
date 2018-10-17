/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositorioArrayList;

import dados.repositorioInterface.IRepositorioProduto;
import negocio.entidades.Produto;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import negocio.excecoes.ProdutoInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioProduto implements IRepositorioProduto {

    private ArrayList<Produto> produtos;
    private int id;
    FileOutputStream arquivoParaGravar;
    FileInputStream recuperadorDeArquivos;
    ObjectOutputStream escritor;
    ObjectInputStream leitor;

    /**
     * Construtor de Repositorio Produto
     */
    public RepositorioProduto() {
        produtos = new ArrayList();
        this.id = 1;
    }

    /**
     *
     * @param produto Adiciona o objeto produto no repositorio
     */
    @Override
    public void adicionar(Produto produto) {
        id++;
        produtos.add(produto);

    }

    /**
     *
     * @param produto Remove o objeto produto do repositorio
     */
    @Override
    public void remover(Produto produto) {
        produtos.remove(produto);
    }

    /**
     *
     * @param produto Atualiza os dados do produto ja existente com os novos
     * dados passados
     */
    @Override
    public void atualizar(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.get(i).setNome(produto.getNome());
                produtos.get(i).setPreco(produto.getPreco());
                produtos.get(i).setTamanho(produto.getTamanho());
                produtos.get(i).setMarca(produto.getMarca());
                produtos.get(i).setCategoria(produto.getCategoria());
            }
        }
    }

    /**
     *
     * @param id
     * @return Retorna o objeto Produto.
     * @throws negocio.excecoes.ProdutoInexistenteException
     */
    @Override
    public Produto buscar(int id) throws ProdutoInexistenteException {

        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == id) {
                return produtos.get(i);

            }
        }
        throw new ProdutoInexistenteException();

    }

    /**
     *
     * @param nome
     * @return Verifica se o produto existe, se sim retorna o objeto Produto,
     * caso não retorna null.
     * @throws negocio.excecoes.ProdutoInexistenteException
     */
    public Produto buscarPeloNome(String nome) throws ProdutoInexistenteException {

        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equals(nome)) {
                return produtos.get(i);
            }
        }
        throw new ProdutoInexistenteException();
    }

    /**
     *
     * @param produto
     * @return
     */
    @Override
    public boolean verificarExistencia(Produto produto) {
        boolean existe = false;
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                existe = true;
            }
        }
        return existe;
    }
    //Retorna o Array de Produtos.

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    /**
     * @return Id que sera colocado no produto durante cadastro
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo para gravar o array de produtos cadastrados no repositorio de
     * produtos emum arquivo binario
     */
    public void gravar() {

        try {
            this.arquivoParaGravar = new FileOutputStream("Dados/ArquivoProdutos.dat");
            this.escritor = new ObjectOutputStream(arquivoParaGravar);
            this.escritor.writeObject(produtos);
            this.escritor.flush();
            this.escritor.close();
            this.arquivoParaGravar.flush();
            this.arquivoParaGravar.close();

        } catch (IOException erro) {
            erro.printStackTrace();
        }

    }

    /**
     * Metodo para leitura de arquivo, ira ler o array de produto guardado em
     * binario no arquivo e carrega-os no repositorio de produtos
     */
    public void ler() {

        try {
            recuperadorDeArquivos = new FileInputStream("Dados/ArquivoProdutos.dat");
            this.leitor = new ObjectInputStream(recuperadorDeArquivos);
            produtos = (ArrayList<Produto>) leitor.readObject();
            if(produtos!=null){
            	this.id = produtos.get(produtos.size() - 1).getId() + 1;
            }
            this.leitor.close();
            recuperadorDeArquivos.close();

        } catch (IOException | ClassNotFoundException erro) {
            System.out.println("Nada nos repositorios!");

        }

    }

}
