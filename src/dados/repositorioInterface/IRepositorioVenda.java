/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados.repositorioInterface;

import negocio.entidades.Venda;
import negocio.excecoes.VendaInexistenteException;

/**
 *
 * @author Raquell Vieira, Adilson Junior
 */
public interface IRepositorioVenda {

    public void adicionar(Venda venda);

    public void remover(Venda venda);

    public void atualizar(Venda venda);

    public Venda buscar(int id)throws VendaInexistenteException;

    public boolean verificarExistencia(Venda venda);
}
