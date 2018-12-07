/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Raquell Vieira
 */
public class ConexaoMySql {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        String servidor = "127.0.0.1:3307"; 
        String nomeDoBanco = "projetoloja";
        final String url = "jdbc:mariadb://" + servidor + "/" + nomeDoBanco + "?useTimezone=true&serverTimezone=UTC";
        String usuario = "root";
        String senha = "99102904";

        System.out.println("carregou o driver");

        return DriverManager.getConnection(url, usuario, senha);

    }
  

}
