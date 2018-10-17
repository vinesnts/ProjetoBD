/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import negocio.entidades.Produto;
import negocio.excecoes.ProdutoInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorAtualizaProduto implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txCampoNome;
    @FXML
    private TextField txCampoPreco;
    @FXML
    private TextField txCampoTamanho;
    @FXML
    private TextField txCampoId;
    @FXML
    private TextField txCampoMarca;
    @FXML
    private TextField txCampoCategoria;
    @FXML
    private Label labelNome1;
    @FXML
    private Label labelCPF;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelPreco;
    @FXML
    private Label labelMatricula;
    @FXML
    private Label labelId;
    @FXML
    private Button botaoAtualizar;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Button botaoPesquisar;
    @FXML
    private Label labelMsgId;
    @FXML
    private Label labelMsgErro;
    @FXML
    private Label labelMsg;
    Fachada fachada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fachada = Fachada.getInstance();
    }

    @FXML
    private void acaoAtualizarProduto(ActionEvent event) {
        try {
            limparLabels();
            if (txCampoNome.getText().equals("") || txCampoPreco.getText().equals("") || txCampoTamanho.getText().equals("")
                    || txCampoMarca.getText().equals("") || txCampoCategoria.getText().equals("")) {
                limparLabels();
                labelMsgErro.setText("Campo Inválido.");
            } else {
                limparLabels();
                Produto produto = fachada.buscarProdutoId(Integer.parseInt(txCampoId.getText()));
                if (produto.getId() == Integer.parseInt(txCampoId.getText())) {
                    produto.setNome(txCampoNome.getText());
                    produto.setPreco(Double.parseDouble(txCampoPreco.getText()));
                    produto.setTamanho(txCampoTamanho.getText());
                    produto.setMarca(txCampoMarca.getText());
                    produto.setCategoria(txCampoCategoria.getText());
                    fachada.atualizarProduto(produto);
                    limpaCampos();
                    labelMsg.setText("Produto Atualizado.");
                   
                }
            }

        } catch (ProdutoInexistenteException erro) {
            limpaCampos();
            limparLabels();
            labelMsgErro.setText(erro.getMessage());

        }
    }

    @FXML
    private void acaoPesquisarProduto(ActionEvent event) {
        try {
            limparLabels();
            if (txCampoId.getText().equals("")) {
                limparLabels();
                labelMsgId.setText("Campo Id Inválido.");
            } else {
                limparLabels();
                Produto produto = fachada.buscarProdutoId(Integer.parseInt(txCampoId.getText()));
                if (produto.getId() == Integer.parseInt(txCampoId.getText())) {
                    txCampoNome.setText(produto.getNome());
                    txCampoPreco.setText(String.valueOf(produto.getPreco()));
                    txCampoTamanho.setText(produto.getTamanho());
                    txCampoMarca.setText(produto.getMarca());
                    txCampoCategoria.setText(produto.getCategoria());

                }
            }

        } catch (ProdutoInexistenteException erro) {
            limpaCampos();
            labelMsgErro.setText(erro.getMessage());

        }
    }

    @FXML
    private void acaoCancelarProduto(ActionEvent event) {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void limpaCampos() {
        txCampoNome.clear();
        txCampoPreco.clear();
        txCampoCategoria.clear();
        txCampoTamanho.clear();
        txCampoMarca.clear();
        txCampoId.clear();
    }

    private void limparLabels() {
        labelMsgId.setText("");
        labelMsg.setText("");
        labelMsgErro.setText("");
    }
}
