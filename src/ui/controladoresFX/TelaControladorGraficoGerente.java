/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

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
    private DatePicker campoDataInicio;
    private DatePicker CampoDataFim;
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
            FlowPane flow = new FlowPane(graficoGerente.criarGraficoPizza());
            anchorPaneGrafico.getChildren().setAll(flow);
        } catch (NumberFormatException error) {
            labelMsgErro.setText("Entrada invalida.");
        }

    }

    private void periodoGrafico() {
        int mesInicio = campoDataInicio.getValue().getMonthValue();
        int anoInicio = campoDataInicio.getValue().getYear();
        int mesFim = CampoDataFim.getValue().getMonthValue();
        int anoFim = CampoDataFim.getValue().getYear();
        fachada.getVendas();
        for (int i = 0; i < fachada.getVendas().size(); i++) {
            if (fachada.getVendas().get(i).getData().getMonthValue() >= mesInicio && fachada.getVendas().get(i).getData().getYear() == anoInicio) {
                System.out.println(fachada.getVendas().get(i).getData());
            }
        }

    }

    @FXML
    private void acaoCancelarTelaMenu(ActionEvent event) throws IOException {
//        anchorPane.setVisible(false);
    	((Node) event.getSource()).getScene().getWindow().hide();

    }

}
