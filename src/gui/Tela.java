
package gui;

import connection.ConexaoMySql;
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
        stage.setTitle("R.A.V. Shop");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setHeight(600);
        stage.setWidth(810);
        stage.show();
    }

    public static void main(String[] args) {
        ConexaoMySql.gerarUsuario();
        launch(args);
    }
    
}
