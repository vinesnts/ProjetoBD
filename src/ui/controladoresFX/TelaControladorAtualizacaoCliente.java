/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import negocio.entidades.Cliente;
import negocio.excecoes.ClienteInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorAtualizacaoCliente implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txCampoCPF;
    @FXML
    private TextField txCampoNome;
    @FXML
    private DatePicker caixaData;
    @FXML
    private Button botaoAtualizar;
    @FXML
    private Button botaoPesquisar;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Label labelCPF;
    @FXML
    private Label LabelNome;
    @FXML
    private Label labelMsgCpf;
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
    private void acaoAtualizarCliente(ActionEvent event) {
        try {
            LocalDate data = caixaData.getValue();
            if (txCampoNome.getText().equals("") || txCampoCPF.getText().equals("") || data == null) {
                limparLabels();
                labelMsgErro.setText("Campo Inválido");
            } else {
                
                Cliente cliente = fachada.getCliente(txCampoCPF.getText());
                if (cliente.getCpf().equals(txCampoCPF.getText())) {
                    cliente.setNome(txCampoNome.getText());
                    cliente.setDataAniversario(caixaData.getValue());
                    fachada.atualizarCliente(cliente);
                    limparCampos();
                    limparLabels();
                    labelMsg.setText("Cliente Atualizado.");
                }
            }

        } catch (ClienteInexistenteException erro) {
            limparCampos();
            limparLabels();
            labelMsgCpf.setText(erro.getMessage());
        }
    }

    @FXML
    private void acaoBotaoPesquisarCliente(ActionEvent event) {
        try {
            limparLabels();
            if (txCampoCPF.getText().equals("")) {
                limparLabels();
                labelMsgCpf.setText("Campo CPF Inválido.");
            } else {
                limparLabels();
                Cliente cliente = fachada.getCliente(txCampoCPF.getText());
                if (cliente.getCpf().equals(txCampoCPF.getText())) {
                    txCampoNome.setText(cliente.getNome());
                    caixaData.setValue(cliente.getDataAniversario());
                }
            }
        } catch (ClienteInexistenteException erro) {
            limparCampos();
            limparLabels();
            labelMsgCpf.setText(erro.getMessage());
        }
    }

    @FXML
    private void caixaData(ActionEvent event) {
    }

    @FXML
    private void acaoBotaoCancelarCliente(ActionEvent event) {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void limparCampos() {
        txCampoNome.clear();
        txCampoCPF.clear();
        caixaData.setValue(null);
    }

    private void limparLabels() {
        labelMsgCpf.setText("");
        labelMsg.setText("");
        labelMsgErro.setText("");
    }

}
