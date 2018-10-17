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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import negocio.entidades.Venda;

/**
 *
 * @author Raquell Vieira
 */
public class Grafico extends Application {

    private static final int GRAFICO_ALTURA = 300;
    private static final int GRAFICO_LARGURA = 400;
    private Fachada fachada;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage s) throws Exception {
        s.setScene(new Scene(new FlowPane(criarGraficoPizzaTeste())));
         //s.setScene(new Scene(new FlowPane(criarGraficoLinha())));
        s.setTitle("Gráficos Vendas");
        s.setWidth(400);
        s.setHeight(400);
        s.show();
    }

    private Node criarGraficoPizzaTeste() {
        PieChart graficoPizza = new PieChart();
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getData().getYear() == 2017) {
                graficoPizza.getData().addAll(new PieChart.Data("Valor " + vendas.get(i).getData().getMonth(), vendas.get(i).calcularValorVenda()));

            }
        }

        graficoPizza.setTitle("Gráfico Vendas.");
        graficoPizza.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
        return graficoPizza;
    }
    //grafico vendedor
    private Node criarGraficoPizzaTeste1() {
        PieChart graficoPizza = new PieChart();
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getFuncionario().getMatricula().equals("AJ117")) {
                graficoPizza.getData().addAll(new PieChart.Data("Valor " + vendas.get(i).getData().getMonth(), 5));

            }
        }

        graficoPizza.setTitle("Gráfico Vendas.");
        graficoPizza.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
        return graficoPizza;
    }
    //grafico gerente
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Node criarGraficoLinha() {
        LineChart<String, Number> graficoLinha = new LineChart<>(
                new CategoryAxis(), new NumberAxis());
        XYChart.Series quantidade = new XYChart.Series();
        quantidade.setName("Total de Vendas");

        ArrayList<Venda> vendas = fachada.getVendas();
        int mesInicio = 6;
        int anoInicio = 2017;
        int mesFim = 12;
        int anoFim = 2017;
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getData().getMonthValue() >= mesInicio && vendas.get(i).getData().getYear() >= anoInicio) {
                if (vendas.get(i).getData().getMonthValue() <= mesFim && vendas.get(i).getData().getYear() <= anoFim) {
                    quantidade.getData().add(new XYChart.Data(""+ (vendas.get(i).getData().getMonth()), vendas.get(i).calcularValorVenda()));

                }
            }

        }

        graficoLinha.getData().addAll(quantidade);
        graficoLinha.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
        return graficoLinha;
    }
    
}
