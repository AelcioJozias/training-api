package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String NAO_EXISTE_UM_RESTAURANTE_COM_O_ID = "Não existe um restaurante com o id: %s";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		restaurante.setCozinha(cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId()));
		restaurante.getEndereco().setCidade(buscarOuFalharCidade(restaurante.getEndereco().getCidade().getId()));
		return restauranteRepository.save(restaurante);
	}

	public void buscarOuFalharCozinha(Restaurante restaurante) {
		cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
	}

	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(
				String.format(NAO_EXISTE_UM_RESTAURANTE_COM_O_ID, id)));
	}

	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		restaurante.ativar();
	}

	@Transactional
	public void desativar(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		restaurante.desativar();
	}

	public Cidade buscarOuFalharCidade(Long id) {
		return cadastroCidadeService.buscarOuFalhar(id);
	}

}
