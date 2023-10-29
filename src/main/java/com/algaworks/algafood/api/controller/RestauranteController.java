package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTODisassembler;
import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private CadastroCozinhaService cadastroCozinhaService;

  @Autowired
  private SmartValidator validator;

  @Autowired
  private RestauranteDTOAssembler restauranDTOAssembler;

  @Autowired
  private RestauranteDTODisassembler restauranteDTODisassembler;

  @GetMapping
  public List<RestauranteDTO> listar() {
    return restauranDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
  }

  @GetMapping("/{restauranteId}")
  public RestauranteDTO buscar(@PathVariable Long restauranteId) {
    return restauranDTOAssembler.toDTO(cadastroRestaurante.buscarOuFalhar(restauranteId));
  }

  @ResponseStatus(code = HttpStatus.CREATED)
  @PostMapping
  public RestauranteDTO adicionar(@Valid @RequestBody RestauranteInputDTO restauranteDTO) {
    Restaurante restaurante = restauranteDTODisassembler.toDomainObject(restauranteDTO);
    try {
      cadastroRestaurante.buscarOuFalharCozinha(restaurante);
    } catch (CozinhaNaoEncontradaException e) {
      throw new NegocioException(e.getMessage(), e);
    }
    return  restauranDTOAssembler.toDTO(cadastroRestaurante.salvar(restaurante));
  }

  @PutMapping("/{restauranteId}")
  public RestauranteDTO atualizar(@PathVariable Long restauranteId, @Valid @RequestBody RestauranteInputDTO restauranteDTO) {
    try {
      Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
      restauranteDTODisassembler.copyToDomainObject(restauranteDTO, restauranteAtual);
      return restauranDTOAssembler.toDTO(cadastroRestaurante.salvar(restauranteAtual));
    } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping(value = "/{restauranteId}/ativo")
  public void ativar(@PathVariable(value = "restauranteId") Long id) {
    cadastroRestaurante.ativar(id);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/{restauranteId}/ativo")
  public void inativar(@PathVariable(value = "restauranteId") Long id) {
    cadastroRestaurante.desativar(id);
  }

}
