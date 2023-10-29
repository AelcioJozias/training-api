package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTODisassembler;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.dto.input.UsuarioAtualizarInputDTO;
import com.algaworks.algafood.api.dto.input.UsuarioAtualizarSenhaInputDTO;
import com.algaworks.algafood.api.dto.input.UsuarioCadastroInputDTO;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class CadastroUsuarioService {

    public static final String SENHA_ATUAL_INFORMADA_NAO_COINCIDE_COM_A_SENHA_DO_USUARIO = "Senha Atual informada não coincide com a senha do usuário";

    @Autowired
    UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    UsuarioDTODisassembler usuarioDisassembler;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Transactional
    public Usuario salvar(UsuarioCadastroInputDTO usuarioCadastroInputDTO) {
        return usuarioRepository.save(usuarioDisassembler.toModelObject(usuarioCadastroInputDTO));
    }

    @Transactional
    public Usuario atualizar(Long id, UsuarioAtualizarInputDTO usuarioAtualizarInputDTO) {
        Usuario usuarioAtual = buscarOuFalhar(id);
        usuarioDisassembler.copyDTOToModel(usuarioAtualizarInputDTO, usuarioAtual);
        return usuarioRepository.save(usuarioAtual);
    }

    public Usuario buscarPorId(Long id) {
        return (buscarOuFalhar(id));
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public void excluir(Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        }
    }

    @Transactional
    public void alterarSenha(Long id, UsuarioAtualizarSenhaInputDTO usuarioAtualizarSenhaInputDTO) {
        Usuario usuarioAtual = buscarOuFalhar(id);
        validaSeUsuarioForneceuAsenhaAtualCorreta(usuarioAtualizarSenhaInputDTO, usuarioAtual);
        usuarioAtual.setSenha(usuarioAtualizarSenhaInputDTO.getNovaSenha());
    }

    private static void validaSeUsuarioForneceuAsenhaAtualCorreta(
            UsuarioAtualizarSenhaInputDTO usuarioAtualizarSenhaInputDTO, Usuario usuarioAtual) {

        if(senhaAtualFornecidaNaoCoincidirComOqueEstaGravadoNoBanco(usuarioAtualizarSenhaInputDTO, usuarioAtual)){
            throw new NegocioException(SENHA_ATUAL_INFORMADA_NAO_COINCIDE_COM_A_SENHA_DO_USUARIO);
        }

    }

    private static boolean senhaAtualFornecidaNaoCoincidirComOqueEstaGravadoNoBanco(
            UsuarioAtualizarSenhaInputDTO usuarioAtualizarSenhaInputDTO, Usuario usuarioAtual) {
        return !usuarioAtual.getSenha().equals(usuarioAtualizarSenhaInputDTO.getSenhaAtual());
    }


}
