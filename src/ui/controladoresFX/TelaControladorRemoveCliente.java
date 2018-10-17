/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import negocio.excecoes.ClienteInexistenteException;

/**
 *
 * @author Raquell Vieira
 */
public class TelaControladorRemoveCliente {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txCampoCPF;
    @FXML
    private Label labelCPF;
    @FXML
    private Button botaoRemover;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Label labelMsgErro;
    @FXML
    private Label labelMsg;
    
    Fachada fachada = Fachada.getInstance();
    
    @FXML
	private void acaoRemoverCliente(ActionEvent event) {
		if (txCampoCPF.getText().equals("")) {
			labelMsgErro.setText("Campo CPF Inválido.");
		} else {
			boolean confirmacao = new GUIConfirmation().janelaConfirmacao(
					"Tem certeza que deseja remover o cliente?",
					"Todas as informacoes dele serao perdidas");
			if (confirmacao) {
				try {
					limparLabels();
					fachada.getCliente(txCampoCPF.getText());
					fachada.removerCliente(txCampoCPF.getText());
					txCampoCPF.clear();
					limparLabels();
					labelMsg.setText("Cliente Removido.");
				} catch (ClienteInexistenteException e) {
					txCampoCPF.clear();
					limparLabels();
					labelMsgErro.setText(e.getMessage());
				}
			} else {
				// nada a fazer
			}
		}
	}

    @FXML
    private void acaoCancelarCliente(ActionEvent event) {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void limparLabels() {
        labelMsgErro.setText("");
        labelMsg.setText("");
    }
}
