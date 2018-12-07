/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositoriobd;

import connection.ConexaoMySql;
import negocio.entidades.Cliente;
import dados.repositorioInterface.IRepositorioCliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.excecoes.ClienteInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioCliente implements IRepositorioCliente {

    private static RepositorioCliente myself;

    public static RepositorioCliente getInstance() {
        if (myself == null) {
            myself = new RepositorioCliente();
        }

        return myself;
    }

    public RepositorioCliente() {
    }

    @Override
    public void adicionar(Cliente cliente) {
        String sql = "INSERT INTO cliente VALUES (?,?,?)";

        try {

            Connection conn = ConexaoMySql.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, cliente.getCpf());
            pst.setString(2, cliente.getNome());
            pst.setDate(3, Date.valueOf(cliente.getDataAniversario()));

            pst.execute();

            pst.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Cliente cliente) {

        String sql = "DELETE FROM cliente WHERE CPF=(?)";

        try {
            Connection conn = ConexaoMySql.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, cliente.getCpf());
            pst.execute();

            pst.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RepositorioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void atualizar(String cpf, String nome, LocalDate dataAniversario) throws ClienteInexistenteException {
        String sql = "UPDATE cliente SET Nome=?, DataAniversario=? WHERE CPF=(?)";

        try {
            Connection conn = ConexaoMySql.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, nome);
            pst.setDate(2, Date.valueOf(dataAniversario));
            pst.setString(3, cpf);
            pst.executeUpdate();

            pst.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente buscar(String cpf) throws ClienteInexistenteException {
        String sql = "SELECT * FROM cliente WHERE CPF=?";
        Cliente cliente = null;
        try {
            Connection conn = ConexaoMySql.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, cpf);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String cpfa = rs.getString("CPF");
                String nome = rs.getString("Nome");
                String dataAniversario = rs.getString("DataAniversario");
                cliente = new Cliente(nome, cpfa, LocalDate.parse(dataAniversario, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            }

            pst.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public boolean verificarExistencia(Cliente cliente) {

        String sql = "SELECT * FROM cliente WHERE CPF = (?)";
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
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Cliente> getClientes() {
        String sql = "SELECT * FROM cliente";
        ArrayList<Cliente> lista = new ArrayList<Cliente>();

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
            e.printStackTrace();
        }

        return lista;

    }

}
