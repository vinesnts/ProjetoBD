/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import negocio.entidades.Carrinho;
import negocio.entidades.Cliente;
import negocio.entidades.Funcionario;
import negocio.entidades.Produto;
import negocio.excecoes.DataInvalidaException;
import negocio.excecoes.ClienteInexistenteException;
import negocio.excecoes.FuncionarioInexistenteException;
import negocio.excecoes.ProdutoInexistenteException;
import negocio.excecoes.ProdutosInsuficientesException;
import negocio.excecoes.QuantidadeInsuficienteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorVenda implements Initializable {

    @FXML
    private Button botaoSalvar;
    @FXML
    private Button botaoCancelar;
    @FXML
    private ListView<Carrinho> listProdutos;
    @FXML
    private TextField txCampoCliente;
    @FXML
    private Button botaoPesquisarProdutos;
    @FXML
    private Button BotaoPesuisarCliente;
    @FXML
    private TextField txCampoQuantidade;
    @FXML
    private Button BotaoListar;
    @FXML
    private TextArea txAreaInformativa;
    @FXML
    private Button botaoAdicionarProdutos;
    @FXML
    private TextField txCampoProduto;
    private Fachada fachada;
    @FXML
    private DatePicker data;

    private List<Carrinho> lista = new ArrayList();
    private ObservableList<Carrinho> observableList;
    //private GerenciamentoCarrinho carrinhos;

    private double desconto;

    @FXML
    private Label labelDeconto;
    @FXML
    private TextField txCampoDesconto;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label labelMsgCliente;
    @FXML
    private Label labelMsgDesconto;
    @FXML
    private Label labelMsgProduto;
    @FXML
    private Label labelMsgQuantidade;
    @FXML
    private Label labelMsgData;
    @FXML
    private Label labelMsgCarrinho;
    @FXML
    private Label labelMsgRemoverProduto;
    @FXML
    private Button botaoRemoverProduto;
    @FXML
    private Label labelMsgCarrinho1;
    @FXML
    private Button botaoAplicarDesconto;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fachada = Fachada.getInstance();
    }

    @FXML
    private void botaoSalvar(ActionEvent event) {
        Funcionario funcionario = funcionario = fachada.getLogado();
        Cliente cliente;
        LocalDate dataVenda;

        try {
            cliente = fachada.getCliente(txCampoCliente.getText());
            dataVenda = data.getValue();
            if (dataVenda == null) {
                throw new DataInvalidaException();
            }
            if (fachada.getCarrinhos() == null) {
                throw new ProdutosInsuficientesException();
            }else if(fachada.getCarrinhos().isEmpty()){
                throw  new ProdutosInsuficientesException();
            }
            
            fachada.adicionarVenda(dataVenda, cliente, funcionario, fachada.getCarrinhos());
            desconto = Integer.parseInt(txCampoDesconto.getText()) / 100.0;
            if (desconto < 0 || desconto > 0.15) {
                throw new NumberFormatException();
            }
            if(cliente.getDataAniversario().equals(dataVenda)){
                desconto+= 0.05;
            }
            txAreaInformativa.setText("Valor Total da venda: \t" + fachada.getVendas().get(fachada.getVendas().size() - 1).calcularValorVenda() + "\nDesconto: " + (fachada.getVendas().get(fachada.getVendas().size() - 1).calcularValorVenda() * (desconto)) + "\nValor Com Desconto: " + (fachada.getVendas().get(fachada.getVendas().size() - 1).calcularValorVenda() - (fachada.getVendas().get(fachada.getVendas().size() - 1).calcularValorVenda() * (desconto))));
            limparLabels();
            limparCampos();
            fachada.getCarrinhos().clear();
        } catch (ClienteInexistenteException error) {
            labelMsgCliente.setText(error.getMessage());
        } catch (NumberFormatException error) {
            labelMsgDesconto.setText("Desconto invalido.");
        } catch (ProdutosInsuficientesException error) {
            labelMsgCarrinho.setText(error.getMessage());
        } catch (DataInvalidaException error) {
            labelMsgData.setText(error.getMessage());
        }

    }

    @FXML
    private void botaoCancelar(ActionEvent event) throws IOException {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void pesquisarProdutos(ActionEvent event) {
        try {
            Produto produto = fachada.buscarProdutoId(Integer.parseInt(txCampoProduto.getText()));
            labelMsgProduto.setText("Produto " + produto.getNome() + " encontrado.");
        } catch (ProdutoInexistenteException erro) {
            labelMsgProduto.setText(erro.getMessage());
        } catch (NumberFormatException error) {
            labelMsgProduto.setText("Produto não existe!");
        }
    }

    @FXML
    private void PesquisarCliente(ActionEvent event) {
        try {
            Cliente cliente = fachada.getCliente(txCampoCliente.getText());
            if (cliente.getCpf().equals(txCampoCliente.getText())) {
                labelMsgCliente.setText("Cliente encontrado");
            }
        } catch (ClienteInexistenteException erro) {
            labelMsgCliente.setText(erro.getMessage());
            txCampoCliente.clear();
        }
    }


    @FXML
    private void ListarProdutosAdicionados(ActionEvent event) throws ProdutoInexistenteException {
        listProdutos.setItems(null);
        lista = lista = new ArrayList();
        limparCampos();
        limparLabels();
        double desconto1;
        int x = 0;
        try {
            if (fachada.getCarrinhos().size() > 0) {
                for (int i = 0; i < fachada.getCarrinhos().size(); i++) {
                    lista.add(fachada.getCarrinhos().get(i));
                }
                txAreaInformativa.setText("Valor dos produtos ja adicionados\t" + calcularValorCarrinho());
                observableList = FXCollections.observableArrayList(lista);
                listProdutos.setItems(observableList);
            } else {
                throw new QuantidadeInsuficienteException();
            }
        } catch (QuantidadeInsuficienteException error) {
        } catch (NumberFormatException error) {
            labelMsgDesconto.setText("Desconto Invalido.");
        }
    }

    @FXML
    private void adicionarProdutos(ActionEvent event
    ) {

        int quantidade;
        labelMsgProduto.setText("");
        labelMsgQuantidade.setText("");
        try {
            quantidade = Integer.parseInt(txCampoQuantidade.getText());
            if (quantidade < 1) {
                throw new QuantidadeInsuficienteException();
            }
            Produto produto = fachada.buscarProdutoId(Integer.parseInt(txCampoProduto.getText()));
            txCampoProduto.clear();
            txCampoQuantidade.clear();
            limparLabels();
            labelMsgProduto.setText("Produto Adicionado.");
            if (produto != null) {
                fachada.adicionarCarrinho(produto, quantidade);
            } else {

            }
        } catch (ProdutoInexistenteException error) {
            labelMsgProduto.setText(error.getMessage());
        } catch (NumberFormatException error) {

        } catch (QuantidadeInsuficienteException error) {
            labelMsgQuantidade.setText(error.getMessage());

        }
    }

    @FXML
    private void removerProduto(ActionEvent event
    ) {
        Carrinho carrinho;
        boolean existe = false;
        labelMsgProduto.setText("");
        labelMsgQuantidade.setText("");
        try {
            if (fachada.getCarrinhos().size() > 0) {
                for (int i = 0; i < fachada.getCarrinhos().size(); i++) {
                    if (fachada.getCarrinhos().get(i).getProduto().getId() == (Integer.parseInt(txCampoProduto.getText()))) {
                        carrinho = new Carrinho(fachada.getCarrinhos().get(i).getProduto(),
                                Integer.parseInt(txCampoQuantidade.getText()));
                        fachada.removerCarrinho(carrinho);
                        labelMsgRemoverProduto.setText("Produto Removido");
                        limparCampos();
                        //  limparLabels();
                    }
                }
                if (existe == true) {
                    throw new ProdutoInexistenteException();
                }

            } else {
                throw new ProdutosInsuficientesException();
            }
        } catch (ProdutosInsuficientesException | ProdutoInexistenteException error) {
            labelMsgRemoverProduto.setText(error.getMessage());
        } catch (QuantidadeInsuficienteException error) {
            labelMsgQuantidade.setText(error.getMessage());

        } catch (NumberFormatException error) {
            labelMsgQuantidade.setText("Quantidade Insuficientes.");
        }
    }

    @FXML
    private void seletorDeData(ActionEvent event) {
    }

    @FXML
    private void carregarListProdutos(MouseEvent event) {
    }

    private void limparCampos() {
        txCampoCliente.clear();
        txCampoDesconto.clear();
        txCampoProduto.clear();
        txCampoQuantidade.clear();
        data.setValue(null);

    }

    private void limparLabels() {
        labelMsgDesconto.setText("");
        labelMsgCliente.setText("");
        labelMsgData.setText("");
        labelMsgProduto.setText("");
        labelMsgQuantidade.setText("");
        labelMsgCarrinho.setText("");
        labelMsgRemoverProduto.setText("");
        labelMsgRemoverProduto.setText("");

    }

    void carregarlista() {
    }

    public double calcularValorCarrinho() {

        double valor = 0;
        if (fachada.getCarrinhos() != null) {
            for (int i = 0; i < fachada.getCarrinhos().size(); i++) {
                valor += fachada.getCarrinhos().get(i).getProduto().getPreco() * fachada.getCarrinhos().get(i).getQuantidade();

            }
        }
        return valor;
    }

    @FXML
    private void botaoAplicarDesconto(ActionEvent event) {
    }

}
