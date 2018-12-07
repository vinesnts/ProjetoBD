
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidades;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class Venda implements Serializable {

    private int id;
    private LocalDate data;
    private Cliente cliente;
    private Funcionario funcionario;
    private double precoTotal;
    private double desconto;
    private ArrayList<Carrinho> arrayVendaProduto;

    public Venda(int id, LocalDate data, Cliente cliente, Funcionario funcionario, double desconto, ArrayList<Carrinho> arrayVendaProduto) throws NumberFormatException {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.setDesconto(desconto, data);
        this.arrayVendaProduto = arrayVendaProduto;
        this.setPrecoTotal();
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public ArrayList<Carrinho> getArrayVendaProduto() {
        return arrayVendaProduto;
    }

    public void setArrayVendaProduto(ArrayList<Carrinho> arrayVendaProduto) {
        this.arrayVendaProduto = arrayVendaProduto;
    }

    /**
     *
     * @return O valor total da venda.
     */
    private void setPrecoTotal() {
        for (int i = 0; i < this.getArrayVendaProduto().size(); i++) {
            precoTotal += this.getArrayVendaProduto().get(i).getProduto().getPreco() * this.getArrayVendaProduto().get(i).getQuantidade();
            precoTotal -= (precoTotal * this.desconto);
        }
    }
    
    public double getPrecoTotal() {
        return precoTotal;
    }
    
    private void setDesconto(double desconto, LocalDate dataVenda) throws NumberFormatException {
        if (desconto < 0 || desconto > 15) {
                throw new NumberFormatException();
        }
        this.desconto = desconto / 100;
        if (cliente.getDataAniversario().equals(dataVenda)) {
                desconto += 0.05;
        }
    }
    
    public double getDesconto() {
        return this.desconto;
    }
    
    public double getPrecoSemDesconto() {
        return this.precoTotal / (this.desconto - 1) * -1;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\tData: " + data.getDayOfMonth() + "/" + data.getMonthValue() + "\nCliente: " + cliente.getNome() + "\nFuncionario: " + funcionario.getNome() + "\tMatricula: " + funcionario.getMatricula() + "\n" + arrayVendaProduto.toString();
    }
}
