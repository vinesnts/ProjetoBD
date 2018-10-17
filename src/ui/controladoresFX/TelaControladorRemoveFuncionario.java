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
import javafx.scene.layout.HBox;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorRemoveFuncionario implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txCampoMatricula;
    @FXML
    private Label labelMatricula;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Button botaoRemover;
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
    private void acaoCancelarFuncionario(ActionEvent event) {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
	private void acaoRemoverFuncionario(ActionEvent event) {

		limparLabels();
		if (txCampoMatricula.getText().equals("")) {
			limparLabels();
			labelMsgErro.setText("Campo Matricula InvÃ¡lido.");

		} else {
			boolean confirmacao = new GUIConfirmation().janelaConfirmacao(
					"Tem certeza que deseja remover o funcionário?",
					"Todas as informações dele serão perdidas");
			if (confirmacao) {
				try {
					fachada.getFuncionario(txCampoMatricula.getText());
					fachada.removerFuncionario(txCampoMatricula.getText());
					txCampoMatricula.clear();
					limparLabels();
					labelMsg.setText("Funcionario Removido.");
				} catch (FuncionarioInexistenteException error) {
					txCampoMatricula.clear();
					limparLabels();
					labelMsgErro.setText(error.getMessage());

				}
			} else {
				// nada a fazer
			}
		}

	}

    private void limparLabels() {
        labelMsg.setText("");
        labelMsgErro.setText("");
    }

}
