
package grafico;

import fachada.Fachada;
import java.time.Month;
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

    private String cpf = "";
    Fachada fachada = Fachada.getInstance();
    

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage s) throws Exception {
        s.setScene(new Scene(new FlowPane(criarGraficoLinha(this.cpf))));
        s.show();
    }
    

    //grafico vendedor
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Node criarGraficoLinha(String cpf) {
        this.cpf = cpf;
        LineChart<String, Number> graficoLinha = new LineChart<>(
                new CategoryAxis(), new NumberAxis());
        XYChart.Series quantidade = new XYChart.Series();
        quantidade.setName("Total de Vendas (R$)");
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            int mes = vendas.get(i).getData().getMonthValue();
            quantidade.getData().add(new XYChart.Data("" + traduzirMes(vendas.get(i).getData().getMonth()), getValorTotalDasVendasMesFuncionario(mes, cpf)));

        }
       
        graficoLinha.getData().addAll(quantidade);
        graficoLinha.setPrefSize(400, 250);
        return graficoLinha;
    }

    private double getValorTotalDasVendasMesFuncionario(int mes, String cpf) {
        double valorVenda = 0.0;
        ArrayList<Venda> vendas = fachada.getVendas();
        for (int i = 0; i < vendas.size(); i++) {
            if (mes == vendas.get(i).getData().getMonthValue() && cpf.equals(vendas.get(i).getFuncionario().getCpf())) {
                valorVenda += vendas.get(i).getPrecoTotal();
            }
        }

        return valorVenda;
    }
    
    private String traduzirMes(Month mes) {
        String smes;
        switch(mes.getValue()) {
            case 1:  smes = "Janeiro";
                return smes;
            case 2:  smes = "Fevereiro";
                return smes;
            case 3:  smes = "Marco";
                return smes;
            case 4:  smes = "Abril";
                return smes;
            case 5:  smes = "Maio";
                return smes;
            case 6:  smes = "Junho";
                return smes;
            case 7:  smes = "Julho";
                return smes;
            case 8:  smes = "Agosto";
                return smes;
            case 9:  smes = "Setembro";
                return smes;
            case 10: smes = "Outubro";
                return smes;
            case 11: smes = "Novembro";
                return smes;
            case 12: smes = "Dezembro";
                return smes;
            default: smes = "Invalid month";
                return smes;
        }
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
}
