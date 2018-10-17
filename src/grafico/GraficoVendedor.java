/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafico;

import fachada.Fachada;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import negocio.entidades.Venda;

/**
 *
 * @author Raquell Vieira
 */
public class GraficoVendedor extends Application {

    private String matricula = "";
    Fachada fachada = Fachada.getInstance();
    

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage s) throws Exception {
        s.setScene(new Scene(new FlowPane(criarGraficoLinha())));
        s.show();
    }
    

    //grafico vendedor
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Node criarGraficoLinha() {
        LineChart<String, Number> graficoLinha = new LineChart<>(
                new CategoryAxis(), new NumberAxis());
        XYChart.Series quantidade = new XYChart.Series();
        quantidade.setName("Total de Vendas");
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            int mes = vendas.get(i).getData().getMonthValue();
            quantidade.getData().add(new XYChart.Data("" + vendas.get(i).getData().getMonth(), getValorTotalDasVendasMesFuncionario(mes, matricula)));

        }
       
        graficoLinha.getData().addAll(quantidade);
        graficoLinha.setPrefSize(400, 250);
        return graficoLinha;
    }

    private double getValorTotalDasVendasMesFuncionario(int mes, String matricula) {
        double valorVenda = 0.0;
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (mes == vendas.get(i).getData().getMonthValue() && matricula.equals(vendas.get(i).getFuncionario().getMatricula())) {
                valorVenda += vendas.get(i).calcularValorVenda();
            }
        }

        return valorVenda;
    }
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }
}
