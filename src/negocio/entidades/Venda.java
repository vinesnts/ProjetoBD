
package negocio.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class Venda implements Serializable {

    private int id;

    private LocalDateTime data;
    private Cliente cliente;
    private Funcionario funcionario;
    private double precoTotal;
    private double desconto;
    private ArrayList<Pacote> arrayVendaProduto;

    public Venda(int id, LocalDateTime data, Cliente cliente, Funcionario funcionario, double desconto) throws NumberFormatException {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.setDesconto(desconto, data);
    }
    
    public Venda(int id, LocalDateTime data, Cliente cliente, Funcionario funcionario, double desconto, ArrayList<Pacote> arrayVendaProduto) throws NumberFormatException {
        this(id, data, cliente, funcionario, desconto);
        this.arrayVendaProduto = arrayVendaProduto;
        this.setPrecoTotal();
    }
    
    public Venda(LocalDateTime data, Cliente cliente, Funcionario funcionario, double desconto, ArrayList<Pacote> arrayVendaProduto) throws NumberFormatException {
        this(0, data, cliente, funcionario, desconto);
        this.arrayVendaProduto = arrayVendaProduto;
        this.setPrecoTotal();
    }

    public Venda(int idVenda, LocalDateTime data, Cliente cliente, Funcionario funcionario, double precoTotal, double desconto) {
        this(idVenda, data, cliente, funcionario, desconto);
        this.precoTotal = precoTotal;
    }
    
    public int getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
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

    public ArrayList<Pacote> getArrayVendaProduto() {
        return arrayVendaProduto;
    }

    public void setArrayVendaProduto(ArrayList<Pacote> arrayVendaProduto) {
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
    
    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }
    

    public double getPrecoTotal() {
        return precoTotal;
    }
    
    private void setDesconto(double desconto, LocalDateTime dataVenda) throws NumberFormatException {
        if (desconto < 0 || desconto > 15) {
                throw new NumberFormatException();
        }
        this.desconto = desconto / 100;
        if (cliente.getDataAniversario().equals(dataVenda.toLocalDate())) {
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
        return "Id: " + id + "\tData: " + data.getDayOfMonth() + "/" + data.getMonthValue() + "\nCliente: " + cliente.getNome() + "\nFuncionario: " + funcionario.getNome() + "\n" + arrayVendaProduto.toString();
    }
}
