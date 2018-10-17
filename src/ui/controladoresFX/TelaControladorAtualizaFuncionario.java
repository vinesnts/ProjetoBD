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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import negocio.entidades.Funcionario;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorAtualizaFuncionario implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txCampoMatricula;
    @FXML
    private TextField txCampoCpf;
    @FXML
    private TextField txCampoNome;
    @FXML
    private PasswordField txCampoSenha;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelMatricula;
    @FXML
    private Label labelSenha;
    @FXML
    private Label labelCPF;
    @FXML
    private Label labelNome;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Button botaoAtualizar;
    @FXML
    private RadioButton radionVendedor;
    @FXML
    private RadioButton radionGerente;
    @FXML
    private Button botaoPesquisarMatricula;
    @FXML
    private Label labelMsgMatricula;
    @FXML
    private Label labelMsg;
    @FXML
    private Label labelMsgErro;

    private String tipo = "";
    Fachada fachada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	 fachada = Fachada.getInstance();
    }

    @FXML
    private void radionVendedor(ActionEvent event) {
        this.tipo = "Vendedor";
    }

    @FXML
    private void radionGerente(ActionEvent event) {
        this.tipo = "Gerente";
    }

    @FXML
    private void acaoAtualizarFuncionario(ActionEvent event) {
        try {
            if (txCampoCpf.getText().equals("") || txCampoNome.getText().equals("") || txCampoSenha.getText().equals("") || tipo.equals("")) {
                limparLabels();
                labelMsgErro.setText("Campo Inválido.");
            } else {
                limparLabels();
                Funcionario funcionario = fachada.getFuncionario(txCampoMatricula.getText());
                if (funcionario.getMatricula().equals(txCampoMatricula.getText())) {
                    funcionario.setNome(txCampoNome.getText());
                    funcionario.setCpf(txCampoCpf.getText());
                    funcionario.setSenha(txCampoSenha.getText());
                    funcionario.setTipo(tipo);
                    fachada.atualizarFuncionario(funcionario);
                    limparCampos();
                    limparLabels();
                    labelMsg.setText("Funcionario Atualizado.");
                }
            }
        } catch (FuncionarioInexistenteException e) {
            limparCampos();
            limparLabels();
            labelMsgErro.setText(e.getMessage());
        }
    }

    @FXML
    private void acaoPesquisarFuncionario(ActionEvent event) {
        try {
            limparLabels();
            if (txCampoMatricula.getText().equals("")) {
                limparLabels();
                labelMsgMatricula.setText("Campo Matrícula Inválido.");
            } else {
                limparLabels();
                Funcionario funcionario = fachada.getFuncionario(txCampoMatricula.getText());
                if (funcionario.getMatricula().equals(txCampoMatricula.getText())) {
                    txCampoNome.setText(funcionario.getNome());
                    txCampoCpf.setText(funcionario.getCpf());
                    txCampoSenha.setText(txCampoSenha.getText());
                }
            }
        } catch (FuncionarioInexistenteException e) {
            limparCampos();
            labelMsgMatricula.setText(e.getMessage());

        }
    }

    @FXML
    private void acaoCancelarFuncionario(ActionEvent event) {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void limparCampos() {
        txCampoNome.clear();
        txCampoCpf.clear();
        txCampoMatricula.clear();
        txCampoSenha.clear();
        tipo = "";

    }

    private void limparLabels() {
        labelMsgMatricula.setText("");
        labelMsg.setText("");
        labelMsgErro.setText("");
    }

}
