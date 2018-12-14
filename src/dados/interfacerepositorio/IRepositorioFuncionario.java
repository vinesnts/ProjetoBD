/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.interfacerepositorio;

import negocio.entidades.Funcionario;
import java.util.ArrayList;
import negocio.excecoes.FuncionarioInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public interface IRepositorioFuncionario {
     public void adicionar(Funcionario funcionario);

    public void remover(Funcionario funcionario);

    public void atualizar(Funcionario funcionario) throws FuncionarioInexistenteException;

    public Funcionario buscar(String matricula)throws FuncionarioInexistenteException;

    public boolean verificarExistencia(Funcionario funcionario);
    
    public ArrayList<Funcionario> getFuncionarios();
}
