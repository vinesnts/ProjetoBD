
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.entidades;

import java.io.Serializable;
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
    private ArrayList<Carrinho> arrayVendaProduto;

    public Venda(int id, LocalDate data, Cliente cliente, Funcionario funcionario, ArrayList<Carrinho> arrayVendaProduto) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.arrayVendaProduto = arrayVendaProduto;
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
    public double calcularValorVenda() {
        double valor = 0.0;
        for (int i = 0; i < this.getArrayVendaProduto().size(); i++) {
            valor += this.getArrayVendaProduto().get(i).getProduto().getPreco() * this.getArrayVendaProduto().get(i).getQuantidade();
        }
        return valor;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\tData: " + data.getDayOfMonth() + "/" + data.getMonthValue() + "\nCliente: " + cliente.getNome() + "\nFuncionario: " + funcionario.getNome() + "\tMatricula: " + funcionario.getMatricula() + "\n" + arrayVendaProduto.toString();
    }

}
