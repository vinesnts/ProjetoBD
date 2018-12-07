/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import fachada.Fachada;
import grafico.GerarRelatorioPDF;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorMenuPrincipal implements Initializable {

    private Fachada fachada;

    private Label gerenteAvisos;
    @FXML
    private TextField campoFuncionario;
    @FXML
    private JFXButton bListarClientes;
    @FXML
    private GridPane gridPane;
    @FXML
    private JFXButton bListarFuncionarios;
    @FXML
    private JFXButton bTelaEstoque;
    @FXML
    private JFXButton bVenda;
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = fachada.getInstance();
        campoFuncionario.setText(fachada.getLogado().getNome());
    }

    @FXML
    private void redirecionaSaidaLogin(ActionEvent event) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new ImageView("gui/icons/pergunta.png"));
        content.setBody(new Label("Tem certeza que deseja sair?"));
        JFXDialog dialogo = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        dialogo.setOverlayClose(false);
        dialogo.setFocusTraversable(true);
        stackPane.setVisible(true);
        botaoNao.setVisible(true);
        botaoSim.setVisible(true);
        botaoNao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogo.close();
                botaoNao.setVisible(false);
                botaoSim.setVisible(false);
                stackPane.setVisible(false);
            }
        });
        botaoSim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setTitle("R.A.V Shop");
                stage.initModality(Modality.APPLICATION_MODAL);
                Scene scene;
                try {
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaLogin.fxml")));
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Erro");
                }
            }
        });
        content.setActions(botaoNao, botaoSim);
        dialogo.show();
    }

    @FXML
    private void redirecionarTelaEstoque(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Estoque de produtos");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaEstoque.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void redirecionaTelaCadastroVendas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Nova venda");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaCadastroVenda.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void redirecionaTelaRelatorioVendas(ActionEvent event) {
        GerarRelatorioPDF gr = new GerarRelatorioPDF();
        gr.criarRelatorio();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new ImageView("gui/icons/okay.png"));
        content.setBody(new Label("Relatório gerado com sucesso"));
        JFXDialog dialogo = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
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

    @FXML
    private void redirecionaTelaPeridoVendas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Grafico gerente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaGraficoGerente.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void botaoListarFuncionarios(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Lista de funcionarios");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaGerenciaFuncionario.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void botaoListarClientes(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Lista de clientes");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaGerenciaCliente.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
