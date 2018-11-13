package ui.controladoresFX;

import fachada.Fachada;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GUIConfirmation {

    private boolean escolha;
    Fachada fachada = Fachada.getInstance();

    public boolean janelaConfirmacao(String pergunta, String aviso) {
        Alert dialogoPergunta = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Nao");
        dialogoPergunta.getButtonTypes().setAll(btnSim, btnNao);
        dialogoPergunta.setTitle("Voce tem certeza?");
        dialogoPergunta.setHeaderText(pergunta);
        dialogoPergunta.setContentText(aviso);
        dialogoPergunta.showAndWait().ifPresent(b -> {
            if (b == btnSim) {
                dialogoPergunta.close();
                escolha = true;
            } else if (b == btnNao) {
                dialogoPergunta.close();
                escolha = false;
            }
        });
        return escolha;
    }
}
