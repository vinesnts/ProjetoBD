
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
        launch();
    }

    @Override
    public void start(Stage s) throws Exception {
        s.setScene(new Scene(new FlowPane(criarGraficoPizza())));
        s.setTitle("Gráfico Vendas Gerente");
        s.setWidth(400);
        s.setHeight(300);
        s.show();
    }
    
    public Node criarGraficoPizza() {
        PieChart graficoPizza = new PieChart();
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 1; i <13; i++) {
                graficoPizza.getData().addAll(new PieChart.Data("" + "Mes: "+i, getValorTotalDasVendasMes(i, ano)));
        }

        graficoPizza.setTitle("Gráfico Vendas "+ ano);
        graficoPizza.setPrefSize(400, 300);
        return graficoPizza;

    }

    private double getValorTotalDasVendasMes(int mes, int ano) {
        double valorVenda = 0.0;
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (mes == vendas.get(i).getData().getMonthValue() && ano == vendas.get(i).getData().getYear()) {
                valorVenda = vendas.get(i).getPrecoTotal();
            }
        }

        return valorVenda;
    }
    
    
    public void setAno(int ano){
        this.ano = ano;
    }
    
    
}