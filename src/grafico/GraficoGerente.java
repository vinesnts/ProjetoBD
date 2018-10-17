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
import javafx.scene.chart.PieChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import negocio.entidades.Venda;

/**
 *
 * @author Raquell Vieira
 */
public class GraficoGerente extends Application {

    private Fachada fachada = Fachada.getInstance();
    private int ano;

    public static void main(String[] args) {
        //Fachada fachada = new Fachada();
        launch();
    }

    @Override
    public void start(Stage s) throws Exception {
        s.setScene(new Scene(new FlowPane(criarGraficoPizza())));
        s.setTitle("Gráficos Vendas Gerente");
        s.setWidth(400);
        s.setHeight(450);
        s.show();
    }

    public Node criarGraficoPizza() {
        PieChart graficoPizza = new PieChart();
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getData().getYear() == ano) {
                graficoPizza.getData().addAll(new PieChart.Data("" + vendas.get(i).getId() , vendas.get(i).calcularValorVenda()));

            }
        }

        graficoPizza.setTitle("Gráfico Vendas ."+ ano);
        graficoPizza.setPrefSize(300, 400);
        return graficoPizza;

    }

    private double getValorTotalDasVendasMesFuncionario(int mes) {
        double valorVenda = 0.0;
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (mes == vendas.get(i).getData().getMonthValue()) {
                valorVenda += vendas.get(i).calcularValorVenda();
            }
        }

        return valorVenda;
    }
    public void setAno(int ano){
        this.ano = ano;
    }
}
