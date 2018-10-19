package dados.repositorioArrayList;

import dados.repositorioInterface.IRepositorioFuncionario;
import negocio.entidades.Funcionario;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public class RepositorioFuncionario implements IRepositorioFuncionario {

    private ArrayList<Funcionario> funcionarios;
    FileOutputStream arquivoParaGravar;
    FileInputStream recuperadorDeArquivos;
    ObjectOutputStream escritor;
    ObjectInputStream leitor;

    public RepositorioFuncionario() {
        this.funcionarios = new ArrayList();
    }

    /**
     *
     * @param funcionario Adiciona o objeto funcionario no repositorio de
     * funcionarios
     */
    @Override
    public void adicionar(Funcionario funcionario) {
        this.funcionarios.add(funcionario);

    }

    /**
     *
     * @param funcionario Remove o objeto funcionario do repositorio de
     * funcionario
     */
    @Override
    public void remover(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
    }

    /**
     *
     * @param funcionario Atualiza os dados do funcionario ja existente com os
     * novos dados passados.
     */
    @Override
    public void atualizar(Funcionario funcionario) {
        Funcionario f = (Funcionario) funcionario;
        for (int i = 0; i < funcionarios.size(); i++) {
            if (this.funcionarios.get(i).getMatricula().equals(f.getMatricula())) {
                this.funcionarios.get(i).setNome(f.getNome());
                this.funcionarios.get(i).setCpf(f.getCpf());
                this.funcionarios.get(i).setSenha(f.getSenha());
                this.funcionarios.get(i).setEGerente(f.eGerente());
            }
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
        Funcionario funcionario;
        for (int i = 0; i < this.funcionarios.size(); i++) {
            if (this.funcionarios.get(i).getMatricula().equals(matricula)) {
                return funcionario = this.funcionarios.get(i);
            }
        }

        throw new FuncionarioInexistenteException();

    }

    /**
     *
     * @param funcionario
     * @return Verifica se o o funcionario ja exixte, se sim retorna true, caso
     * não retorna false.
     */
    @Override
    public boolean verificarExistencia(Funcionario funcionario) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getMatricula().equals(funcionario.getMatricula())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return Retorna a lista com todos os funcionarios cadastrados.
     */
    @Override
    public ArrayList<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    /**
     *
     * @return Retorna o Array com os funcionarios que são vendedores
     */
    public ArrayList<Funcionario> getVendedores() {
        ArrayList<Funcionario> vendedores = new ArrayList();
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
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).eGerente()) {
                gerentes.add(funcionarios.get(i));
            }
        }
        return gerentes;
    }

    /**
     * Metodo para gravar o Array de Funcionarios em um arquivo binario
     *
     */
    public void gravar() {

        try {
            this.arquivoParaGravar = new FileOutputStream("Dados/ArquivoFuncionarios.dat");
            this.escritor = new ObjectOutputStream(arquivoParaGravar);
            this.escritor.writeObject(funcionarios);
            this.escritor.flush();
            this.escritor.close();
            this.arquivoParaGravar.flush();
            this.arquivoParaGravar.close();

        } catch (IOException erro) {
            erro.printStackTrace();
        }

    }

    /**
     * Metodo para recuperar o Array de funcionarios previamente cadastrados e
     * carrega no repositorio de funcionarios
     */
    public void ler() {

        try {
            recuperadorDeArquivos = new FileInputStream("Dados/ArquivoFuncionarios.dat");
            this.leitor = new ObjectInputStream(recuperadorDeArquivos);
            funcionarios = (ArrayList<Funcionario>) leitor.readObject();
            this.leitor.close();
            recuperadorDeArquivos.close();

        } catch (IOException | ClassNotFoundException erro) {
        	System.out.println("Nada nos repositorios!");
        }
    }
}
