package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.dao.UsuarioDao;
import br.com.vendas.model.Usuario;
import br.com.vendas.util.NegocioException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UsuarioService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<Usuario> daoGenerico;

    @Inject
    private UsuarioDao usuarioDao;

    public void salvar(Usuario usuario, String confirmarSenha) throws NegocioException {

        validarCamposObrigatorios(usuario);
        validarSenhaConfirmacao(usuario.getSenha(), confirmarSenha);
        validarLoginExistente(usuario);
        daoGenerico.salvar(usuario);
    }

    public void atualizar(Usuario usuario, String novaSenha,String confirmarSenha) throws NegocioException {

        if (novaSenha != null && !novaSenha.isBlank()){
            validarSenhaConfirmacao(novaSenha, confirmarSenha);
            usuario.setSenha(novaSenha);
        } else {
            Usuario usuarioBanco = usuarioDao.buscarPorId(usuario.getId());
            usuario.setSenha(usuarioBanco.getSenha());
        }
        usuarioDao.atualizar(usuario);
    }

    private void validarCamposObrigatorios(Usuario usuario) throws NegocioException {

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()
                || usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()
                || usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new NegocioException("Todos os campos são de preenchimento obrigatório!");
        }
    }

    private void validarLoginExistente(Usuario usuario) throws NegocioException {

        // Criação de usuário (novo usuário)
        if (usuario.getId() == null) {
            if (usuarioDao.existeLogin(usuario.getLogin())) {
                throw new NegocioException("Usuário já cadastrado!");
            }
            return;
        }

        // Edição de usuário
        Usuario usuarioComMesmoLogin = usuarioDao.buscarPorLogin(usuario.getLogin());

        if (usuarioComMesmoLogin != null && !usuarioComMesmoLogin.equals(usuario)) {
            throw new NegocioException("Usuário já cadastrado!");
        }
    }

    private void validarSenhaConfirmacao(String senha, String confirmarSenha) throws NegocioException {
        if (senha == null || !senha.equals(confirmarSenha)) {
            throw new NegocioException("As senhas não conferem. Verifique e tente novamente.");
        }
    }

    public Usuario logar(String login, String senha) throws NegocioException {
        Usuario usuario = usuarioDao.buscarPorLogin(login);

        if (usuario == null || !usuario.getSenha().equals(senha)) {
            throw new NegocioException("Usuário ou senha inválidos!");
        }

        return usuario;
    }


    public void remover(Usuario usuarioParaExcluir, Usuario usuarioLogado) throws NegocioException {
        if (!usuarioLogado.isAdmin()) {
            throw new NegocioException("Somente administradores podem excluir usuários!");
        }

        daoGenerico.remover(Usuario.class, usuarioParaExcluir.getId());
    }

    public List<Usuario> buscarTodosUsuarios() {
        return daoGenerico.buscarTodos
                (Usuario.class, "SELECT u FROM Usuario u ORDER BY u.id DESC");
    }
}
