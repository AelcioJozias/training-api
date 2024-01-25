package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.openapi.controller.CidadeControllerOpenApi;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeDTODisassembler;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.input.CidadeInputDTO;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
@Api(tags = "Cidade")
@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	private final CidadeRepository cidadeRepository;
	private final CadastroCidadeService cadastroCidade;
	private final CidadeDTOAssembler cidadeDTOAssembler;
	private final CidadeDTODisassembler cidadeDTODisassembler;

	CidadeController(CidadeRepository cidadeRepository, CadastroCidadeService cadastroCidade,
					 CidadeDTOAssembler cidadeDTOAssembler, CidadeDTODisassembler cidadeDTODisassemble) {
		this.cadastroCidade = cadastroCidade;
		this.cidadeDTOAssembler = cidadeDTOAssembler;
		this.cidadeRepository = cidadeRepository;
		this.cidadeDTODisassembler = cidadeDTODisassemble;
	}

	@Override
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return cidadeDTOAssembler.toDTO(cadastroCidade.buscarOuFalhar(cidadeId));
	}

	@Override
	@PostMapping
	@ResponseStatus(value = CREATED)
	public CidadeDTO adicionar(
			@Valid @RequestBody CidadeInputDTO cidadeInputDTO) {
		Cidade cidade = cidadeDTODisassembler.toDomainObject(cidadeInputDTO);
		try {
			if (cidade.getEstado() != null)
				cidade.setEstado(cadastroCidade.buscarOuFalharEstado(cidade.getEstado().getId()));
		} catch (EstadoNaoEncontradoException entidadeNaoEncontradaException) {
			throw new NegocioException(entidadeNaoEncontradaException.getMessage(), entidadeNaoEncontradaException);
		}
		return cidadeDTOAssembler.toDTO(cadastroCidade.salvar(cidade));
	}

	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @Valid @RequestBody CidadeInputDTO cidadeInputDTO) {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
		cidadeDTODisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);
		try {
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException entidadeNaoEncontradaException) {
			throw new NegocioException(entidadeNaoEncontradaException.getMessage(), entidadeNaoEncontradaException);
		}
		return cidadeDTOAssembler.toDTO(cidadeAtual);
	}

	@Override
	@ResponseStatus(NO_CONTENT)
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
}