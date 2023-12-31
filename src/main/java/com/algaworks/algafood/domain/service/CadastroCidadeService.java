package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String NOO_E_POSSIVEL_EXCLUIR_O_ID_ESTÁ_SENDO_USADO_COMO_UMA_FK = "não é possível excluir o id %s. id está sendo usado como uma fk";

	private static final String NAO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CODIGO = "Não existe um cadastro de cidade com código %s";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		cidade.setEstado(buscarOuFalharEstado(estadoId));
		return cidadeRepository.save(cidade);
	}

	public Estado buscarOuFalharEstado(Long estadoId) {
		return cadastroEstadoService.buscarOuFalhar(estadoId);
	}

	@Transactional
	public void excluir(Long cidadeId) {
		try {
			buscarOuFalhar(cidadeId);
			cidadeRepository.deleteById(cidadeId);
      cidadeRepository.flush();
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
