
package gui.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import grafico.GraficoGerente;
import fachada.Fachada;
import grafico.GraficoVendedor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import negocio.TextFieldFormatter;
import negocio.entidades.Funcionario;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorGraficoGerente implements Initializable {

    Fachada fachada;
    
    @FXML
    private Label labelMsgErro;
    @FXML
    private TextField txCampoAno;
    @FXML
    private Tab totaVendasAnoTab;
    @FXML
    private Tab meuTotalVendasTab;
    @FXML
    private JFXTextField txCampoCPF;
    @FXML
    private JFXButton botaoMostrarGrafico1;
    @FXML
    private Label labelMsgErro1;
    @FXML
    private JFXButton botaoTotalVendasAno;
    @FXML
    private AnchorPane anchorPaneTotalAno;
    @FXML
    private AnchorPane anchorPaneMeuTotal;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
        txCampoCPF.setText(fachada.getLogado().getCpf());
    }

    @FXML
    private void acaoTotalVendasAno(ActionEvent event) {
        try {

            int ano = Integer.parseInt(txCampoAno.getText());
            GraficoGerente graficoGerente = new GraficoGerente();
            graficoGerente.setAno(ano);
            labelMsgErro.setText("");
            FlowPane flow = new FlowPane(graficoGerente.criarGraficoPizza());
            anchorPaneTotalAno.getChildren().setAll(flow);

        } catch (NumberFormatException error) {
            labelMsgErro.setText("Entrada invalida.");
        }
    }
    
    @FXML
    private void acaoMeuTotalVendas(ActionEvent event) {
        try {
            Funcionario funcionario = fachada.getFuncionario(txCampoCPF.getText());
            if (funcionario.getCpf().equals(txCampoCPF.getText())) {
                GraficoVendedor graficoVendedor = new GraficoVendedor();
                FlowPane flow = new FlowPane(graficoVendedor.criarGraficoLinha(txCampoCPF.getText()));
                anchorPaneMeuTotal.getChildren().setAll(flow);
            }
        } catch (FuncionarioInexistenteException e) {
            labelMsgErro.setText(e.getMessage());
        }
    }

    @FXML
    private void acaoCancelarTelaMenu(ActionEvent event) throws IOException {
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
