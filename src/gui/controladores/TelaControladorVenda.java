
package gui.controladores;

import fachada.Fachada;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import negocio.TextFieldFormatter;
import negocio.entidades.Carrinho;
import negocio.entidades.Cliente;
import negocio.entidades.Funcionario;
import negocio.entidades.Produto;
import negocio.entidades.Venda;
import negocio.excecoes.DataInvalidaException;
import negocio.excecoes.ClienteInexistenteException;
import negocio.excecoes.ProdutoInexistenteException;
import negocio.excecoes.ProdutosInsuficientesException;
import negocio.excecoes.QuantidadeInsuficienteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorVenda implements Initializable {

    
    private List<Carrinho> lista = new ArrayList();
    private ObservableList<Carrinho> observableList;
    private double desconto;
    
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
    private TextArea txAreaInformativa;
    @FXML
    private Button botaoAdicionarProdutos;
    @FXML
    private TextField txCampoProduto;
    private Fachada fachada;
    @FXML
    private DatePicker data;
    @FXML
    private TextField txCampoDesconto;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label labelMsgCliente;
    @FXML
    private Label labelMsgDesconto;
    @FXML
    private Label labelMsgData;
    @FXML
    private Label labelMsgCarrinho;
    @FXML
    private Button botaoRemoverProduto;
    @FXML
    private Button botaoAplicarDesconto;
    @FXML
    private Label labelMsg;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
        data.setValue(LocalDate.now());
    }

    @FXML
    private void botaoSalvar(ActionEvent event) {
        Funcionario funcionario = funcionario = fachada.getLogado();
        Cliente cliente;
        try {
            cliente = fachada.getCliente(txCampoCliente.getText());
            adicionarVenda(cliente, funcionario);
            limparLabels();
            limparCampos();
            fachada.getCarrinhos().clear();
            listarProdutosAdicionados();
        } catch (ClienteInexistenteException error) {
            labelMsgCliente.setText(error.getMessage());
        } catch (NumberFormatException error) {
            labelMsgDesconto.setText("Desconto invalido");
        } catch (ProdutosInsuficientesException error) {
            labelMsgCarrinho.setText(error.getMessage());
        } catch (DataInvalidaException error) {
            labelMsgData.setText(error.getMessage());
        } catch (ProdutoInexistenteException ex) {
            labelMsg.setText(ex.getMessage());
        }

    }

    private void adicionarVenda(Cliente cliente, Funcionario funcionario) throws DataInvalidaException, ProdutosInsuficientesException {
        Venda venda = fachada.adicionarVenda(data.getValue(), cliente, funcionario, desconto, fachada.getCarrinhos());
        txAreaInformativa.setText("Valor Total da venda: \t" + new DecimalFormat(".00").format(venda.getPrecoSemDesconto())
                + "\nDesconto: " + venda.getDesconto()
                + "\nValor Com Desconto: " + new DecimalFormat(".00").format(venda.getPrecoTotal()));
    }

    @FXML
    private void botaoCancelar(ActionEvent event) throws IOException {
        fachada.getCarrinhos().clear();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void pesquisarProdutos(ActionEvent event) {
        try {
            Produto produto = fachada.buscarProdutoId(Integer.parseInt(txCampoProduto.getText()));
            labelMsg.setText("Produto " + produto.getNome() + " encontrado");
        } catch (ProdutoInexistenteException erro) {
            labelMsg.setText(erro.getMessage());
        } catch (NumberFormatException error) {
            labelMsg.setText("Produto não existe!");
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

    private void listarProdutosAdicionados() throws ProdutoInexistenteException {
        listProdutos.setItems(null);
        lista = lista = new ArrayList();
        txCampoProduto.clear();
        txCampoQuantidade.clear();
        limparLabels();
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
            System.out.println("Carrinho limpo");
        } catch (NumberFormatException error) {
            labelMsgDesconto.setText("Desconto invalido");
        }
    }

    @FXML
    private void adicionarProdutos(ActionEvent event) {
        int quantidade;
        labelMsg.setText("");
        try {
            quantidade = Integer.parseInt(txCampoQuantidade.getText());
            if (quantidade < 1) {
                throw new QuantidadeInsuficienteException();
            }
            Produto produto = fachada.buscarProdutoId(Integer.parseInt(txCampoProduto.getText()));
            limparLabels();
            labelMsg.setText("Produto adicionado");
            if (produto != null) {
                fachada.adicionarCarrinho(produto, quantidade);
            }
            listarProdutosAdicionados();
        } catch (ProdutoInexistenteException | QuantidadeInsuficienteException error) {
            labelMsg.setText(error.getMessage());
        } catch (NumberFormatException error) {
            labelMsg.setText("Digite apenas números");
        }
    }

    @FXML
    private void removerProduto(ActionEvent event
    ) {
        Carrinho carrinho;
        boolean existe = false;
        labelMsg.setText("");
        try {
            if (fachada.getCarrinhos().size() > 0) {
                for (int i = 0; i < fachada.getCarrinhos().size(); i++) {
                    if (fachada.getCarrinhos().get(i).getProduto().getId() == (Integer.parseInt(txCampoProduto.getText()))) {
                        carrinho = new Carrinho(fachada.getCarrinhos().get(i).getProduto(),
                                Integer.parseInt(txCampoQuantidade.getText()));
                        fachada.removerCarrinho(carrinho);
                        labelMsg.setText("Produto Removido");
                        listarProdutosAdicionados();
                    }
                }
                if (existe == true) {
                    throw new ProdutoInexistenteException();
                }

            } else {
                throw new ProdutosInsuficientesException();
            }
        } catch (ProdutosInsuficientesException |
                ProdutoInexistenteException |
                QuantidadeInsuficienteException error) {
            labelMsg.setText(error.getMessage());
        } catch (NumberFormatException error) {
            labelMsg.setText("Quantidade insuficiente");
        }
    }

    private void limparCampos() {
        txCampoCliente.clear();
        txCampoDesconto.clear();
        txCampoProduto.clear();
        txCampoQuantidade.clear();
        data.setValue(LocalDate.now());

    }

    private void limparLabels() {
        labelMsgDesconto.setText("");
        labelMsgCliente.setText("");
        labelMsgData.setText("");
        labelMsg.setText("");
        labelMsgCarrinho.setText("");
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
        String descontoTexto = txCampoDesconto.getText();
        if(descontoTexto.equals("")) {
            descontoTexto = "0";
        }
        desconto = Integer.parseInt(descontoTexto);
        double valor = calcularValorCarrinho();
        if (desconto < 0 || desconto > 15) {
                labelMsgDesconto.setText("Desconto invalido!");
                return;
        }
        valor -= (valor * desconto) / 100;
        txAreaInformativa.setText("Valor dos produtos com desconto: \t" + new DecimalFormat(".00").format(valor));
        labelMsgDesconto.setText("Desconto aplicado");
    }

    @FXML
    private void txCampoClienteOnKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txCampoCliente);
        tff.formatter();
    }

    @FXML
    private void txCampoProdutoOnKeyReleased(KeyEvent event) {
        if(!txCampoProduto.getText().matches("[0-9]+"))    txCampoProduto.setStyle("-fx-border-color: red;");
        else    txCampoProduto.setStyle("-fx-border-color: black;");
    }

    @FXML
    private void txCampoQuantidadeOnKeyReleased(KeyEvent event) {
        if(!txCampoQuantidade.getText().matches("[0-9]+"))    txCampoQuantidade.setStyle("-fx-border-color: red;");
        else    txCampoQuantidade.setStyle("-fx-border-color: black;");
    }

    @FXML
    private void txCampoDescontoOnKeyReleased(KeyEvent event) {
        if(!txCampoDesconto.getText().matches("[0-9]+"))    txCampoDesconto.setStyle("-fx-border-color: red;");
        else    txCampoDesconto.setStyle("-fx-border-color: black;");
    }

}
