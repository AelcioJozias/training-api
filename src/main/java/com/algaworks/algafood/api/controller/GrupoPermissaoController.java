package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoDTODisassembler;
import com.algaworks.algafood.api.assembler.PermissaoAssemblerDTO;
import com.algaworks.algafood.api.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoPermissaoController {

    @Autowired
    CadastroGrupoService cadastroGrupoService;

    @Autowired
    GrupoDTODisassembler grupoDTODisassembler;

    @Autowired
    GrupoDTOAssembler grupoDTOAssembler;

    @Autowired
    PermissaoAssemblerDTO permissaoAssemblerDTO;

    @Autowired
    CadastroPermissaoService cadastroPermissaoService;

    @GetMapping(value = "/{grupoId}/permissoes")
    public List<PermissaoDTO> buscar(@PathVariable  Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscar(grupoId);
        Set<Permissao> permissoes = grupo.getPermissoes();
        return permissaoAssemblerDTO.toCollectionDTO(permissoes);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{grupoId}/permissoes/{permissaoId}")
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        Grupo grupo = cadastroGrupoService.buscar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
        grupo.associarPermissao(permissao);
        cadastroGrupoService.salvar(grupo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{grupoId}/permissoes/{permissaoId}")
    public void remover(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        Grupo grupo = cadastroGrupoService.buscar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
        grupo.removerPermissao(permissao);
        cadastroGrupoService.salvar(grupo);
    }


}
