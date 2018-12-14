package gui.controladores;

import fachada.Fachada;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import negocio.entidades.Pacote;
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

    private Fachada fachada;
    private ArrayList<Pacote> pacotes = new ArrayList();
    private ObservableList<Pacote> observableList;
    private double desconto;

    @FXML
    private Button botaoSalvar;
    @FXML
    private Button botaoCancelar;
    @FXML
    private ListView<Pacote> listProdutos;
    @FXML
    private TextField txCampoCliente;
    @FXML
    private Button botaoPesquisarProdutos;
    @FXML
    private Button botaoPesquisarCliente;
    @FXML
    private TextField txCampoQuantidade;
    @FXML
    private TextArea txAreaInformativa;
    @FXML
    private Button botaoAdicionarProdutos;
    @FXML
    private TextField txCampoProduto;
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
    private Label labelMsgPacote;
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
        pacotes = new ArrayList();
        data.setValue(LocalDate.now());
    }

    @FXML
    private void botaoSalvar(ActionEvent event) {
        Cliente cliente;
        try {
            cliente = fachada.getCliente(txCampoCliente.getText());
            adicionarVenda(cliente, fachada.getLogado());
            limparLabels();
            limparCampos();
            fachada.adicionarPacotes(pacotes);
            pacotes.clear();
            listarProdutosAdicionados();
        } catch (ClienteInexistenteException error) {
            labelMsgCliente.setText(error.getMessage());
        } catch (NumberFormatException error) {
            labelMsgDesconto.setText("Desconto invalido");
        } catch (ProdutosInsuficientesException error) {
            labelMsgPacote.setText(error.getMessage());
        } catch (DataInvalidaException error) {
            labelMsgData.setText(error.getMessage());
        } catch (ProdutoInexistenteException ex) {
            labelMsg.setText(ex.getMessage());
        }

    }

    private void adicionarVenda(Cliente cliente, Funcionario funcionario) throws DataInvalidaException, ProdutosInsuficientesException {
        Venda venda = fachada.adicionarVenda(data.getValue(), cliente, funcionario, desconto, pacotes);
        txAreaInformativa.setText("Valor Total da venda: \t" + new DecimalFormat(".00").format(venda.getPrecoSemDesconto())
                + "\nDesconto: " + venda.getDesconto()
                + "\nValor Com Desconto: " + new DecimalFormat(".00").format(venda.getPrecoTotal()));
    }

    @FXML
    private void botaoCancelar(ActionEvent event) throws IOException {
        pacotes.clear();
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
    private void pesquisarCliente(ActionEvent event) {
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
        txCampoProduto.clear();
        txCampoQuantidade.clear();
        limparLabels();
        int x = 0;
        try {
            txAreaInformativa.setText("Valor dos produtos ja adicionados\t" + calcularValorPacote());
            observableList = FXCollections.observableArrayList(pacotes);
            listProdutos.setItems(observableList);

        } catch (NumberFormatException error) {
            labelMsgDesconto.setText("Desconto invalido");
        }
    }

    @FXML
    private void adicionarProdutos(ActionEvent event) {
        Pacote pacote;
        Boolean existe = false;
        labelMsg.setText("");
        try {
            pacote = gerarPacote();
            limparLabels();
            for (int i = 0; i < pacotes.size(); i++) {
                if (pacotes.get(i).getProduto().getId() == pacote.getProduto().getId()) {
                    pacotes.get(i).setQuantidade(pacotes.get(i).getQuantidade() + pacote.getQuantidade());
                    existe = true;
                }
            }
            if (!existe) {
                pacotes.add(pacote);
            }
            listarProdutosAdicionados();
            labelMsg.setText("Produto adicionado");
        } catch (ProdutoInexistenteException | QuantidadeInsuficienteException error) {
            labelMsg.setText(error.getMessage());
        } catch (NumberFormatException error) {
            labelMsg.setText("Digite apenas números");
        }
    }

    private Pacote gerarPacote() throws QuantidadeInsuficienteException, NumberFormatException, ProdutoInexistenteException {
        Produto produto = fachada.buscarProdutoId(Integer.parseInt(txCampoProduto.getText()));
        if (produto == null) {
            throw new ProdutoInexistenteException();
        }

        int quantidade = verificarQuantidade();
        Pacote pacote = new Pacote(produto, quantidade);
        return pacote;
    }

    private int verificarQuantidade() throws QuantidadeInsuficienteException, NumberFormatException {
        int quantidade;
        quantidade = Integer.parseInt(txCampoQuantidade.getText());
        if (quantidade < 1) {
            throw new QuantidadeInsuficienteException();
        }
        return quantidade;
    }

    @FXML
    private void removerProduto(ActionEvent event) {
        Pacote pacote;
        boolean removeu = false;
        labelMsg.setText("");
        try {
            if (pacotes.size() > 0) {
                for (int i = 0; i < pacotes.size(); i++) {
                    if (pacotes.get(i).getProduto().getId() == (Integer.parseInt(txCampoProduto.getText()))) {
                        pacote = new Pacote(pacotes.get(i).getProduto(),
                                Integer.parseInt(txCampoQuantidade.getText()));
                        removeu = removerPacote(pacote);
                        labelMsg.setText("Produto removido");
                        listarProdutosAdicionados();
                    }
                }
                
                if (!removeu) throw new ProdutoInexistenteException();
                
            } else {
                throw new ProdutosInsuficientesException("Nao ha produtos no carrinho");
            }
        } catch (ProdutosInsuficientesException
                | ProdutoInexistenteException error) {
            labelMsg.setText(error.getMessage());
        } catch (NumberFormatException error) {
            labelMsg.setText("Quantidade insuficiente");
        }
    }

    private boolean removerPacote(Pacote pacote) {
        Boolean removeu = false;
        for (int j = 0; j < pacotes.size(); j++) {
            if (pacotes.get(j).getProduto().getId() == pacote.getProduto().getId()) {
                pacotes.get(j).setQuantidade(pacotes.get(j).getQuantidade() - pacote.getQuantidade());
                removeu = true;
            }
        }
        return removeu;
    }

    public double calcularValorPacote() {
        double valor = 0;
        if (pacotes != null) {
            for (int i = 0; i < pacotes.size(); i++) {
                valor += pacotes.get(i).getProduto().getPreco() * pacotes.get(i).getQuantidade();

            }
        }
        return valor;
    }

    @FXML
    private void botaoAplicarDesconto(ActionEvent event) {
        String descontoTexto = txCampoDesconto.getText();
        if (descontoTexto.equals("")) {
            descontoTexto = "0";
        }
        desconto = Integer.parseInt(descontoTexto);
        double valor = calcularValorPacote();
        if (desconto < 0 || desconto > 15) {
            labelMsgDesconto.setText("Desconto invalido!");
            return;
        }
        valor -= (valor * desconto) / 100;
        txAreaInformativa.setText("Valor dos produtos com desconto: \t" + new DecimalFormat(".00").format(valor));
        labelMsgDesconto.setText("Desconto aplicado");
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
        labelMsgPacote.setText("");
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
        if (!txCampoProduto.getText().matches("[0-9]+")) {
            txCampoProduto.setStyle("-fx-border-color: red;");
        } else {
            txCampoProduto.setStyle("-fx-border-color: black;");
        }
    }

    @FXML
    private void txCampoQuantidadeOnKeyReleased(KeyEvent event) {
        if (!txCampoQuantidade.getText().matches("[0-9]+")) {
            txCampoQuantidade.setStyle("-fx-border-color: red;");
        } else {
            txCampoQuantidade.setStyle("-fx-border-color: black;");
        }
    }

    @FXML
    private void txCampoDescontoOnKeyReleased(KeyEvent event) {
        if (!txCampoDesconto.getText().matches("[0-9]+")) {
            txCampoDesconto.setStyle("-fx-border-color: red;");
        } else {
            txCampoDesconto.setStyle("-fx-border-color: black;");
        }
    }

}
