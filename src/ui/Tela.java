/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Raquell Vieira e Adilson Júnior
 */
public class Tela extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TelaLogin.fxml"));
        stage.setTitle("R.A. Corporation ® Generic Shop.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setHeight(500);
        stage.setWidth(616);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
