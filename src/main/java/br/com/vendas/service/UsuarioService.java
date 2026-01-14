package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.dao.UsuarioDao;
import br.com.vendas.model.Usuario;
import br.com.vendas.util.NegocioException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
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

    public void atualizarUsuario(Usuario usuario) throws NegocioException {
        validarLoginExistente(usuario);
        usuarioDao.atualizar(usuario);
    }

    public void atualizarSenha(Usuario usuario, String novaSenha, String confirmarSenha) throws NegocioException {
        validarSenha(novaSenha);
        usuario.setSenha(novaSenha);
        validarSenhaConfirmacao(novaSenha, confirmarSenha);
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

    public void validarSenha(String novaSenha) throws NegocioException {
        if(novaSenha == null || novaSenha.trim().isEmpty()){
            throw new NegocioException("Nova senha é obrigatória!");
        }

        if(novaSenha.length() < 5 || novaSenha.length() > 20){
            throw new NegocioException("Nova senha deve ter entre 5 a 20 caracteres");
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

        if (usuarioParaExcluir.equals(usuarioLogado)){
            throw new NegocioException("Não é permitido excluir seu próprio perfil de usuário!");
        }

        daoGenerico.remover(Usuario.class, usuarioParaExcluir.getId());
    }

    public List<Usuario> buscarTodosUsuarios() {
        return daoGenerico.buscarTodos
                (Usuario.class, "SELECT u FROM Usuario u ORDER BY u.id DESC");
    }

    public void alterarPermissaoAdmin(Usuario usuarioParaAlterar, Usuario usuarioLogado, boolean admin) throws NegocioException{

        if(!usuarioLogado.isAdmin()){
            throw new NegocioException("Somente administradores podem tornar usuários administradores!");
        }

        if (usuarioParaAlterar.equals(usuarioLogado)){
            throw new NegocioException("Não é permitido alterar seu próprio perfil de administrador!");
        }

        usuarioDao.alterarPermissaoAdmin(usuarioParaAlterar, admin);
    }

    public String resetarSenha(Usuario usuarioParaResetar, Usuario usuarioLogado) throws NegocioException {
        if (!usuarioLogado.isAdmin()){
            throw new NegocioException("Somente administradores podem resetar senhas");
        }

        if (usuarioParaResetar.getId().equals(usuarioLogado.getId())){
            throw new NegocioException("Não é possível resetar a própria senha!");
        }

        String novaSenha = gerarSenhaAleatoria();
        usuarioParaResetar.setSenha(novaSenha);
        usuarioDao.atualizar(usuarioParaResetar);

        return novaSenha;
    }

    private String gerarSenhaAleatoria(){
        String caracteres = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789@#";
        SecureRandom random = new SecureRandom();

        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < 8; i++){
            senha.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return senha.toString();
    }
}
