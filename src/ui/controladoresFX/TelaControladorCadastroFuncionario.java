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
import negocio.excecoes.FuncionarioExistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class TelaControladorCadastroFuncionario implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txCampoNome;
    @FXML
    private TextField txCampoCpf;
    @FXML
    private PasswordField txCampoSenha;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelCPF;
    @FXML
    private Label labelSenha;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelMatricula;
    @FXML
    private RadioButton radionGerente;
    @FXML
    private RadioButton radionVendedor;
    @FXML
    private Button botaoCadastrar;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Label labelMsg;
    @FXML
    private Label labelMsgErro;

    private String tipoFuncionario;
    private Fachada fachada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fachada = Fachada.getInstance();
        tipoFuncionario = "";
    }

    @FXML
    private void radionGerente(ActionEvent event) {
        this.tipoFuncionario = "Gerente";

    }

    @FXML
    private void radionVendedor(ActionEvent event) {
        this.radionGerente.arm();
        this.tipoFuncionario = "Vendedor";
    }

    @FXML
    private void acaoCadastrarFuncionario(ActionEvent event) {
        try {
            limparLabels();
            if (txCampoNome.getText().equals("") ||
                    txCampoCpf.getText().equals("") ||
                    txCampoSenha.getText().equals("") ||
                    tipoFuncionario.equals("")) {
                limparLabels();
                labelMsgErro.setText("Campo Inválido.");
            } else {
                String matricula = gerarMatricula(tipoFuncionario, txCampoCpf.getText());
                fachada.adicionarFuncionario(txCampoNome.getText(),
                        txCampoCpf.getText(),
                        tipoFuncionario,
                        matricula,
                        txCampoSenha.getText());
                limparCampos();
                limparLabels();
                labelMsg.setText("Cadastrado com ID: " + matricula);
            }
        } catch (FuncionarioExistenteException e) {
            limparCampos();
            limparLabels();
            labelMsgErro.setText(e.getMessage());
        }
    }
    
    private static String gerarMatricula(String tipoFuncionario, String cpf) {
        if(tipoFuncionario.equals("Gerente")) return "G" + cpf;
        else if(tipoFuncionario.equals("Vendedor")) return "V" + cpf;
        return null;
    }

    @FXML
    private void acaoCancelarFuncionario(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void limparCampos() {
        txCampoNome.clear();
        txCampoCpf.clear();
        txCampoSenha.clear();
        tipoFuncionario = "";

    }

    private void limparLabels() {
        labelMsg.setText("");
        labelMsgErro.setText("");
    }
}