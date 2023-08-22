package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId) {
		return cadastroCidade.buscarOuFalhar(cidadeId);
	}
	
	@PostMapping
	@ResponseStatus(value = CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		try {
			cadastroCidade.buscarOuFalharEstado(cidade.getEstado().getId());
		}
		catch(EntidadeNaoEncontradaException entidadeNaoEncontradaException){
			throw new NegocioException(entidadeNaoEncontradaException.getMessage());
		}
		return cadastroCidade.salvar(cidade);
	}
	
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
		}catch(EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
			throw new NegocioException(entidadeNaoEncontradaException.getMessage());
		}
		return cidadeAtual;
	}
	
	@ResponseStatus(NO_CONTENT)
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);	
	}
}