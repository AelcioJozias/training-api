package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String NAO_EXISTE_UM_CADASTRO_DE_ESTADO_COM_CODIGO = "Não existe um cadastro de estado com código %d";
	private static final String ESTADO_DE_CODIGO_NAO_PODE_SER_REMOVIDO_POIS_ESTA_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
      estadoRepository.flush();
		} catch (EmptyResultDataAccessException exception) {
			throw new EstadoNaoEncontradoException(
					String.format(NAO_EXISTE_UM_CADASTRO_DE_ESTADO_COM_CODIGO, estadoId), exception);

		} catch (DataIntegrityViolationException exception) {
			throw new EntidadeEmUsoException(
					String.format(ESTADO_DE_CODIGO_NAO_PODE_SER_REMOVIDO_POIS_ESTA_EM_USO, estadoId, exception));
		}
	}
	
	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id).orElseThrow(
				() -> new EstadoNaoEncontradoException(String.format(NAO_EXISTE_UM_CADASTRO_DE_ESTADO_COM_CODIGO, id)));
	}

}
