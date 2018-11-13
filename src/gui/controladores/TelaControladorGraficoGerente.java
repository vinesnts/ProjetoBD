/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controladores;

import grafico.GraficoGerente;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import negocio.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorGraficoGerente implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label labelDataInicio;
    @FXML
    private Button botaoMostrarGrafico;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Label labelMsgErro;
    @FXML
    private AnchorPane anchorPaneGrafico;
    @FXML
    private TextField txCampoAno;

    Fachada fachada;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
    }

    @FXML
    private void acaoMostrarGrafico(ActionEvent event) {
        try {

            int ano = Integer.parseInt(txCampoAno.getText());
            GraficoGerente graficoGerente = new GraficoGerente();
            graficoGerente.setAno(ano);
            labelMsgErro.setText("");
            FlowPane flow = new FlowPane(graficoGerente.criarGraficoPizza());
            anchorPaneGrafico.getChildren().setAll(flow);

        } catch (NumberFormatException error) {
            labelMsgErro.setText("Entrada invalida.");
        }

    }

    @FXML
    private void acaoCancelarTelaMenu(ActionEvent event) throws IOException {
//        anchorPane.setVisible(false);
        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    private void txCampoAnoOnKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txCampoAno);
        tff.formatter();
    }

}
