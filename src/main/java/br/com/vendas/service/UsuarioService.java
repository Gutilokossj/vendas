package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.dao.UsuarioDao;
import br.com.vendas.model.Usuario;
import br.com.vendas.util.NegocioException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class UsuarioService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<Usuario> daoGenerico;

    @Inject
    private UsuarioDao usuarioDao;

    @Transactional
    public void salvar(Usuario usuario) throws NegocioException {

        validarCamposObrigatorios(usuario);
        validarLoginExistente(usuario);
        daoGenerico.salvar(usuario);
    }

    private void validarCamposObrigatorios(Usuario usuario) throws NegocioException {

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()
                ||usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()
                ||usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()){
            throw new NegocioException("Todos os campos são de preenchimento obrigatório!");
        }
    }

    private void validarLoginExistente(Usuario usuario) throws NegocioException {

        // Criação de usuário (novo usuário)
        if (usuario.getId() == null){
            if (usuarioDao.existeLogin(usuario.getLogin())){
                throw new NegocioException("Usuário já cadastrado!");
            }
            return;
        }

        // Edição de usuário
        Usuario usuarioComMesmoLogin = usuarioDao.buscarPorLogin(usuario.getLogin());

        if (usuarioComMesmoLogin != null && !usuarioComMesmoLogin.equals(usuario)){
            throw new NegocioException("Usuário já cadastrado!");
        }
    }

    public Usuario logar(String login, String senha) throws NegocioException {
        Usuario usuario = usuarioDao.buscarPorLogin(login);

        if (usuario == null || !usuario.getSenha().equals(senha)){
            throw new NegocioException("Usuário ou senha inválidos!");
        }

        return usuario;
    }

   @Transactional
   public void remover(Usuario usuarioParaExcluir, Usuario usuarioLogado) throws NegocioException {
        if (!usuarioLogado.isAdmin()){
            throw new NegocioException("Somente administradores podem excluir usuários!");
        }

        daoGenerico.remover(Usuario.class, usuarioParaExcluir.getId());
   }

    public List<Usuario> buscarTodosUsuarios(){
        return daoGenerico.buscarTodos
                (Usuario.class, "SELECT u FROM Usuario u ORDER BY u.id DESC");
    }
}
