/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import connection.ConexaoMySql;
import dados.repositoriobd.RepositorioCliente;
import java.time.LocalDate;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import negocio.entidades.Cliente;

/**
 *
 * @author Raquell Vieira e Adilson Júnior
 */
public class Tela extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TelaGerenciaCliente.fxml"));
        stage.setTitle("R.A.V. Shop");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setHeight(600);
        stage.setWidth(810);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
