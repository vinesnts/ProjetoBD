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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import negocio.entidades.Funcionario;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorLogin implements Initializable {

    @FXML
    private Button botaoEntrar;
    @FXML
    private Label rotuloMatricula;
    @FXML
    private Label rotuloSenha;
    @FXML
    private TextField txCampoMatricula;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField txCampoSenha;
    @FXML
    private Label labelMsg;

    private Fachada fachada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
    }

    @FXML
    private void acaoBotaoEntrar(ActionEvent event) throws IOException {

        try {
            Funcionario funcionario = fachada.getFuncionario(txCampoMatricula.getText());
            if (funcionario.getMatricula().equals(txCampoMatricula.getText()) &&
                    funcionario.getSenha().equals(txCampoSenha.getText())) {
                fachada.setLogado(funcionario);
                System.out.println(fachada.getLogado().getNome());
            	limparCampo();
                if (funcionario.getTipo().equals("Gerente")) {
                    VBox v = (VBox) FXMLLoader.load(getClass().getResource("/ui/TelaMenuGerente.fxml"));
                    anchorPane.getChildren().setAll(v);
                } else {
                    VBox v = (VBox) FXMLLoader.load(getClass().getResource("/ui/TelaMenuVendedor.fxml"));
                    anchorPane.getChildren().setAll(v);
                }

            } else {
                limparCampo();
                labelMsg.setText("Senha Inválida.");
            }

        } catch (FuncionarioInexistenteException erro) {
            limparCampo();
            labelMsg.setText("Matricula Inexistente.");
        }

    }

    private void limparCampo() {
        txCampoMatricula.clear();
        txCampoSenha.clear();
        labelMsg.setText("");
    }

}
