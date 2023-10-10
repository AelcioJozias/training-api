package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

import javax.transaction.Transactional;

@Service
public class CadastroCidadeService {

	private static final String NOO_E_POSSIVEL_EXCLUIR_O_ID_ESTÁ_SENDO_USADO_COMO_UMA_FK = "não é possível excluir o id %d. id está sendo usado como uma fk";

	private static final String NAO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CODIGO = "Não existe um cadastro de cidade com código %d";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		buscarOuFalharEstado(estadoId);
		return cidadeRepository.save(cidade);
	}

	public void buscarOuFalharEstado(Long estadoId) {
		cadastroEstadoService.buscarOuFalhar(estadoId);
	}
	@Transactional
	public void excluir(Long cidadeId) {
		try {
			buscarOuFalhar(cidadeId);
			cidadeRepository.deleteById(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(NOO_E_POSSIVEL_EXCLUIR_O_ID_ESTÁ_SENDO_USADO_COMO_UMA_FK, cidadeId), e);
		}
	}

	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(
				() -> new CidadeNaoEncontradaException(String.format(NAO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CODIGO, id)));
	}
}
