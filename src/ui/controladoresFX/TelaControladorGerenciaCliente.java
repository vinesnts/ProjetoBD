/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controladoresFX;

import fachada.Fachada;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import negocio.entidades.Cliente;
import negocio.excecoes.ClienteExistenteException;
import negocio.excecoes.ClienteInexistenteException;
import negocio.excecoes.FuncionarioInexistenteException;

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
    private ListView<Cliente> listViewCliente;
    @FXML
    private GridPane cadastrarEditarGrid;
    @FXML
    private TextField txtCampoNome;
    @FXML
    private TextField txtCampoCPF;
    @FXML
    private Button botaoSalvarCliente;
    @FXML
    private Button botaoCancelarEdicao;
    @FXML
    private Button botaoRemoverCliente;
    @FXML
    private DatePicker caixaData;
    @FXML
    private GridPane menusGrid;
    @FXML
    private Button botaoCadastrarCliente;
    @FXML
    private Button botaoEditarCliente;
    @FXML
    private Button botaoProcurarCPF;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Label labelMsg;
    @FXML
    private TextField txtCampoProcurarCPF;

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
        if (txtCampoNome.getText().equals("") || txtCampoCPF.getText().equals("") || caixaData.getValue() == null) {
            labelMsg.setText("Campo invalido");
        } else {
            try {
                if (cliente != null) {
                    cliente = fachada.getCliente(txtCampoProcurarCPF.getText());
                    if (cliente.getCpf().equals(txtCampoProcurarCPF.getText())) {
                        cliente.setNome(txtCampoNome.getText());
                        cliente.setDataAniversario(caixaData.getValue());
                        fachada.atualizarCliente(cliente);
                        limparCampos();
                        labelMsg.setText("Cliente atualizado");
                    }
                } else {
                    fachada.adicionarCliente(txtCampoNome.getText(), txtCampoCPF.getText(), caixaData.getValue());
                    limparCampos();
                    labelMsg.setText("Cliente cadastrado");
                }

            } catch (ClienteExistenteException | ClienteInexistenteException e) {
                limparCampos();
                labelMsg.setText(e.getMessage());
            }
        }

    }

    @FXML
    private void botaoRemoverCliente(ActionEvent event) {
        if (txtCampoProcurarCPF.getText().equals("")) {
            labelMsg.setText("Campo CPF invalido");
        } else {
            boolean confirmacao = new GUIConfirmation().janelaConfirmacao(
                    "Tem certeza que deseja remover o cliente?",
                    "Todas as informacoes dele serao perdidas");
            if (confirmacao) {
                try {
                    fachada.getCliente(txtCampoProcurarCPF.getText());
                    fachada.removerCliente(txtCampoProcurarCPF.getText());
                    limparCampos();
                    labelMsg.setText("Cliente removido");
                } catch (ClienteInexistenteException ex) {
                    limparCampos();
                    labelMsg.setText(ex.getMessage());
                }
            }
        }
    }

    @FXML
    private void caixaData(ActionEvent event) {
    }

    @FXML
    private void botaoCadastrarCliente(ActionEvent event) {
        limparCampos();
        cadastrarEditarGrid.setVisible(true);
        botaoRemoverCliente.setVisible(false);
        menusGrid.setVisible(false);
        listViewCliente.setVisible(false);
    }

    @FXML
    private void botaoProcurarCPF(ActionEvent event) {
        labelMsg.setText("");
        try {
            if (txtCampoProcurarCPF.getText().equals("")) {
                labelMsg.setText("Digite o CPF");
            } else {
                labelMsg.setText("");
                cliente = fachada.getCliente(txtCampoProcurarCPF.getText());
                txtCampoNome.setText(cliente.getNome());
                txtCampoCPF.setText(cliente.getCpf());
                caixaData.setValue(cliente.getDataAniversario());
                txtCampoCPF.setDisable(true);
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
            txtCampoProcurarCPF.setText(String.valueOf(cliente.getCpf()));
            txtCampoNome.setText(cliente.getNome());
            txtCampoCPF.setText(String.valueOf(cliente.getCpf()));
            txtCampoCPF.setDisable(true);
            caixaData.setValue(cliente.getDataAniversario());
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
        txtCampoNome.clear();
        txtCampoCPF.clear();
        txtCampoProcurarCPF.clear();
        caixaData.setValue(null);
        labelMsg.setText("");
        listarClientes();
        listViewCliente.refresh();
        txtCampoCPF.setDisable(false);
        menusGrid.setVisible(true);
        cadastrarEditarGrid.setVisible(false);
        botaoRemoverCliente.setVisible(true);
        listViewCliente.setVisible(true);
    }

}
