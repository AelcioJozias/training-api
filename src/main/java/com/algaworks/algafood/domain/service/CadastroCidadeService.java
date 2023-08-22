package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String NÃO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CÓDIGO = "Não existe um cadastro de cidade com código %d";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		buscarOuFalharEstado(estadoId);
		return cidadeRepository.save(cidade);
	}

	public void buscarOuFalharEstado(Long estadoId) {
		cadastroEstadoService.buscarOuFalhar(estadoId);
	}
	
	public void excluir(Long cidadeId) {
		try {
			buscarOuFalhar(cidadeId);
			cidadeRepository.deleteById(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(NÃO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CÓDIGO, cidadeId));
		}
	}
	
	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format(NÃO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CÓDIGO, id)));
	}
}
