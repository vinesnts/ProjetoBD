
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidades;

import java.io.Serializable;

/**
 *
 * @author Raquell Vieira, Adilson Júnior
 */
public class Carrinho implements Comparable<Carrinho>, Serializable{

    private Produto produto;
    private int quantidade;

    public Carrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Quant: " + quantidade + " | " + produto.toString();
    }

    @Override
    public int compareTo(Carrinho o2) {
        int comparacao = 0;
        if(this.getQuantidade() > o2.getQuantidade()){
            comparacao = 1;
        }else if (this.getQuantidade() < o2.getQuantidade()){
            comparacao = -1;
        }
        return comparacao;
    }
}
