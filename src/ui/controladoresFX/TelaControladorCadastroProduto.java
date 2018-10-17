/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import negocio.excecoes.PrecoInvalidoException;
import negocio.excecoes.ProdutoExistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorCadastroProduto implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField txCampoTamanho;
    @FXML
    private TextField txCampoPreco;
    @FXML
    private TextField txCampoNome;
    @FXML
    private TextField txCampoCategoria;
    @FXML
    private TextField txCampoMarca;
    @FXML
    private Label labelMatricula;
    @FXML
    private Label labelPreco;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelCPF;
    @FXML
    private Label labelMarca;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Button botaoCadastrar;
    @FXML
    private Label labelMsg;
    @FXML
    private Label labelMsgErro;
    
    Fachada fachada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fachada = Fachada.getInstance();
    }
    
    @FXML
    private void acaoCadastrarProduto(ActionEvent event) {
        try {
            limparLabels();
            if (txCampoNome.getText().equals("") || txCampoPreco.getText().equals("") || txCampoTamanho.getText().equals("")
                    || txCampoMarca.getText().equals("") || txCampoCategoria.getText().equals("")) {
                limparLabels();
                labelMsgErro.setText("Campo Inválido.");
            } else {
                double preco = Double.parseDouble(txCampoPreco.getText());
                if (preco < 1.0) {
                    throw new PrecoInvalidoException();
                }
                fachada.adicionarProduto(txCampoNome.getText(), Double.parseDouble(txCampoPreco.getText()), txCampoTamanho.getText(),
                        txCampoMarca.getText(), txCampoCategoria.getText());
                limpaCampos();
                limparLabels();
                labelMsg.setText("Produto Cadastrado.");
            }
        } catch (ProdutoExistenteException ex) {
            limpaCampos();
            limparLabels();
            labelMsgErro.setText(ex.getMessage());
        } catch (PrecoInvalidoException | NumberFormatException ex) {
            labelMsgErro.setText(ex.getMessage());
        }
        
    }
    
    @FXML
    private void acaoCancelarProduto(ActionEvent event) {
//        limparLabels();
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    private void limpaCampos() {
        txCampoNome.clear();
        txCampoPreco.clear();
        txCampoCategoria.clear();
        txCampoTamanho.clear();
        txCampoMarca.clear();
    }
    
    private void limparLabels() {
        labelMsg.setText("");
        labelMsgErro.setText("");
        
    }
}
