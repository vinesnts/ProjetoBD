/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositoriobd;

import connection.ConexaoMySql;
import negocio.entidades.Cliente;
import dados.interfacerepositorio.IRepositorioCliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import negocio.excecoes.ClienteInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioCliente implements IRepositorioCliente {

    private static RepositorioCliente instancia;

    public static RepositorioCliente getInstance() {
        if (instancia == null) {
            instancia = new RepositorioCliente();
        }

        return instancia;
    }

    private RepositorioCliente() {
        String sql = "CREATE TABLE IF NOT EXISTS `cliente` (\n" +
                    "  `CPF` varchar(14) NOT NULL,\n" +
                    "  `Nome` varchar(45) NOT NULL,\n" +
                    "  `DataAniversario` date DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`CPF`))";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.execute();
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
    
    @Override
    public void adicionar(Cliente cliente) {
        String sql = "INSERT INTO cliente VALUES (?,?,?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setString(1, cliente.getCpf());
            pst.setString(2, cliente.getNome());
            pst.setDate(3, Date.valueOf(cliente.getDataAniversario()));

            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (ClassNotFoundException | SQLException e) {
           System.out.println("ERRO: " + e.getMessage());
        }
    }

    @Override
    public void remover(Cliente cliente) {
        String sql = "UPDATE cliente SET DataAniversario=(?) WHERE CPF=(?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setString(1, null);
            pst.setString(2, cliente.getCpf());
            
            pst.executeUpdate();
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

    }

    @Override
    public void atualizar(Cliente cliente) throws ClienteInexistenteException {
        String sql = "UPDATE cliente SET Nome=(?), DataAniversario=(?) WHERE CPF=(?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setString(1, cliente.getNome());
            pst.setDate(2, Date.valueOf(cliente.getDataAniversario()));
            pst.setString(3, cliente.getCpf());
            
            pst.executeUpdate();
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    @Override
    public Cliente buscar(String cpf) throws ClienteInexistenteException {
        String sql = "SELECT * FROM (\n" +
                    "	SELECT * FROM cliente \n" +
                    "	WHERE CPF=(?)) c\n" +
                    "WHERE NOT c.DataAniversario IS NULL";
        Cliente cliente = null;
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setString(1, cpf);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String cpfa = rs.getString("CPF");
                String nome = rs.getString("Nome");
                String dataAniversario = rs.getString("DataAniversario");
                cliente = new Cliente(nome, cpfa, LocalDate.parse(dataAniversario, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            }

            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        
        if(cliente == null) throw new ClienteInexistenteException();
        else return cliente;
    }

    @Override
    public boolean verificarExistencia(Cliente cliente) {

        String sql = "SELECT * FROM (\n" +
                    "	SELECT * FROM cliente \n" +
                    "	WHERE CPF=(?)) c\n" +
                    "WHERE NOT c.DataAniversario IS NULL";
        Cliente c;
        try {
            Connection conn = ConexaoMySql.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, cliente.getCpf());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("Nome");
                String cpfa = rs.getString("CPF");
                String data = rs.getString("DataAniversario");

                c = new Cliente(nome, cpfa, LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                return true;
            }

            pst.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return false;
    }

    @Override
    public ArrayList<Cliente> getClientes() {
        String sql = "SELECT * FROM cliente\n"
                + "WHERE NOT DataAniversario IS NULL";
        ArrayList<Cliente> lista = new ArrayList<>();

        try {
            Connection conn = ConexaoMySql.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String cpf = rs.getString("CPF");
                String nome = rs.getString("Nome");
                String dataAniversario = rs.getString("DataAniversario");
                lista.add(new Cliente(nome, cpf, LocalDate.parse(dataAniversario, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            }

            pst.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return lista;

    }

}
