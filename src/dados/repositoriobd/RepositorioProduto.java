
package dados.repositoriobd;

import connection.ConexaoMySql;
import dados.interfacerepositorio.IRepositorioProduto;
import negocio.entidades.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import negocio.excecoes.ProdutoInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioProduto implements IRepositorioProduto {
    
    private static RepositorioProduto instancia;

    public static RepositorioProduto getInstance() {
        if(instancia == null) {
            instancia = new RepositorioProduto();
        }
        return instancia;
    }
    
    public RepositorioProduto() {
    String sql = "CREATE TABLE IF NOT EXISTS `produto` (\n" +
                "  `IdProduto` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `Nome` varchar(45) NOT NULL,\n" +
                "  `Preco` double NOT NULL,\n" +
                "  `Tamanho` varchar(10) NOT NULL,\n" +
                "  `Marca` varchar(20) NOT NULL,\n" +
                "  `Categoria` varchar(20) NOT NULL,\n" +
                "  PRIMARY KEY (`IdProduto`))";

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

    /**
     *
     * @param produto Adiciona o objeto produto no repositorio
     */
    @Override
    public void adicionar(Produto produto) {
        String sql = "INSERT INTO produto(Nome, Preco, Tamanho, Marca, Categoria) VALUES (?,?,?,?,?)";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, produto.getNome());
            pst.setDouble(2, produto.getPreco());
            pst.setString(3, produto.getTamanho());
            pst.setString(4, produto.getMarca());
            pst.setString(5, produto.getCategoria());
            
            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param produto Remove o objeto produto do repositorio
     */
    @Override
    public void remover(Produto produto) {
        String sql = "DELETE FROM produto WHERE IdProduto=(?)";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, produto.getId());
            
            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param produto Atualiza os dados do produto ja existente com os novos
     * dados passados
     */
    @Override
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET Nome=(?), Preco=(?), Tamanho=(?), Marca=(?), Categoria=(?) WHERE IdProduto=(?)";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, produto.getNome());
            pst.setDouble(2, produto.getPreco());
            pst.setString(3, produto.getTamanho());
            pst.setString(4, produto.getMarca());
            pst.setString(5, produto.getCategoria());
            pst.setInt(6, produto.getId());
            
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
     * @return Retorna o objeto Produto.
     * @throws negocio.excecoes.ProdutoInexistenteException
     */
    @Override
    public Produto buscar(int id) throws ProdutoInexistenteException {
        String sql = "SELECT * FROM produto WHERE IdProduto=(?)";
        Produto produto = null;
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int idAux = rs.getInt("IdProduto");
                String nome = rs.getString("Nome");
                Double preco = rs.getDouble("Preco");
                String tamanho = rs.getString("Tamanho");
                String marca = rs.getString("Marca");
                String categoria = rs.getString("Categoria");

                
                produto = new Produto(idAux, nome, preco, tamanho, marca, categoria);
                
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        if(produto == null) throw new ProdutoInexistenteException();
        else return produto;
    }

    /**
     *
     * @param produto
     * @return
     */
    @Override
    public boolean verificarExistencia(Produto produto) {
        String sql = "SELECT * FROM produto WHERE Nome=(?), Tamanho=(?), Marca=(?)";
        Produto p = null;
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getTamanho());
            pst.setString(3, produto.getMarca());
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String nome = rs.getString("Nome");
                Double preco = rs.getDouble("Preco");
                String tamanho = rs.getString("Tamanho");
                String marca = rs.getString("Marca");
                String categoria = rs.getString("Categoria");

                
                p = new Produto(nome, preco, tamanho, marca, categoria);
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return p != null;
    }
    //Retorna o Array de Produtos.

    @Override
    public ArrayList<Produto> getProdutos() {
        String sql = "SELECT * FROM produto";
        ArrayList<Produto> lista = new ArrayList<>();
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int idAux = rs.getInt("IdProduto");
                String nome = rs.getString("Nome");
                Double preco = rs.getDouble("Preco");
                String tamanho = rs.getString("Tamanho");
                String marca = rs.getString("Marca");
                String categoria = rs.getString("Categoria");

                
                lista.add(new Produto(idAux, nome, preco, tamanho, marca, categoria));
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return lista;
    }
}
