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

    public void salvar(Cliente cliente) throws NegocioException {
        validarDocumento(cliente.getDocumento());
        validarCliente(cliente);
        normalizarCliente(cliente);
        daoGenerico.salvar(cliente);
    }

    public void atualizar(Cliente cliente) throws NegocioException {
        Cliente clienteBanco = clienteDao.buscarPorId(cliente.getId());

        if (clienteBanco == null) {
            throw new NegocioException("Cliente não encontrado!");
        }

        //Documento do cliente não pode ser alterado
        cliente.setDocumento(clienteBanco.getDocumento());
        normalizarCliente(cliente);
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

    public List<Cliente> buscarTodosClientes(){
        return daoGenerico.buscarTodos(Cliente.class, "SELECT c FROM Cliente c ORDER BY c.id DESC");
    }

    public void validarCliente(Cliente cliente) throws NegocioException{

        if (clienteDao.existeClienteComDocumento(cliente.getDocumento())){
            throw new NegocioException("Já existe um cliente cadastrado com este documento.");
        }
    }

    private void validarDocumento(String documento) throws NegocioException{

        String somenteNumeros = documento.replaceAll("\\D", "");

        if (somenteNumeros.length() != 11 && somenteNumeros.length() != 14) {
            throw new NegocioException("Documento inválido! Informe um CPF ou CNPJ válido");
        }
    }

    public void normalizarCliente(Cliente cliente){
        cliente.setNome(cliente.getNome().trim());
        cliente.setLogradouro(cliente.getLogradouro().trim());
        cliente.setNumero(cliente.getNumero().trim());
        cliente.setBairro(cliente.getBairro().trim());
        cliente.setCep(cliente.getCep().trim());
        cliente.setCidade(cliente.getCidade().trim());
        cliente.setCidade(cliente.getCidade().toUpperCase());
        cliente.setEstado(cliente.getEstado().trim());
        cliente.setEstado(cliente.getEstado().toUpperCase());
        cliente.setComplemento(cliente.getComplemento().trim());
    }

    public List<Cliente> buscarPorNomeOuDocumento(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()){
            return List.of();
        }

        return clienteDao.buscarPorNomeOuDocumento(filtro);
    }

    public Cliente buscarPorId(Long id){
        return clienteDao.buscarPorId(id);
    }
}
