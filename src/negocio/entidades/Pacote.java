
package negocio.entidades;

import java.io.Serializable;

/**
 *
 * @author Raquell Vieira, Adilson Júnior
 */
public class Pacote implements Comparable<Pacote>, Serializable {

    private Produto produto;
    private int quantidade;

    public Pacote(Produto produto, int quantidade) {
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
        return "Quant: " + quantidade + " | " + produto;
    }

    @Override
    public int compareTo(Pacote o2) {
        int comparacao = 0;
        if(this.getQuantidade() > o2.getQuantidade()){
            comparacao = 1;
        }else if (this.getQuantidade() < o2.getQuantidade()){
            comparacao = -1;
        }
        return comparacao;
    }
}
