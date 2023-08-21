package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {


	private static final String NÃO_EXISTE_UM_RESTAURANTE_COM_O_ID = "Não existe um restaurante com o id %d: ";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	public Restaurante salvar(Restaurante restaurante) {
		cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(NÃO_EXISTE_UM_RESTAURANTE_COM_O_ID, id)));
	}
}
