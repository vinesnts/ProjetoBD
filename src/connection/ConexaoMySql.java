
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import negocio.entidades.Funcionario;

/**
 *
 * @author Raquell Vieira
 */
public class ConexaoMySql {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        String servidor = "127.0.0.1:3306"; 
        String nomeDoBanco = "projetoloja";
        final String url = "jdbc:mariadb://" + servidor + "/" + nomeDoBanco + "?useTimezone=true&serverTimezone=UTC";
        String usuario = "root";
        String senha = "99102904";
        
        return DriverManager.getConnection(url, usuario, senha);
    }
    
    public static void gerarUsuario() {
        String sql = "INSERT IGNORE funcionario VALUES (?,?,?,?)";
        Funcionario funcionario = new Funcionario("Administrador", "000.000.000-00", true, "admin");
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setBoolean(1, funcionario.eGerente());
            pst.setString(2, funcionario.getCpf());
            pst.setString(3, funcionario.getNome());
            pst.setString(4, funcionario.getSenha());
            
            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

}
