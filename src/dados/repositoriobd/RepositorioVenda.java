/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositoriobd;

import dados.repositorioInterface.IRepositorioVenda;
import negocio.entidades.Venda;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import negocio.entidades.Funcionario;
import negocio.excecoes.VendaInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioVenda implements IRepositorioVenda {

    private ArrayList<Venda> vendas;
    private int id;
    FileOutputStream arquivoParaGravar;
    FileInputStream recuperadorDeArquivos;
    ObjectOutputStream escritor;
    ObjectInputStream leitor;

    public RepositorioVenda() {
        vendas = new ArrayList();
        this.id = 1;

    }

    /**
     *
     * @param venda Adiciona o objeto venda no repositorio de vendas
     */

    @Override
    public void adicionar(Venda venda) {
        vendas.add(venda);
        id++;
    }

    /**
     *
     * @param venda Remove o objeto venda do repositorio de vendas
     */

    @Override
    public void remover(Venda venda) {
        vendas.remove(venda);
    
    }

    /**
     *
     * @param venda Atualiza uma venda ja cadastrada com os novos dados passados
     * no parametro
     */

    @Override
    public void atualizar(Venda venda) {
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getId() == venda.getId()) {
                vendas.get(i).setCliente(venda.getCliente());
                vendas.get(i).setData(venda.getData());
                vendas.get(i).setFuncionario(venda.getFuncionario());
                vendas.get(i).setArrayVendaProduto(venda.getArrayVendaProduto());
            }
        }
    }

    /**
     *
     * @param id
     * @return O objeto Venda caso ele exista, caso não lança exceção
     * @throws VendaInexistenteException
     */

    @Override
    public Venda buscar(int id) throws VendaInexistenteException {

        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getId() == id) {
                return vendas.get(i);
            }
        }
        throw new VendaInexistenteException();
    }

    @Override
    public boolean verificarExistencia(Venda venda) {
        boolean existe = false;
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getId() == venda.getId()) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     *
     * @return O Array de vendas cadastradas no repositorio
     */

    public ArrayList<Venda> getVendas() {
        return vendas;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo para gravar em um arquivo binario o array de vendas do repositorio
     * de vendas
     */

    public void gravar() {

        try {
            this.arquivoParaGravar = new FileOutputStream("Dados/ArquivoVendas.dat");
            this.escritor = new ObjectOutputStream(arquivoParaGravar);
            this.escritor.writeObject(vendas);
            this.escritor.flush();
            this.escritor.close();
            this.arquivoParaGravar.flush();
            this.arquivoParaGravar.close();

        } catch (IOException erro) {
            erro.printStackTrace();
        }

    }

    /**
     * Metodo para recuperar de um arquivo binario o Array de vendas e carregalo
     * no repositorio de vendas
     */

    public void ler() {

        try {
            recuperadorDeArquivos = new FileInputStream("Dados/ArquivoVendas.dat");
            this.leitor = new ObjectInputStream(recuperadorDeArquivos);
            vendas = (ArrayList<Venda>) leitor.readObject();
            if(vendas!=null){
            
            this.id = vendas.get(vendas.size() - 1).getId() + 1;
            }
            this.leitor.close();
            recuperadorDeArquivos.close();

        } catch (IOException | ClassNotFoundException erro) {
        	System.out.println("Nada nos repositorios!");
        }
        
    }

    /**
     *
     * @param cpf
     * @return o valor total das vendas do funcionário passado como parametro
     */
    private double getValorTotalDasVendasDoFuncionario(String cpf) {
        double valorVenda = 0.0;
        for (int i = 0; i < vendas.size(); i++) {
            if (cpf.equals(vendas.get(i).getFuncionario().getCpf())) {
                valorVenda += vendas.get(i).getPrecoTotal();
            }
        }
        return valorVenda;
    }

    /**
     *
     * @return o melhor funcionario
     */
    public Funcionario getMelhorFuncionario() {
        double maiorVenda = 0.0, valorVenda;
        Funcionario funcionario = null;

        for (int i = 0; i < vendas.size(); i++) {
            valorVenda = getValorTotalDasVendasDoFuncionario(vendas.get(i).getFuncionario().getCpf());
            if (valorVenda > maiorVenda) {
                maiorVenda = valorVenda;
                funcionario = vendas.get(i).getFuncionario();
            }
        }
        return funcionario;
    }
}
