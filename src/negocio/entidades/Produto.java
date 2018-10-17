
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidades;

import java.io.Serializable;
import negocio.excecoes.PrecoInvalidoException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class Produto implements Serializable {

    private int id;
    private String nome;
    private double preco;
    private String tamanho;
    private String marca;
    private String categoria;

    public Produto(int id, String nome, double preco, String tamanho, String marca, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tamanho = tamanho;
        this.marca = marca;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setPreco(String preco) throws PrecoInvalidoException {
        try {
            this.preco = Double.parseDouble(preco);
        } catch (NumberFormatException erro) {
            throw new PrecoInvalidoException();
        }
    }
    
    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Id: " + id + " | Nome: " + nome + " | Preço: " + preco + " | Tamanho: " + tamanho + " | Marca: " + marca + " | Categoria: " + categoria;
    }
}
