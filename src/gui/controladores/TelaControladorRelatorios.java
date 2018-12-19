
package gui.controladores;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import fachada.Fachada;
import grafico.GerarRelatorioPDF;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import negocio.entidades.Pacote;
import negocio.entidades.Venda;

/**
 * FXML Controller class
 *
 * @author vinesnts
 */
public class TelaControladorRelatorios implements Initializable {

    private Fachada fachada;
    private List listaTotalVendas;
    private List listaProdutosVendidos;
    private ObservableList<Venda> listaObservavelTotalVendas;
    private ObservableList<Pacote> listaObservavelProdutosVendidos;
    
    @FXML
    private Tab totalVendas;
    @FXML
    private ListView<Venda> totalVendasListView;
    @FXML
    private Tab produtosVendidosTab;
    @FXML
    private ListView<Pacote> produtosVendidosListView;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton botaoOkay;
    @FXML
    private JFXButton botaoNao;
    @FXML
    private JFXButton botaoSim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
        listarTotalVendas();
        listarProdutosVendidos();
        
    }    
    
    private void listarTotalVendas() {
        ArrayList<Venda> lista = fachada.getVendas();
        listaTotalVendas = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            listaTotalVendas.add(lista.get(i));
        }
        listaObservavelTotalVendas = FXCollections.observableArrayList(listaTotalVendas);
        totalVendasListView.setItems(listaObservavelTotalVendas);
    }
    
    private void listarProdutosVendidos() {
        ArrayList<Pacote> lista = fachada.getQuantidadeVendasProdutos();
        listaProdutosVendidos = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            listaProdutosVendidos.add(lista.get(i));
        }
        listaObservavelProdutosVendidos = FXCollections.observableArrayList(listaProdutosVendidos);
        produtosVendidosListView.setItems(listaObservavelProdutosVendidos);
    }

    @FXML
    private void gerarPDFAction(ActionEvent event) {
        GerarRelatorioPDF gr = new GerarRelatorioPDF();
        try {
            gr.criarRelatorio();
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new ImageView("gui/icons/okay.png"));
            content.setBody(new Label("Relatório gerado com sucesso"));
            JFXDialog dialogo = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            dialogo.setOverlayClose(false);
            dialogo.setFocusTraversable(true);
            stackPane.setVisible(true);
            botaoOkay.setVisible(true);
            botaoOkay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogo.close();
                    botaoOkay.setVisible(false);
                    stackPane.setVisible(false);
                }
            });
            content.setActions(botaoOkay);
            dialogo.show();
        } catch (DocumentException | FileNotFoundException ex) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new ImageView("gui/icons/erro.png"));
            content.setBody(new Label("Erro ao gerar relatorio de vendas"));
            JFXDialog dialogo = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            dialogo.setOverlayClose(false);
            dialogo.setFocusTraversable(true);
            stackPane.setVisible(true);
            botaoOkay.setVisible(true);
            botaoOkay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogo.close();
                    botaoOkay.setVisible(false);
                    stackPane.setVisible(false);
                }
            });
            content.setActions(botaoOkay);
            dialogo.show();
        }        
    }

    @FXML
    private void botaoCancelar(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}
