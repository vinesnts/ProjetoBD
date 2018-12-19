/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import negocio.DatePickerFormatter;
import negocio.TextFieldFormatter;
import negocio.entidades.Cliente;
import negocio.excecoes.CPFInvalidoException;
import negocio.excecoes.ClienteExistenteException;
import negocio.excecoes.ClienteInexistenteException;
import negocio.excecoes.DataInvalidaException;
import negocio.excecoes.NomeInvalidoException;

/**
 * FXML Controller class
 *
 * @author vinesnts
 */
public class TelaControladorGerenciaCliente implements Initializable {

    private Fachada fachada;
    private List listaClientes;
    private ObservableList<Cliente> listaObservavelCliente;
    private Cliente cliente;

    @FXML
    private JFXListView<Cliente> listViewCliente;
    @FXML
    private GridPane cadastrarEditarGrid;
    @FXML
    private GridPane menusGrid;
    @FXML
    private Label labelMsg;
    @FXML
    private JFXTextField tfProcurarCPF;
    @FXML
    private JFXButton bProcurar;
    @FXML
    private JFXButton bCancelar;
    @FXML
    private JFXButton bCadastrarCliente;
    @FXML
    private JFXButton bEditarCliente;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXTextField tfCPF;
    @FXML
    private JFXDatePicker dpAniversario;
    @FXML
    private JFXButton bRemover;
    @FXML
    private JFXButton bSalvarCliente;
    @FXML
    private JFXButton bCancelarEdicao;
    @FXML
    private StackPane stackPane;
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
        listarClientes();
    }

    @FXML
    private void botaoSalvarCliente(ActionEvent event) {
        try {
            if (dpAniversario.getEditor().getText().length() != 10) {
                throw new DataInvalidaException();
            }
            if (cliente != null) {
                fachada.atualizarCliente(tfProcurarCPF.getText(),
                        tfNome.getText(),
                        dpAniversario.getValue());
                limparCampos();
                labelMsg.setText("Cliente atualizado");
            } else {
                fachada.adicionarCliente(tfNome.getText(), tfCPF.getText(), dpAniversario.getValue());
                limparCampos();
                labelMsg.setText("Cliente cadastrado");
            }
        } catch (ClienteExistenteException |
                ClienteInexistenteException |
                NomeInvalidoException |
                CPFInvalidoException |
                DataInvalidaException e) {
            labelMsg.setText(e.getMessage());
        }
    }

    @FXML
    private void botaoRemoverCliente(ActionEvent event) {
        if (tfProcurarCPF.getText().equals("")) {
            labelMsg.setText("Campo CPF invalido");
        } else {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new ImageView("gui/icons/pergunta.png"));
            content.setBody(new Label("Tem certeza que deseja remover o cliente?\n"));
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
                        fachada.getCliente(tfProcurarCPF.getText());
                        fachada.removerCliente(tfProcurarCPF.getText());
                        limparCampos();
                        labelMsg.setText("Cliente removido");
                        dialogo.close();
                        botaoNao.setVisible(false);
                        botaoSim.setVisible(false);
                        stackPane.setVisible(false);
                    } catch (ClienteInexistenteException ex) {
                        limparCampos();
                        labelMsg.setText(ex.getMessage());
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
    private void botaoCadastrarCliente(ActionEvent event) {
        limparCampos();
        cadastrarEditarGrid.setVisible(true);
        bRemover.setVisible(false);
        menusGrid.setVisible(false);
        listViewCliente.setVisible(false);
    }

    @FXML
    private void botaoProcurarCPF(ActionEvent event) {
        labelMsg.setText("");
        try {
            if (tfProcurarCPF.getText().equals("")) {
                labelMsg.setText("Digite o CPF");
            } else {
                labelMsg.setText("");
                cliente = fachada.getCliente(tfProcurarCPF.getText());
                tfNome.setText(cliente.getNome());
                tfCPF.setText(cliente.getCpf());
                dpAniversario.setValue(cliente.getDataAniversario());
                tfCPF.setDisable(true);
                cadastrarEditarGrid.setVisible(true);
                menusGrid.setVisible(false);
                listViewCliente.setVisible(false);
            }
        } catch (ClienteInexistenteException ex) {
            limparCampos();
            labelMsg.setText(ex.getMessage());
        }
    }

    @FXML
    private void botaoCancelar(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void botaoCancelarEdicao(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void botaoEditarCliente(ActionEvent event) {
        try {
            cliente = listViewCliente.getSelectionModel().selectedItemProperty().getValue();
            tfProcurarCPF.setText(String.valueOf(cliente.getCpf()));
            tfNome.setText(cliente.getNome());
            tfCPF.setText(String.valueOf(cliente.getCpf()));
            tfCPF.setDisable(true);
            dpAniversario.setValue(cliente.getDataAniversario());
            labelMsg.setText("");
            cadastrarEditarGrid.setVisible(true);
            menusGrid.setVisible(false);
            listViewCliente.setVisible(false);
        } catch (NullPointerException ex) {
            labelMsg.setText("Selecione algum item!");
        }
    }

    private void listarClientes() {
        ArrayList<Cliente> lista = fachada.getClientes();
        listaClientes = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            listaClientes.add(lista.get(i));
        }
        listaObservavelCliente = FXCollections.observableArrayList(listaClientes);
        listViewCliente.setItems(listaObservavelCliente);
    }

    private void limparCampos() {
        cliente = null;
        tfNome.clear();
        tfCPF.clear();
        tfProcurarCPF.clear();
        dpAniversario.setValue(null);
        labelMsg.setText("");
        listarClientes();
        listViewCliente.refresh();
        tfCPF.setDisable(false);
        menusGrid.setVisible(true);
        cadastrarEditarGrid.setVisible(false);
        bRemover.setVisible(true);
        listViewCliente.setVisible(true);
    }

    @FXML
    private void cadastrarCPFOnKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(tfCPF);
        tff.formatter();
        if (tfCPF.getText().contains(" ")) {
            tfCPF.setStyle("-fx-border-color: red;");
        } else {
            tfCPF.setStyle("-fx-border-color: black;");
        }
    }

    @FXML
    private void txtCampoProcurarCPFOnKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(tfProcurarCPF);
        tff.formatter();
    }

    @FXML
    private void caixaDataOnKeyReleased(KeyEvent event) {
        DatePickerFormatter dpf = new DatePickerFormatter();
        dpf.setMask("##/##/####");
        dpf.setCaracteresValidos("0123456789");
        dpf.setDp(dpAniversario);
        dpf.formatter();
    }

}
