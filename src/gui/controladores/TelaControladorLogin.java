
package gui.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import fachada.Fachada;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.TextFieldFormatter;
import negocio.entidades.Funcionario;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 * FXML Controller class
 *
 * @author Raquell Vieira
 */
public class TelaControladorLogin implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private Fachada fachada;
    @FXML
    private Label labelMsg;
    @FXML
    private JFXButton bEntrar;
    @FXML
    private JFXPasswordField txtCampoSenha;
    @FXML
    private JFXTextField txtCampoCPF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fachada = Fachada.getInstance();
    }

    @FXML
    private void acaoBotaoEntrar(ActionEvent event) throws IOException {

        try {
            Funcionario funcionario = fachada.getFuncionario(txtCampoCPF.getText());
            if (funcionario.getCpf().equals(txtCampoCPF.getText())
                    && funcionario.getSenha().equals(txtCampoSenha.getText())) {
                fachada.setLogado(funcionario);
                System.out.println(fachada.getLogado().getNome());
                limparCampo();
                if (funcionario.eGerente()) {
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    Stage stage = new Stage();
                    stage.setTitle("Bem-Vindo(a), Gerente!");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaMenuGerente.fxml")));
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } else {
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    Stage stage = new Stage();
                    stage.setTitle("Bem-Vindo(a), Vendedor!");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/TelaMenuVendedor.fxml")));
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }

            } else {
                limparCampo();
                labelMsg.setText("Senha invalida");
                txtCampoSenha.setStyle("-jfx-unfocus-color: red;");
            }

        } catch (FuncionarioInexistenteException erro) {
            limparCampo();
            labelMsg.setText("Funcionario inexistente");
            txtCampoCPF.setStyle("-jfx-unfocus-color: red;");
        }

    }

    private void limparCampo() {
        txtCampoSenha.clear();
        labelMsg.setText("");
        txtCampoSenha.setStyle("-jfx-unfocus-color: #0080ff;");
        txtCampoCPF.setStyle("-jfx-unfocus-color: #0080ff;");
    }

    @FXML
    private void txtCampoCPFOnKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCampoCPF);
        tff.formatter();
    }
}
