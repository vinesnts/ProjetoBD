/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controladores;

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
import javafx.scene.control.TextField;
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
    private Label labelMatricula;
    @FXML
    private TextField txCampoMatricula;
    @FXML
    private AnchorPane anchorPaneGrafico;
    @FXML
    private Label labelMsgErro;
    @FXML
    private Button botaoMostrarGrafico;

    private Fachada fachada;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fachada = Fachada.getInstance();
    	txCampoMatricula.setText(fachada.getLogado().getMatricula());
    }
    // s.setScene(new Scene(new FlowPane(criarGraficoLinha())));

    @FXML
    private void acaoCancelarFuncionario(ActionEvent event) throws IOException {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    private void acaoMostrarGrafico(ActionEvent event) {
        try {
            if (txCampoMatricula.getText().equals("")) {
               // txCampoMatricula.clear();
                //labelMsgErro.setText("Campo Inválido.");
            } else {
                Funcionario funcionario = fachada.getFuncionario(txCampoMatricula.getText());
                if (funcionario.getMatricula().equals(txCampoMatricula.getText())) {
                    GraficoVendedor graficoVendedor = new GraficoVendedor();
                    graficoVendedor.setMatricula(txCampoMatricula.getText());
                    FlowPane flow = new FlowPane(graficoVendedor.criarGraficoLinha());
                    anchorPaneGrafico.getChildren().setAll(flow);

                }
            }
        } catch (FuncionarioInexistenteException e) {
            labelMsgErro.setText(e.getMessage());
        }
    }

}
