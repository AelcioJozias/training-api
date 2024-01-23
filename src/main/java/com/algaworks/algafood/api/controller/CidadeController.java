package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;

	@Autowired
	private CidadeDTODisassembler cidadeDTODisassembler;

	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@ApiOperation("Busca cidade por id")
	@GetMapping("/{cidadeId}")
	@ApiResponses({
			@ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade Não encontrada", response = Problem.class)
	})
	// @ApiParam: Atencao quando for um tipo inteiro, colocar um exemplo pra não dar excecao,
	// talvez tenha que fazer isso pra String também, quando é um objeto nao precisa
	public CidadeDTO buscar(@ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long cidadeId) {
		return cidadeDTOAssembler.toDTO(cadastroCidade.buscarOuFalhar(cidadeId));
	}

	@ApiOperation("Cria uma cidade")
	@PostMapping
	@ResponseStatus(value = CREATED)
	@ApiResponses({
			// aqui podemos emitir o response, o spring fox já sabe que por retornar uma objeto cidade dto, esse será o tipo à representar no response
			@ApiResponse(code = 201, message = "Cidade criada"),
	})
	public CidadeDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
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

	@ApiOperation("Atualiza uma cidade existente")
	@PutMapping("/{cidadeId}")
	@ApiResponses({
			@ApiResponse(code = 200, message = "CidadeAtualizada"),
			@ApiResponse(code = 404, message = "Cidade Não encontrada", response = Problem.class)
	})
	public CidadeDTO atualizar(@ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long cidadeId,
							   @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
							   @Valid @RequestBody CidadeInputDTO cidadeInputDTO) {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
		cidadeDTODisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);
		try {
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException entidadeNaoEncontradaException) {
			throw new NegocioException(entidadeNaoEncontradaException.getMessage(), entidadeNaoEncontradaException);
		}
		return cidadeDTOAssembler.toDTO(cidadeAtual);
	}

	@ApiOperation("Exclui uma cidade")
	@ResponseStatus(NO_CONTENT)
	@DeleteMapping("/{cidadeId}")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cidade Excluída"),
			@ApiResponse(code = 404, message = "Cidade Não encontrada", response = Problem.class)
	})
	public void remover(@ApiParam(value = "Id de uma cidade", example = "1")
							@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
}