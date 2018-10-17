/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import negocio.excecoes.ClienteExistenteException;

/**
 *
 * @author Raquell Vieira
 */
public class TelaControladorCadastroCliente {
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txCampoNome;
    @FXML
    private TextField txCampoCPF;
    @FXML
    private DatePicker caixaData;
    @FXML
    private Label labelCPF;
    @FXML
    private Label LabelNome;
    @FXML
    private Button botaoCadastrar;
    @FXML
    private Label labelMsgErro;
    @FXML
    private Label labelMsg;
    
    Fachada fachada = Fachada.getInstance();
    
    @FXML
    private void acaoCadastrarCliente(ActionEvent event) {
        try {
            limparLabels();
            LocalDate data = caixaData.getValue();
            if (txCampoNome.getText().equals("") || txCampoCPF.getText().equals("") || data == null) {
                limparLabels();
                labelMsgErro.setText("Campo Inválido.");
            } else {
                fachada.adicionarCliente(txCampoNome.getText(), txCampoCPF.getText(), data);
                limparCampos();
                limparLabels();
                labelMsg.setText("Cliente Cadastrado.");
            }
        } catch (ClienteExistenteException e) {
            limparCampos();
            limparLabels();
            labelMsgErro.setText(e.getMessage());
            
        }
    }
    @FXML
    private void acaoCancelarTelaCadastro(ActionEvent event) throws IOException {
//        limparCampos();
//        limparLabels();
//        VBox v = (VBox) FXMLLoader.load(getClass().getResource("/ui/TelaMenuGerente.fxml"));
//        anchorPane.setVisible(false);
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    private void caixaData(ActionEvent event) {
    }
    
    private void limparCampos() {
        txCampoNome.clear();
        txCampoCPF.clear();
        caixaData.setValue(null);
    }
    
    private void limparLabels() {
        labelMsg.setText("");
        labelMsgErro.setText("");
        
    }
    
}
