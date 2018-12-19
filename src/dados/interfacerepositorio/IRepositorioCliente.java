/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.interfacerepositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import negocio.entidades.Cliente;
import negocio.excecoes.ClienteInexistenteException;


/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public interface IRepositorioCliente {
    /**
     * 
     * @param cliente Adiciona um objeto cliente no repositorio 
     */
    public void adicionar(Cliente cliente);

    public void remover(Cliente cliente);

    public void atualizar(Cliente cliente) throws ClienteInexistenteException;

    public Cliente buscar(String cpf) throws ClienteInexistenteException;

    public boolean verificarExistencia(Cliente cliente);

    public ArrayList<Cliente> getClientes();

}
