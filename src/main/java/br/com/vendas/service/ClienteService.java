package br.com.vendas.service;

import br.com.vendas.dao.ClienteDao;
import br.com.vendas.dao.GenericDao;
import br.com.vendas.model.Cliente;
import br.com.vendas.model.Usuario;
import br.com.vendas.util.NegocioException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@ApplicationScoped
public class ClienteService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<Cliente> daoGenerico;

    @Inject
    private ClienteDao clienteDao;


    public void salvar(Cliente cliente){
        daoGenerico.salvar(cliente);
    }

    public void remover(Cliente clienteParaExcluir, Usuario usuarioLogado) throws NegocioException {
        if(!usuarioLogado.isAdmin()){
            throw new NegocioException("Apenas administradores podem excluir clientes!");
        }

        if(clienteDao.existePedidoParaCliente(clienteParaExcluir)){
            throw new NegocioException("Cliente possui pedidos vinculados, não pode ser excluído!");
        }

        daoGenerico.remover(Cliente.class, clienteParaExcluir.getId());
    }

    public List<Cliente> buscarTodos(){
        return daoGenerico.buscarTodos(Cliente.class, "SELECT c FROM Cliente c ORDER BY c.id DESC");
    }

    public List<Cliente> buscarPorNome(String nome) throws NegocioException {

        List<Cliente> clientes = clienteDao.buscarPorNome(nome);

        if(clientes.isEmpty()){
            throw new NegocioException("Nenhum cliente encontrado com este nome!");
        }

        return clientes;
    }
}
