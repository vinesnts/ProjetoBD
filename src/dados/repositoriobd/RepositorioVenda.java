package dados.repositoriobd;

import connection.ConexaoMySql;
import dados.interfacerepositorio.IRepositorioPacote;
import dados.interfacerepositorio.IRepositorioVenda;
import negocio.entidades.Venda;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import negocio.entidades.Cliente;
import negocio.entidades.Funcionario;
import negocio.entidades.Pacote;
import negocio.excecoes.VendaInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioVenda implements IRepositorioVenda {

    private static RepositorioVenda instancia;

    public static RepositorioVenda getInstance() {
        if (instancia == null) {
            instancia = new RepositorioVenda();
        }
        return instancia;
    }

    private RepositorioVenda() {
        String createTableVenda = "CREATE TABLE IF NOT EXISTS `venda` (\n"
                + "  `IdVenda` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `IdCliente` varchar(14) NOT NULL,\n"
                + "  `IdFuncionario` varchar(14) NOT NULL,\n"
                + "  `Data` datetime NOT NULL,\n"
                + "  `PrecoTotal` double NOT NULL,\n"
                + "  `Desconto` double NOT NULL DEFAULT 0,\n"
                + "  PRIMARY KEY (`IdVenda`),\n"
                + "  KEY `IdCliente` (`IdCliente`),\n"
                + "  KEY `IdFuncionario` (`IdFuncionario`),\n"
                + "  CONSTRAINT `IdCliente` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`CPF`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n"
                + "  CONSTRAINT `IdFuncionario` FOREIGN KEY (`IdFuncionario`) REFERENCES `funcionario` (`CPF`) ON DELETE NO ACTION ON UPDATE NO ACTION)\n";
        String createViewVenda = "CREATE VIEW IF NOT EXISTS `venda_view` AS SELECT IdVenda, Data, PrecoTotal, Desconto,\n"
                + "IdCliente, c.Nome AS NomeCliente, \n"
                + "IdFuncionario, f.Nome AS NomeFuncionario\n"
                + "FROM venda v, cliente c, funcionario f\n"
                + "WHERE (v.IdCliente=c.CPF AND v.IdFuncionario=f.CPF)\n"
                + "ORDER BY v.IdVenda DESC";
        String createTableVendaPacote = "CREATE TABLE IF NOT EXISTS `venda_pacote` (\n"
                + "  `IdVendaPacote` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `IdVenda` int(11) NOT NULL,\n"
                + "  `IdPacote` int(11) DEFAULT NULL,\n"
                + "  PRIMARY KEY (`IdVendaPacote`),\n"
                + "  KEY `IdVenda` (`IdVenda`),\n"
                + "  KEY `IdPacote` (`IdPacote`),\n"
                + "  CONSTRAINT `IdPacote` FOREIGN KEY (`IdPacote`) REFERENCES `pacote` (`IdPacote`) ON DELETE SET NULL ON UPDATE SET NULL,\n"
                + "  CONSTRAINT `IdVenda` FOREIGN KEY (`IdVenda`) REFERENCES `venda` (`IdVenda`) ON DELETE CASCADE ON UPDATE CASCADE)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            
            PreparedStatement pst = conexao.prepareStatement(createTableVenda);
            pst.execute();
            pst = conexao.prepareStatement(createViewVenda);
            pst.execute();
            pst = conexao.prepareStatement(createTableVendaPacote);
            pst.execute();
            
            pst.close();
            conexao.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param venda Adiciona o objeto venda no repositorio de vendas
     */
    @Override
    public void adicionar(Venda venda) {
        String insereVenda = "INSERT INTO venda(IdCliente, IdFuncionario, Data, PrecoTotal, Desconto) VALUES (\n"
                + "(SELECT CPF FROM cliente WHERE CPF=(?)),\n"
                + "(SELECT CPF FROM funcionario WHERE CPF=(?)),?,?,?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(insereVenda, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, venda.getCliente().getCpf());
            pst.setString(2, venda.getFuncionario().getCpf());
            pst.setString(3, venda.getData().toString());
            pst.setDouble(4, venda.getPrecoTotal());
            pst.setDouble(5, venda.getDesconto());

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            
            if(rs.next()) this.adicionarVendaPacote(venda.getArrayVendaProduto(), rs.getInt(1));
            
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }


    private void adicionarVendaPacote(ArrayList<Pacote> pacotes, int idVenda) {
        IRepositorioPacote pacotebd = RepositorioPacote.getInstance();
        String sql = "INSERT INTO venda_pacote(IdVenda, IdPacote) VALUES(\n"
                + "(SELECT IdVenda FROM venda WHERE IdVenda=(?)),?)";
        
        for (int i = 0; i < pacotes.size(); i++) {
            Pacote pacote = pacotes.get(i);
            int idPacote = pacotebd.adicionar(pacote);

            try {
                Connection conexao = ConexaoMySql.getConnection();
                PreparedStatement pst = conexao.prepareStatement(sql);

                pst.setInt(1, idVenda);
                pst.setInt(2, idPacote);

                pst.execute();
                pst.close();
                conexao.close();
                
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }

    }

    /**
     *
     * @param venda Remove o objeto venda do repositorio de vendas
     */
    @Override
    public void remover(Venda venda) {
        String sql = "DELETE FROM venda WHERE IdVenda=(?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setInt(1, venda.getId());

            pst.execute();
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    /**
     *
     * @param venda Atualiza uma venda ja cadastrada com os novos dados passados
     * no parametro
     */
    @Override
    public void atualizar(Venda venda) {
        String sql = "UPDATE venda SET PrecoTotal=(?), Desconto=(?)\n"
                + "WHERE IdVenda=(?)";
        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setDouble(1, venda.getPrecoTotal());
            pst.setDouble(2, venda.getDesconto());

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
     * @return O objeto Venda caso ele exista, caso não lança exceção
     * @throws VendaInexistenteException
     */
    @Override
    public Venda buscar(int id) throws VendaInexistenteException {
        IRepositorioPacote pacotebd = RepositorioPacote.getInstance();
        String sql = "SELECT * FROM venda_view\n" +
                    "WHERE IdVenda=(1)";

        Venda venda = null;

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                String data = rs.getString("Data");
                String cpfCliente = rs.getString("IdCliente");
                String nomeCliente = rs.getString("cliente.Nome");
                String aniversario = rs.getString("DataAniversario");
                String cpfFuncionario = rs.getString("IdFuncionario");
                String nomeFuncionario = rs.getString("funcionario.Nome");
                Double precoTotal = rs.getDouble("PrecoTotal");
                Double desconto = rs.getDouble("Desconto");

                Cliente c = new Cliente(nomeCliente, cpfCliente, LocalDate.parse(aniversario));
                Funcionario f = new Funcionario(nomeFuncionario, cpfFuncionario);
                venda = new Venda(idVenda, LocalDateTime.parse(data), c, f, desconto);
                venda.setPrecoTotal(precoTotal);

                venda.setArrayVendaProduto(pacotebd.getPacotes(idVenda));
            }
            
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        if (venda == null) {
            throw new VendaInexistenteException();
        } else {
            return venda;
        }
    }

    @Override
    public boolean verificarExistencia(Venda venda) {
        IRepositorioPacote pacotebd = RepositorioPacote.getInstance();
        String sql = "SELECT IdVenda FROM venda_view\n"
                + "WHERE IdVenda=(?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setInt(1, venda.getId());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                if (idVenda != 0) {
                    return true;
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return false;
    }

    /**
     *
     * @return O Array de vendas cadastradas no repositorio
     */
    @Override
    public ArrayList<Venda> getVendas() {
        IRepositorioPacote pacotebd = RepositorioPacote.getInstance();
        ArrayList<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda_view";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                Date data = rs.getDate("Data");
                Time hora = rs.getTime("Data");
                String cpfCliente = rs.getString("IdCliente");
                String nomeCliente = rs.getString("NomeCliente");
                String cpfFuncionario = rs.getString("IdFuncionario");
                String nomeFuncionario = rs.getString("NomeFuncionario");
                Double precoTotal = rs.getDouble("PrecoTotal");
                Double desconto = rs.getDouble("Desconto");

                Cliente c = new Cliente(nomeCliente, cpfCliente);
                Funcionario f = new Funcionario(nomeFuncionario, cpfFuncionario);
                
                lista.add(new Venda(idVenda, LocalDateTime.of(data.toLocalDate(),
                                hora.toLocalTime()), c, f, precoTotal, desconto));
            }
            
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public ArrayList<Venda> getVendasAno(int ano) {
        IRepositorioPacote pacotebd = RepositorioPacote.getInstance();
        ArrayList<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda_view\n"
                + "WHERE year(Data)=(?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, ano);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                Date dataAux = rs.getDate("Data");
                Time hora = rs.getTime("Data");
                String cpfCliente = rs.getString("IdCliente");
                String nomeCliente = rs.getString("NomeCliente");
                String cpfFuncionario = rs.getString("IdFuncionario");
                String nomeFuncionario = rs.getString("NomeFuncionario");
                Double precoTotal = rs.getDouble("PrecoTotal");
                Double desconto = rs.getDouble("Desconto");

                Cliente c = new Cliente(nomeCliente, cpfCliente);
                Funcionario f = new Funcionario(nomeFuncionario, cpfFuncionario);
                
                lista.add(new Venda(idVenda, LocalDateTime.of(dataAux.toLocalDate(),
                                hora.toLocalTime()), c, f, precoTotal, desconto));
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
     * @param cpf
     * @param ano
     * @return o valor total das vendas do funcionário passado como parametro
     */
    @Override
    public ArrayList<Venda> getVendasFuncionario(String cpf, int ano) {
        IRepositorioPacote pacotebd = RepositorioPacote.getInstance();
        ArrayList<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda_view\n"
                + "WHERE year(Data)=(?)\n"
                + "AND IdFuncionario=(?)";

        try {
            Connection conexao = ConexaoMySql.getConnection();
            PreparedStatement pst = conexao.prepareStatement(sql);
            
            pst.setInt(1, ano);
            pst.setString(2, cpf);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                Date dataAux = rs.getDate("Data");
                Time hora = rs.getTime("Data");
                String cpfCliente = rs.getString("IdCliente");
                String nomeCliente = rs.getString("NomeCliente");
                String cpfFuncionario = rs.getString("IdFuncionario");
                String nomeFuncionario = rs.getString("NomeFuncionario");
                Double precoTotal = rs.getDouble("PrecoTotal");
                Double desconto = rs.getDouble("Desconto");

                Cliente c = new Cliente(nomeCliente, cpfCliente);
                Funcionario f = new Funcionario(nomeFuncionario, cpfFuncionario);
                
                lista.add(new Venda(idVenda, LocalDateTime.of(dataAux.toLocalDate(),
                                hora.toLocalTime()), c, f, precoTotal, desconto));
            }
            
            pst.close();
            conexao.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return lista;
    }
}
