package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String NÃO_EXISTE_UM_CADASTRO_DE_ESTADO_COM_CÓDIGO = "Não existe um cadastro de estado com código %d";
	private static final String ESTADO_DE_CÓDIGO_NÃO_PODE_SER_REMOVIDO_POIS_ESTÁ_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntidadeNaoEncontradaException(
				String.format(NÃO_EXISTE_UM_CADASTRO_DE_ESTADO_COM_CÓDIGO, estadoId));
		
		} catch (DataIntegrityViolationException exception) {
			throw new EntidadeEmUsoException(
				String.format(ESTADO_DE_CÓDIGO_NÃO_PODE_SER_REMOVIDO_POIS_ESTÁ_EM_USO, estadoId));
		}
	}
	
	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id).
				orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(NÃO_EXISTE_UM_CADASTRO_DE_ESTADO_COM_CÓDIGO, id)));
	}
	
}
