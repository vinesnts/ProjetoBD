/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import fachada.Fachada;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import negocio.entidades.Produto;
import negocio.excecoes.PrecoInvalidoException;
import negocio.excecoes.ProdutoExistenteException;
import negocio.excecoes.ProdutoInexistenteException;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class TelaControladorEstoque implements Initializable {

    private Fachada fachada;
    private List listaProduto;
    private ObservableList<Produto> listaObservavelProduto;
    private Produto produto;

    @FXML
    private ListView<Produto> listaEstoque;
    @FXML
    private TextField txtCampoNome;
    @FXML
    private TextField txtCampoPreco;
    @FXML
    private TextField txtCampoTamanho;
    @FXML
    private TextField txtCampoMarca;
    @FXML
    private TextField txtCampoCategoria;
    @FXML
    private Button botaoEditarEstoque;
    @FXML
    private Button botaoSalvarEstoque;
    @FXML
    private Label labelMsg;
    @FXML
    private Button botaoProcurarId;
    @FXML
    private TextField txtCampoId;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Button botaoCadastrarProduto;
    @FXML
    private Button botaoRemoverProduto;
    @FXML
    private GridPane cadastrarEditarGrid;
    @FXML
    private Button botaoCancelarEdicao;
    @FXML
    private GridPane menusGrid;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton botaoNao;
    @FXML
    private JFXButton botaoSim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
        listarEstoque();
    }

    private void listarEstoque() {
        ArrayList<Produto> estoque = fachada.getProdutos();
        listaProduto = new ArrayList<>();
        for (int i = 0; i < estoque.size(); i++) {
            listaProduto.add(estoque.get(i));
        }
        listaObservavelProduto = FXCollections.observableArrayList(listaProduto);
        listaEstoque.setItems(listaObservavelProduto);
    }

    @FXML
    private void botaoEditarEstoque(ActionEvent event) {
        try {
            produto = listaEstoque.getSelectionModel().selectedItemProperty().getValue();
            txtCampoId.setText(String.valueOf(produto.getId()));
            txtCampoNome.setText(produto.getNome());
            txtCampoPreco.setText(String.valueOf(produto.getPreco()));
            txtCampoTamanho.setText(produto.getTamanho());
            txtCampoMarca.setText(produto.getMarca());
            txtCampoCategoria.setText(produto.getCategoria());
            labelMsg.setText("");
            cadastrarEditarGrid.setVisible(true);
            menusGrid.setVisible(false);
            listaEstoque.setVisible(false);
        } catch (NullPointerException ex) {
            labelMsg.setText("Selecione algum item!");
        }
    }

    @FXML
    private void botaoSalvarEstoque(ActionEvent event) {
        if (txtCampoNome.getText().equals("") || txtCampoPreco.getText().equals("") || txtCampoTamanho.getText().equals("")
                || txtCampoMarca.getText().equals("") || txtCampoCategoria.getText().equals("")) {
            labelMsg.setText("Campo(s) vazio(s)");
        } else {
            try {
                if (produto == null) {
                    double preco = Double.parseDouble(txtCampoPreco.getText());
                    if (preco < 1.0) {
                        throw new PrecoInvalidoException();
                    }
                    fachada.adicionarProduto(txtCampoNome.getText(),
                            Double.parseDouble(txtCampoPreco.getText()),
                            txtCampoTamanho.getText(),
                            txtCampoMarca.getText(),
                            txtCampoCategoria.getText());
                    limparCampos();
                    labelMsg.setText("Produto cadastrado");
                } else {
                    produto.setNome(txtCampoNome.getText());
                    produto.setPreco(Double.parseDouble(txtCampoPreco.getText()));
                    produto.setTamanho(txtCampoTamanho.getText());
                    produto.setMarca(txtCampoMarca.getText());
                    produto.setCategoria(txtCampoCategoria.getText());
                    fachada.atualizarProduto(produto);
                    limparCampos();
                    labelMsg.setText("Produto atualizado");
                }
            } catch (ProdutoInexistenteException erro) {
                labelMsg.setText(erro.getMessage());
                limparCampos();
            } catch (PrecoInvalidoException | NumberFormatException e) {
                labelMsg.setText("Preco invalido");
                txtCampoPreco.clear();
            } catch (ProdutoExistenteException ex) {
                limparCampos();
                labelMsg.setText(ex.getMessage());
            }
        }
    }

    private void limparCampos() {
        txtCampoNome.clear();
        txtCampoCategoria.clear();
        txtCampoMarca.clear();
        txtCampoPreco.clear();
        txtCampoTamanho.clear();
        labelMsg.setText("");
        produto = null;
        cadastrarEditarGrid.setVisible(false);
        botaoRemoverProduto.setVisible(true);
        menusGrid.setVisible(true);
        listarEstoque();
        listaEstoque.refresh();
        listaEstoque.setVisible(true);
    }

    @FXML
    private void botaoProcurarId(ActionEvent event) {
        labelMsg.setText("");
        try {
            if (txtCampoId.getText().equals("")) {
                labelMsg.setText("Campo id inválido");
            } else {
                labelMsg.setText("");
                produto = fachada.buscarProdutoId(Integer.parseInt(txtCampoId.getText()));
                txtCampoNome.setText(produto.getNome());
                txtCampoPreco.setText(String.valueOf(produto.getPreco()));
                txtCampoTamanho.setText(produto.getTamanho());
                txtCampoMarca.setText(produto.getMarca());
                txtCampoCategoria.setText(produto.getCategoria());
                cadastrarEditarGrid.setVisible(true);
                menusGrid.setVisible(false);
                listaEstoque.setVisible(false);

            }

        } catch (ProdutoInexistenteException erro) {
            limparCampos();
            labelMsg.setText(erro.getMessage());

        }
    }

    @FXML
    private void botaoCancelar(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void botaoCadastrarProduto(ActionEvent event) {
        limparCampos();
        cadastrarEditarGrid.setVisible(true);
        botaoRemoverProduto.setVisible(false);
        menusGrid.setVisible(false);
        listaEstoque.setVisible(false);
    }

    @FXML
    private void botaoRemoverProduto(ActionEvent event) {
        if (txtCampoId.getText().equals("")) {
            labelMsg.setText("Nenhum item selecionado");
        } else {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new ImageView("gui/icons/pergunta.png"));
            content.setBody(new Label("Tem certeza que deseja remover o produto?\n"));
            JFXDialog dialogo = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            dialogo.setOverlayClose(false);
            dialogo.setFocusTraversable(true);
            stackPane.setVisible(true);
            botaoNao.setVisible(true);
            botaoSim.setVisible(true);
            botaoNao.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogo.close();
                    botaoNao.setVisible(false);
                    botaoSim.setVisible(false);
                    stackPane.setVisible(false);
                }
            });
            botaoSim.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        fachada.removerProduto(Integer.parseInt(txtCampoId.getText()));
                        txtCampoId.clear();
                        limparCampos();
                        labelMsg.setText("Produto removido");
                        dialogo.close();
                        botaoNao.setVisible(false);
                        botaoSim.setVisible(false);
                        stackPane.setVisible(false);
                    } catch (ProdutoInexistenteException erro) {
                        limparCampos();
                        txtCampoId.clear();
                        labelMsg.setText(erro.getMessage());
                        dialogo.close();
                        botaoNao.setVisible(false);
                        botaoSim.setVisible(false);
                        stackPane.setVisible(false);
                    }
                }
            });
            content.setActions(botaoNao, botaoSim);
            dialogo.show();
        }
    }

    @FXML
    private void botaoCancelarEdicao(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void txtCampoIdOnKeyReleased(KeyEvent event) {
        if (!txtCampoId.getText().matches("[0-9]+")) {
            txtCampoId.setStyle("-fx-border-color: red;");
        } else {
            txtCampoId.setStyle("-fx-border-color: black;");
        }
    }
}
