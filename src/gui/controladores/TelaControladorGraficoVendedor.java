/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controladores;

import com.jfoenix.controls.JFXTextField;
import grafico.GraficoVendedor;
import fachada.Fachada;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import negocio.entidades.Funcionario;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorGraficoVendedor implements Initializable {

    @FXML
    private Button botaoCancelar;
    @FXML
    private AnchorPane anchorPaneGrafico;
    @FXML
    private Button botaoMostrarGrafico;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField txCampoCPF;
    @FXML
    private Label labelMsgErro;

    private Fachada fachada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
        txCampoCPF.setText(fachada.getLogado().getCpf());
    }

    @FXML
    private void acaoCancelarFuncionario(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    private void acaoMostrarGrafico(ActionEvent event) {
        try {
            Funcionario funcionario = fachada.getFuncionario(txCampoCPF.getText());
            if (funcionario.getCpf().equals(txCampoCPF.getText())) {
                GraficoVendedor graficoVendedor = new GraficoVendedor();
                FlowPane flow = new FlowPane(graficoVendedor.criarGraficoLinha(txCampoCPF.getText()));
                anchorPaneGrafico.getChildren().setAll(flow);
            }
        } catch (FuncionarioInexistenteException e) {
            labelMsgErro.setText(e.getMessage());
        }
    }

}
