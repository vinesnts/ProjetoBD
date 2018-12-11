package dados.repositoriobd;

import connection.ConexaoMySql;
import dados.interfacerepositorio.IRepositorioFuncionario;
import negocio.entidades.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioFuncionario implements IRepositorioFuncionario {

    private static RepositorioFuncionario instancia;

    public static RepositorioFuncionario getInstance() {
        if (instancia == null) {
            instancia = new RepositorioFuncionario();
        }
        return instancia;
    }
    
    private RepositorioFuncionario() {}

    /**
     *
     * @param funcionario Adiciona o objeto funcionario no repositorio de
     * funcionarios
     */
    @Override
    public void adicionar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario VALUES (?,?,?,?,?)";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, funcionario.getMatricula());
            pst.setBoolean(2, funcionario.eGerente());
            pst.setString(3, funcionario.getCpf());
            pst.setString(4, funcionario.getNome());
            pst.setString(5, funcionario.getSenha());
            
            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param funcionario Remove o objeto funcionario do repositorio de
     * funcionario
     */
    @Override
    public void remover(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE Matricula=(?) AND Senha=(?)";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, funcionario.getMatricula());
            pst.setString(2, funcionario.getSenha());
            
            pst.execute();
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param matricula Atualiza os dados do funcionario ja existente com os
     * novos dados passados.
     * @param nome Nome novo do funcionario
     * @param eGerente Cargo novo do funcionario
     * @param senha Senha nova do funcionario
     * @throws negocio.excecoes.FuncionarioInexistenteException Se o funcionario nao existe, lanca erro
     */
    @Override
    public void atualizar(String cpf, String nome, boolean eGerente, String senha) throws FuncionarioInexistenteException {
        String sql = "UPDATE funcionario SET Matricula=(?), eGerente=(?), Nome=(?), Senha=(?) WHERE CPF=(?)";
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, Funcionario.gerarMatricula(eGerente, cpf));
            pst.setBoolean(2, eGerente);
            pst.setString(3, nome);
            pst.setString(4, senha);
            pst.setString(5, cpf);
            
            pst.executeUpdate();
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param matricula
     * @return retorna o objeto funcionario.
     * @throws negocio.excecoes.FuncionarioInexistenteException
     */
    @Override
    public Funcionario buscar(String matricula) throws FuncionarioInexistenteException {
        String sql = "SELECT * FROM funcionario WHERE Matricula=(?)";
        Funcionario funcionario = null;
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, matricula);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String matriculaAux = rs.getString("Matricula");
                Boolean eGerente = rs.getBoolean("eGerente");
                String cpf = rs.getString("CPF");
                String nome = rs.getString("Nome");
                String senha = rs.getString("Senha");
                
                funcionario = new Funcionario(nome, cpf, eGerente, senha);
                
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        if(funcionario == null) throw new FuncionarioInexistenteException();
        else return funcionario;
    }

    /**
     *
     * @param funcionario
     * @return Verifica se o o funcionario ja exixte, se sim retorna true, caso
     * não retorna false.
     */
    @Override
    public boolean verificarExistencia(Funcionario funcionario) {
        String sql = "SELECT * FROM funcionario WHERE Matricula=(?)";
        Funcionario f;
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setString(1, funcionario.getMatricula());
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String matriculaAux = rs.getString("Matricula");
                Boolean eGerente = rs.getBoolean("eGerente");
                String cpf = rs.getString("CPF");
                String nome = rs.getString("Nome");
                String senha = rs.getString("Senha");
                
                f = new Funcionario(nome, cpf, eGerente, senha);
                
                return true;
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
     * @return Retorna a lista com todos os funcionarios cadastrados.
     */
    @Override
    public ArrayList<Funcionario> getFuncionarios() {
        String sql = "SELECT * FROM funcionario";
        ArrayList<Funcionario> lista = new ArrayList<Funcionario>();
        
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String matriculaAux = rs.getString("Matricula");
                Boolean eGerente = rs.getBoolean("eGerente");
                String cpf = rs.getString("CPF");
                String nome = rs.getString("Nome");
                String senha = rs.getString("Senha");
                
                lista.add(new Funcionario(nome, cpf, eGerente, senha));
            }
            
            pst.close();
            conexao.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        
        return lista;
    }

    /**
     *
     * @return Retorna o Array com os funcionarios que são vendedores
     */
    public ArrayList<Funcionario> getVendedores() {
        ArrayList<Funcionario> vendedores = new ArrayList();
        ArrayList<Funcionario> funcionarios = this.getFuncionarios();
        for (int i = 0; i < funcionarios.size(); i++) {
            if (!funcionarios.get(i).eGerente()) {
                vendedores.add(funcionarios.get(i));
            }
        }
        return vendedores;
    }

    /**
     *
     * @return Retorna o Array com os funcionarios que são gerentes.
     */
    public ArrayList<Funcionario> getGerentes() {
        ArrayList<Funcionario> gerentes = new ArrayList();
        ArrayList<Funcionario> funcionarios = this.getFuncionarios();
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).eGerente()) {
                gerentes.add(funcionarios.get(i));
            }
        }
        return gerentes;
    }
}
