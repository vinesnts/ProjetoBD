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
import negocio.excecoes.ProdutoInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorRemoveProduto implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button botaoRemover;
    @FXML
    private Button botaocancelar;
    @FXML
    private Label labelId;
    @FXML
    private TextField txCampoId;
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
	private void acaoRemoverProduto(ActionEvent event) {

		limparLabels();
		if (txCampoId.getText().equals("")) {
			labelMsgErro.setText("Campo Id Inválido.");
		} else {
			boolean confirmacao = new GUIConfirmation().janelaConfirmacao(
					"Tem certeza que deseja remover o produto?",
					"Todas as informacoes dele serao perdidas");
			if (confirmacao) {
				try {
					limparLabels();
					fachada.buscarProdutoId(Integer.parseInt(txCampoId.getText()));
					fachada.removerProduto(Integer.parseInt(txCampoId.getText()));
					txCampoId.clear();
					labelMsg.setText("Produto Removido.");
				} catch (ProdutoInexistenteException erro) {
					limparLabels();
					txCampoId.clear();
					labelMsgErro.setText(erro.getMessage());

				}
			} else {
				// nada a fazer
			}
		}

	}

    @FXML
    private void acaoCancelarProduto(ActionEvent event) {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void limparLabels() {
        labelMsgErro.setText("");
        labelMsg.setText("");
    }

}
