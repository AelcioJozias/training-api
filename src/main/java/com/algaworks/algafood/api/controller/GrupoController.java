package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDTODisassembler;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

  @Autowired
  CadastroGrupoService cadastroGrupoService;

  @Autowired 
  GrupoDTODisassembler disassembler;

  @GetMapping("/{id}")
  public GrupoDTO pesquisar(@PathVariable Long id){
    return cadastroGrupoService.buscarGrupoDTO(id);
  }

  @GetMapping
  public List<GrupoDTO> listar(){
    return cadastroGrupoService.listar();
  }

  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping
  public GrupoDTO salvar(@Valid @RequestBody GrupoInputDTO grupoInputDTO) {
      return cadastroGrupoService.salvarGrupoDTO(grupoInputDTO);
  }

  @PutMapping(value = "/{id}")
  public GrupoDTO atualizar( @RequestBody @Valid GrupoInputDTO grupoInputDTO, @PathVariable Long id){
    Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(id);
    BeanUtils.copyProperties(grupoInputDTO, grupoAtual);
    return cadastroGrupoService.salvarGrupoDTO(grupoInputDTO);
  }

  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void excluir(@PathVariable Long id) {
    cadastroGrupoService.excluir(id);
  }

}
