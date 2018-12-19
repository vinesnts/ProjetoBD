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
import java.sql.Statement;
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
        String createTable = "CREATE TABLE IF NOT EXISTS `pacote` (\n" +
                    "  `IdPacote` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `IdProduto` int(11) DEFAULT NULL,\n" +
                    "  `Quantidade` int(11) NOT NULL,\n" +
                    "  PRIMARY KEY (`IdPacote`),\n" +
                    "  KEY `IdProduto` (`IdProduto`),\n" +
                    "  CONSTRAINT `IdProduto` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`IdProduto`) ON DELETE SET NULL ON UPDATE SET NULL)\n";
        String createView = "CREATE VIEW IF NOT EXISTS pacote_view AS SELECT *\n" +
                    "FROM pacote \n" +
                    "NATURAL JOIN venda_pacote\n" +
                    "NATURAL JOIN produto";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            
            PreparedStatement pst = conexao.prepareStatement(createTable);
            pst.execute();
            pst = conexao.prepareStatement(createView);
            pst.execute();
            
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param pacote pacote a ser adicionado
     * @return retorna a id do pacote adicionado
     *
     */
    @Override
    public int adicionar(Pacote pacote) {
        int ultimoId = 0;
        String sql = "INSERT INTO pacote(IdProduto, Quantidade) VALUES (\n"
                + "(SELECT IdProduto FROM produto WHERE IdProduto=(?)),?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, pacote.getProduto().getId());
            pst.setInt(2, pacote.getQuantidade());

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            
            if(rs.next()) ultimoId = rs.getInt(1);
            
            pst.close();
            conexao.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        
        return ultimoId;
    }

    /**
     *
     * @param pacote pacote a ser removido
     *
     */
    @Override
    public void remover(Pacote pacote) {
        String sql = "DELETE FROM pacote WHERE IdProduto=(\n"
                + "(SELECT IdProduto FROM produto WHERE IdProduto=(?)))";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setInt(1, pacote.getProduto().getId());

            pst.execute();
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param pacote Verifica a posicao do produto e sobrescreve a sua
     * quantidade
     */
    @Override
    public void atualizar(Pacote pacote) {
        String sql = "UPDATE pacote SET Quantidade=(?) WHERE IdProduto=("
                + "(SELECT IdProduto FROM produto WHERE IdProduto=(?)))";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setInt(1, pacote.getQuantidade());
            pst.setInt(2, pacote.getProduto().getId());

            pst.executeUpdate();
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        
    }

    /**
     *
     * @param id
     * @return O objeto pacote se ele existir
     * @throws negocio.excecoes.PacoteInexistenteException lanca mensagem de que
     * o pacote nao existe
     */
    public Pacote buscar(int id) throws PacoteInexistenteException {
        String sql = "SELECT * \n"
                + "FROM pacote_view\n"
                + "WHERE IdProduto=(?)";

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
            System.out.println("ERRO: " + e.getMessage());
        }
        
        if (pacote == null) {
            throw new PacoteInexistenteException();
        } else {
            return pacote;
        }
    }

    /**
     *
     * @param pacote verifica se existe o objeto pacote no repositorio.
     * @return true se ele existir, caso não, false.
     */
    @Override
    public boolean verificarExistencia(Pacote pacote) {
        String sql = "SELECT IdProduto \n"
                + "FROM pacote_view \n"
                + "WHERE IdProduto=(IdProduto=(?))";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setInt(1, pacote.getProduto().getId());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int idProduto = rs.getInt("IdProduto");
                if(idProduto != 0) return true;
            }

            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return false;
    }

    /**
     *
     * @return Array com todos os pacotes
     */
    public ArrayList<Pacote> getPacotes() {
        String sql = "SELECT * \n"
                + "FROM pacote_view";
        ArrayList<Pacote> lista = new ArrayList<>();

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
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
            System.out.println("ERRO: " + e.getMessage());
        }

        return lista;
    }
    
    @Override
    public ArrayList<Pacote> getPacotes(int idVenda) {
        String sql = "SELECT * \n"
                + "FROM pacote_view"
                + "WHERE IdVenda=(?)";
        ArrayList<Pacote> lista = new ArrayList<Pacote>();

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, idVenda);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
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
            System.out.println("ERRO: " + e.getMessage());
        }

        return lista;
    }
}
