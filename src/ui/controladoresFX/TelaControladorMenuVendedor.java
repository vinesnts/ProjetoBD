/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import fachada.Fachada;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorMenuVendedor implements Initializable {

    private Fachada fachada;

    @FXML
    private Button botaoVenda;
    @FXML
    private TextField campoVendedor;
    @FXML
    private Button botaoSair;
    @FXML
    private Button botaoListarClientes;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton botaoOkay;
    @FXML
    private JFXButton botaoNao;
    @FXML
    private JFXButton botaoSim;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = fachada.getInstance();
        campoVendedor.setText(fachada.getLogado().getNome());
    }

    @FXML
    private void redirecionaSaidaLogin(ActionEvent event) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new ImageView("ui/icons/pergunta.png"));
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
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaLogin.fxml")));
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

    private void redirecionaTelaCadastroCliente(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar cliente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaCadastroCliente.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    private void redirecionaTelaAtualizaCliente(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Atualizar cliente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaAtualizaCliente.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    private void redirecionaTelaRemoverCliente(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Remover cliente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaRemoveCliente.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void redirecionaTelaCadastroVendas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Nova venda");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaCadastroVenda.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void redirecionaTelaGraficoVendedor(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Gr�fico vendedor");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaGraficoVendedor.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void botaoListarClientes(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Lista de clientes");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaGerenciaCliente.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
