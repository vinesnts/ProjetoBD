
package dados.repositoriobd;

import negocio.excecoes.PacoteInexistenteException;
import connection.ConexaoMySql;
import java.util.ArrayList;
import negocio.entidades.Pacote;
import dados.interfacerepositorio.IRepositorioPacote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.entidades.Produto;


/**
 *
 * @author Raquell Vieira
 */
public class RepositorioPacote implements IRepositorioPacote {
    
    private static RepositorioPacote instancia;
    
    public static RepositorioPacote getInstance() {
        if (instancia == null) {
            instancia = new RepositorioPacote();
        }
        return instancia;
    }
    
    private RepositorioPacote() {
        String sql = "CREATE TABLE IF NOT EXISTS `pacote` (\n" +
                    "  `IdProduto` int(11) NOT NULL,\n" +
                    "  `Quantidade` int(11) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`IdProduto`),\n" +
                    "  CONSTRAINT `IdProduto` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`IdProduto`))";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.execute();
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); //System.out.println("ERRO: " + e.getMessage());
        }
    }
    
    /**
     *
     * @param pacote pacote a ser adicionado
     *
     */
    @Override
    public void adicionar(Pacote pacote) {
        String sql = "INSERT INTO pacote(IdProduto, Quantidade) VALUES (\n" +
                    "(SELECT IdProduto FROM produto WHERE IdProduto=(?)),?)";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, pacote.getProduto().getId());
            pst.setInt(2, pacote.getQuantidade());
            
            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); //System.out.println("ERRO: " + e.getMessage());
        }
    }
    
    /**
     *
     * @param pacote pacote a ser removido
     *
     */
    @Override
    public void remover(Pacote pacote) {
        String sql = "DELETE FROM pacote WHERE IdProduto=(\n" +
                    "(SELECT IdProduto FROM produto WHERE IdProduto=(?)))";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, pacote.getProduto().getId());
            
            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); //System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param pacote Verifica a posicao do produto e sobrescreve a sua
     * quantidade
     */
    @Override
    public void atualizar(Pacote pacote) {
        String sql = "UPDATE pacote SET Quantidade=(?) WHERE IdProduto=(" +
                    "(SELECT IdProduto FROM produto WHERE IdProduto=(?)))";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, pacote.getQuantidade());
            pst.setInt(2, pacote.getProduto().getId());
            
            pst.executeUpdate();
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     *
     * @param id
     * @return O objeto pacote se ele existir
     * @throws negocio.excecoes.PacoteInexistenteException lanca mensagem
     * de que o pacote nao existe
     */
    public Pacote buscar(int id) throws PacoteInexistenteException {
        String sql = "SELECT * \n" +
                    "FROM projetoloja.pacote NATURAL JOIN projetoloja.produto \n" +
                    "WHERE IdProduto=(IdProduto=(1))";
        
        Pacote pacote = null;
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int idProduto = rs.getInt("IdProduto");
                String nome = rs.getString("Nome");
                Double preco = rs.getDouble("Preco");
                String tamanho = rs.getString("Tamanho");
                String marca = rs.getString("Marca");
                String categoria = rs.getString("Categoria");
                int quantidade = rs.getInt("Quantidade");
                
                pacote = new Pacote(new Produto(idProduto, nome, preco,
                                        tamanho, marca, categoria), quantidade);
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); //System.out.println("ERRO: " + e.getMessage());
        }

        if(pacote == null) throw new PacoteInexistenteException();
        else return pacote;
    }

    /**
     *
     * @param pacote verifica se existe o objeto pacote no repositorio.
     * @return true se ele existir, caso não, false.
     */
    @Override
    public boolean verificarExistencia(Pacote pacote) {
         String sql = "SELECT * \n" +
                    "FROM pacote NATURAL JOIN produto \n" +
                    "WHERE IdProduto=(IdProduto=(1))";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, pacote.getProduto().getId());
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                int idProduto = rs.getInt("IdProduto");
                String nome = rs.getString("Nome");
                Double preco = rs.getDouble("Preco");
                String tamanho = rs.getString("Tamanho");
                String marca = rs.getString("Marca");
                String categoria = rs.getString("Categoria");
                int quantidade = rs.getInt("Quantidade");
                
                return true;
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); //System.out.println("ERRO: " + e.getMessage());
        }
        
        return false;
    }

    /**
     *
     * @return Array com todos os pacotes
     */
    public ArrayList<Pacote> getPacotes() {
        String sql = "SELECT * \n" +
                    "FROM pacote NATURAL JOIN produto";
        ArrayList<Pacote> lista = new ArrayList<Pacote>();
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                int idProduto = rs.getInt("IdProduto");
                String nome = rs.getString("Nome");
                Double preco = rs.getDouble("Preco");
                String tamanho = rs.getString("Tamanho");
                String marca = rs.getString("Marca");
                String categoria = rs.getString("Categoria");
                int quantidade = rs.getInt("Quantidade");

                lista.add(new Pacote(new Produto(idProduto, nome, preco,
                                        tamanho, marca, categoria), quantidade));
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); //System.out.println("ERRO: " + e.getMessage());
        }
        
        return lista;
    }
}
