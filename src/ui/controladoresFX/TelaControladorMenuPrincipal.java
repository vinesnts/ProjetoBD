/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import grafico.GerarRelatorioPDF;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class TelaControladorMenuPrincipal implements Initializable {
    
    private Fachada fachada;

    @FXML
    private VBox vboxMenu;
    @FXML
    private MenuItem menuItemRelatorioVendas;
    @FXML
    private MenuItem menuItemVendasPeriodo;
    @FXML
    private Label gerenteAvisos;
    @FXML
    private Button botaoTelaEstoque;
    @FXML
    private Button botaoCadastrarVenda;
    @FXML
    private TextField campoFuncionario;
    @FXML
    private Button botaoSair;
    @FXML
    private Button botaoListarFuncionario;
    @FXML
    private Button botaoListarClientes;

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
    	boolean confirmacao = new GUIConfirmation().janelaConfirmacao("Tem certeza que deseja sair?", "Seus dados serao salvos");
    	if(confirmacao) {
    		AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getClass().getResource("/ui/TelaLogin.fxml"));
    		vboxMenu.getChildren().setAll(p);
    	} else {
    		// nada a fazer
    	}

    }

    @FXML
    private void redirecionarTelaEstoque(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Estoque de produtos");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaEstoque.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    private void redirecionaTelaCadastroVendas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Nova venda");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaCadastroVenda.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void redirecionaTeladeRelatorioVendas(ActionEvent event) {
        GerarRelatorioPDF gr = new GerarRelatorioPDF();
        gr.criarRelatorio();
        gerenteAvisos.setText("Relatario gerado com sucesso!");

    }

    @FXML
    private void redirecionaTelaPeridoVendas(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
        stage.setTitle("Grafico gerente");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaGraficoGerente.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void botaoListarFuncionario(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Lista de funcionarios");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ui/TelaGerenciaFuncionario.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
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
