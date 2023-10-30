package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTODisassembler;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.dto.input.UsuarioAtualizarInputDTO;
import com.algaworks.algafood.api.dto.input.UsuarioAtualizarSenhaInputDTO;
import com.algaworks.algafood.api.dto.input.UsuarioCadastroInputDTO;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    UsuarioDTODisassembler usuarioDisassembler;


    @GetMapping(value = "/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        return usuarioDTOAssembler.toDTO(cadastroUsuarioService.buscarPorId(id));
    }

    @GetMapping
    public List<UsuarioDTO> buscarTodos() {
        return usuarioDTOAssembler.toCollectionDTO(cadastroUsuarioService.buscarTodos());
    }

    @PostMapping
    public UsuarioDTO salvar(@Valid @RequestBody UsuarioCadastroInputDTO usuarioCadastroInputDTO) {
        return usuarioDTOAssembler.toDTO(cadastroUsuarioService.salvar(usuarioCadastroInputDTO));
    }

    @PutMapping(value = "/{id}")
    public UsuarioDTO atualizar( @PathVariable Long id, @Valid @RequestBody UsuarioAtualizarInputDTO usuarioAtualizarInputDTO) {
        return usuarioDTOAssembler.toDTO(cadastroUsuarioService.atualizar(id, usuarioAtualizarInputDTO));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}/senha")
    public void atualizarSenha(@PathVariable Long id, @Valid @RequestBody UsuarioAtualizarSenhaInputDTO usuarioAtualizarSenhaInputDTO) {
        cadastroUsuarioService.alterarSenha(id, usuarioAtualizarSenhaInputDTO);
    }

}
