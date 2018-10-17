/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private VBox vboxMenu;
    @FXML
    private MenuItem menuItemCadastroCliente;
    @FXML
    private MenuItem menuItematualizaCliente;
    @FXML
    private MenuItem menuItemRemoverCliente;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private MenuItem menuItemMinhasVendas;
    @FXML
    private Button botaoVenda;
    @FXML
    private TextField campoVendedor;
    @FXML
    private Button botaoSair;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = fachada.getInstance();
        campoVendedor.setText(fachada.getLogado().getNome());
    }
    
    @FXML
    private void redirecionaSaidaLogin(ActionEvent event) throws IOException {
        boolean confirmacao = new GUIConfirmation().janelaConfirmacao("Tem certeza que deseja sair?", "Seus dados serao salvos");
    	if(confirmacao) {
    		AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getClass().getResource("/ui/TelaLogin.fxml"));
    		vboxMenu.getChildren().setAll(p);
    	} else {
    		// nada a fazer
    	}

    }

    @FXML
    private void redirecionaTelaCadastroCliente(ActionEvent event) throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/ui/TelaCadastroCliente.fxml"));
//        a.setVisible(true);
//        AnchorPane.getChildren().setAll(a);
    	Stage stage = new Stage();
        stage.setTitle("Cadastrar cliente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaCadastroCliente.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void redirecionaTelaAtualizaCliente(ActionEvent event) throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/ui/TelaAtualizaCliente.fxml"));
//        a.setVisible(true);
//        AnchorPane.getChildren().setAll(a);
    	Stage stage = new Stage();
        stage.setTitle("Atualizar cliente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaAtualizaCliente.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void redirecionaTelaRemoverCliente(ActionEvent event) throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/ui/TelaRemoveCliente.fxml"));
//        a.setVisible(true);
//        AnchorPane.getChildren().setAll(a);
    	Stage stage = new Stage();
        stage.setTitle("Remover cliente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaRemoveCliente.fxml")));
        stage.setScene(scene);
        stage.show();
    }

  
    @FXML
    private void redirecionaTelaCadastroVendas(ActionEvent event) throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/ui/TelaCadastroVenda.fxml"));
//        AnchorPane.getChildren().setAll(a);
    	Stage stage = new Stage();
        stage.setTitle("Nova venda");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaCadastroVenda.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    
    @FXML
    private void redirecionaTelaGraficoVendedor(ActionEvent event) throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/ui/TelaGraficoVendedor.fxml"));
//        AnchorPane.getChildren().setAll(a);
    	Stage stage = new Stage();
        stage.setTitle("Gr�fico vendedor");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaGraficoVendedor.fxml")));
        stage.setScene(scene);
        stage.show();
    }

  
    
}
