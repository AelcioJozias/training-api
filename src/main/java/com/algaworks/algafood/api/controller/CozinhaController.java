package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaDTODisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

  @Autowired
  CozinhaDTOAssembler cozinhaDTOAssembler;

  @Autowired
  CozinhaDTODisassembler cozinhaDTODisassembler;

	@GetMapping
	public List<CozinhaDTO> listar() {
		return  cozinhaDTOAssembler.toCollectionDTO(cozinhaRepository.findAll()); 
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		return cozinhaDTOAssembler.toDTO(cadastroCozinha.buscarOuFalhar(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@Valid @RequestBody CozinhaDTO cozinhaDTO) {
		return cozinhaDTOAssembler.toDTO(cadastroCozinha.salvar(cozinhaDTODisassembler.toModel(cozinhaDTO)));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId,
			@Valid @RequestBody CozinhaDTO cozinhaDTO) {
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		BeanUtils.copyProperties(cozinhaDTODisassembler.toModel(cozinhaDTO) , cozinhaAtual, "id");
		return cozinhaDTOAssembler.toDTO(cadastroCozinha.salvar(cozinhaAtual));
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}

}
