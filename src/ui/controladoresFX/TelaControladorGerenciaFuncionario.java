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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import negocio.TextFieldFormatter;
import negocio.entidades.Funcionario;
import negocio.excecoes.CPFInvalidoException;
import negocio.excecoes.ClienteInexistenteException;
import negocio.excecoes.FuncionarioExistenteException;
import negocio.excecoes.FuncionarioInexistenteException;
import negocio.excecoes.NomeInvalidoException;
import negocio.excecoes.SenhaInvalidaException;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class TelaControladorGerenciaFuncionario implements Initializable {

    private Fachada fachada;
    private List listaFuncionario;
    private ObservableList<Funcionario> listaObservavelFuncionario;
    private Funcionario funcionario;
    private boolean eGerente;

    @FXML
    private GridPane cadastrarEditarGrid;
    @FXML
    private TextField txtCampoNome;
    @FXML
    private TextField txtCampoCPF;
    @FXML
    private Button botaoSalvarFuncionario;
    @FXML
    private Button botaoCancelarEdicao;
    @FXML
    private Button botaoRemoverFuncionario;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private Label labelTipo;
    @FXML
    private GridPane menusGrid;
    @FXML
    private Button botaoCadastrarFuncionario;
    @FXML
    private Button botaoEditarFuncionario;
    @FXML
    private TextField txtCampoMatricula;
    @FXML
    private Button botaoProcurarMatricula;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Label labelMsg;
    @FXML
    private ListView<Funcionario> listViewFuncionarios;
    @FXML
    private ToggleGroup funcionarioGroup;
    @FXML
    private RadioButton radioGerente;
    @FXML
    private RadioButton radioVendedor;
    @FXML
    private StackPane stackPane;
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
        fachada = Fachada.getInstance();
        listarFuncionarios();
    }

    private void listarFuncionarios() {
        ArrayList<Funcionario> lista = fachada.getFuncionarios();
        listaFuncionario = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            listaFuncionario.add(lista.get(i));
        }
        listaObservavelFuncionario = FXCollections.observableArrayList(listaFuncionario);
        listViewFuncionarios.setItems(listaObservavelFuncionario);
    }

    @FXML
    private void botaoCancelarEdicao(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void botaoCancelar(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void limparCampos() {
        funcionario = null;
        eGerente = false;
        txtCampoNome.clear();
        txtCampoCPF.clear();
        campoSenha.clear();
        labelMsg.setText("");
        txtCampoMatricula.setText("");
        listarFuncionarios();
        listViewFuncionarios.refresh();
        txtCampoCPF.setDisable(false);
        menusGrid.setVisible(true);
        cadastrarEditarGrid.setVisible(false);
        botaoRemoverFuncionario.setVisible(true);
        listViewFuncionarios.setVisible(true);
        radioGerente.setSelected(false);
        radioVendedor.setSelected(false);
    }

    @FXML
    private void botaoSalvarFuncionario(ActionEvent event) {
        try {
            if (funcionario == null) {
                String matricula = gerarMatricula(eGerente, txtCampoCPF.getText());
                fachada.adicionarFuncionario(txtCampoNome.getText(),
                        txtCampoCPF.getText(),
                        eGerente,
                        matricula,
                        campoSenha.getText());
                limparCampos();
                labelMsg.setText("Cadastrado com ID: " + matricula);
            } else {
                fachada.atualizarFuncionario(txtCampoMatricula.getText(), txtCampoNome.getText(),
                        eGerente,
                        campoSenha.getText());
                limparCampos();
                labelMsg.setText("Funcionario Atualizado.");
            }
        } catch (FuncionarioExistenteException
                | FuncionarioInexistenteException
                | NomeInvalidoException
                | CPFInvalidoException
                | SenhaInvalidaException e) {
            labelMsg.setText(e.getMessage());
        }
    }

    @FXML
    private void botaoRemoverFuncionario(ActionEvent event) {
        if (txtCampoMatricula.getText().equals("")) {
            labelMsg.setText("Campo matricula invalido");
        } else {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new ImageView("ui/icons/pergunta.png"));
            content.setBody(new Label("Tem certeza que deseja demitir o funcionario?\n"));
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
                    try {
                        fachada.getFuncionario(txtCampoMatricula.getText());
                        fachada.removerFuncionario(txtCampoMatricula.getText());
                        labelMsg.setText("Funcionario removido");
                        limparCampos();
                        dialogo.close();
                        botaoNao.setVisible(false);
                        botaoSim.setVisible(false);
                        stackPane.setVisible(false);
                    } catch (FuncionarioInexistenteException ex) {
                        txtCampoMatricula.clear();
                        labelMsg.setText(ex.getMessage());
                        limparCampos();
                        dialogo.close();
                        botaoNao.setVisible(false);
                        botaoSim.setVisible(false);
                        stackPane.setVisible(false);
                    }
                }
            });
            content.setActions(botaoNao, botaoSim);
            dialogo.show();
        }
    }

    @FXML
    private void botaoCadastrarFuncionario(ActionEvent event) {
        limparCampos();
        cadastrarEditarGrid.setVisible(true);
        botaoRemoverFuncionario.setVisible(false);
        menusGrid.setVisible(false);
        listViewFuncionarios.setVisible(false);
    }

    @FXML
    private void botaoEditarFuncionario(ActionEvent event) {
        try {
            funcionario = listViewFuncionarios.getSelectionModel().selectedItemProperty().getValue();
            txtCampoMatricula.setText(String.valueOf(funcionario.getMatricula()));
            txtCampoNome.setText(funcionario.getNome());
            txtCampoCPF.setText(String.valueOf(funcionario.getCpf()));
            if (funcionario.eGerente()) {
                radioGerente.setSelected(true);
                radioVendedor.setSelected(false);
                eGerente = true;
            } else {
                radioGerente.setSelected(false);
                radioVendedor.setSelected(true);
                eGerente = false;
            }
            txtCampoCPF.setDisable(true);
            labelMsg.setText("");
            cadastrarEditarGrid.setVisible(true);
            menusGrid.setVisible(false);
            listViewFuncionarios.setVisible(false);
        } catch (NullPointerException ex) {
            labelMsg.setText("Selecione algum item!");
        }
    }

    @FXML
    private void botaoProcurarMatricula(ActionEvent event) {
        labelMsg.setText("");
        try {
            if (txtCampoMatricula.getText().equals("")) {
                labelMsg.setText("Digite a matricula");
            } else {
                labelMsg.setText("");
                funcionario = fachada.getFuncionario(txtCampoMatricula.getText());
                txtCampoNome.setText(funcionario.getNome());
                txtCampoCPF.setText(funcionario.getCpf());
                campoSenha.setText(campoSenha.getText());
                if (funcionario.eGerente()) {
                    radioGerente.setSelected(true);
                    radioVendedor.setSelected(false);
                    eGerente = true;
                } else {
                    radioGerente.setSelected(false);
                    radioVendedor.setSelected(true);
                    eGerente = false;
                }
                txtCampoCPF.setDisable(true);
                cadastrarEditarGrid.setVisible(true);
                menusGrid.setVisible(false);
                listViewFuncionarios.setVisible(false);
            }
        } catch (FuncionarioInexistenteException e) {
            limparCampos();
            labelMsg.setText(e.getMessage());

        }
    }

    @FXML
    private void radioGerente(ActionEvent event) {
        this.eGerente = true;
    }

    @FXML
    private void radioVendedor(ActionEvent event) {
        this.eGerente = false;
    }

    public static String gerarMatricula(boolean eGerente, String cpf) {
        if (eGerente) {
            return "G" + cpf;
        } else {
            return "V" + cpf;
        }
    }

    @FXML
    private void txtCampoCPFOnKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCampoCPF);
        tff.formatter();
        if (txtCampoCPF.getText().contains(" ")) {
            txtCampoCPF.setStyle("-jfx-focus-color: red;");
        } else {
            txtCampoCPF.setStyle("-jfx-focus-color: #0080ff;");
        }
    }

    @FXML
    private void txtCampoMatriculaOnKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("?###.###.###-##");
        tff.setCaracteresValidos("GVgv0123456789");
        tff.setTf(txtCampoMatricula);
        tff.formatter();
    }

}
