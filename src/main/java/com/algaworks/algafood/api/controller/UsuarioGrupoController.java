package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTODisassembler;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioGrupoController {

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    CadastroGrupoService cadastroGrupoService;

    @Autowired
    GrupoDTOAssembler grupoDTOAssembler;


    @GetMapping("/{usuarioId}/grupos")
    public List<GrupoDTO> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
        return grupoDTOAssembler.toCollectionDTO(usuario.getGrupos());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @PutMapping("/{usuarioId}/grupos/{grupoId}")
    public void vincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        usuario.vincularGupo(grupo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("/{usuarioId}/grupos/{grupoId}")
    public void removerGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        usuario.desvincularGrupo(grupo);
    }




}











