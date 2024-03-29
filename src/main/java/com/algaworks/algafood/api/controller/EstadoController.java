package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoDTODisassembler;
import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.api.dto.input.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import com.algaworks.algafood.openapi.controller.EstadoControllerOpenApi;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;

	@Autowired
	private EstadoDTODisassembler estadoDTODisassembler;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Override
  @GetMapping
	public List<EstadoDTO> listar() {
		return estadoDTOAssembler.toCollectionDTO(estadoRepository.findAll());
	}

	@Override
  @GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable Long estadoId) {
		return cadastroEstado.buscarOuFalhar(estadoId);
	}

	@Override
  @PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@Valid @RequestBody EstadoInputDTO estadoInputDTO) {
		return estadoDTOAssembler.toDTO(cadastroEstado.salvar(estadoDTODisassembler.toDomainObject(estadoInputDTO)));
	}

	@Override
  @PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @Valid @RequestBody EstadoInputDTO estadoInputDTO) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		estadoDTODisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
		return cadastroEstado.salvar(estadoAtual);
	}

	@Override
  @ResponseStatus(NO_CONTENT)
	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);
	}

}
