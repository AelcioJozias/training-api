package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String CIDADE_DE_CÓDIGO_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String NÃO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CÓDIGO = "Não existe um cadastro de cidade com código %d";


	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		cadastroEstadoService.buscarOuFalhar(estadoId);
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			buscarOuFalhar(cidadeId);
			cidadeRepository.deleteById(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(CIDADE_DE_CÓDIGO_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO, cidadeId));
		}
	}
	
	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format(NÃO_EXISTE_UM_CADASTRO_DE_CIDADE_COM_CÓDIGO, id)));
	}
}
