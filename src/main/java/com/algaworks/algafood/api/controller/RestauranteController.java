package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.api.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTODisassembler;
import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private RestauranteDTOAssembler restauranDTOAssembler;

  @Autowired
  private RestauranteDTODisassembler restauranteDTODisassembler;

  // para esse filtro do json view funcionar, tem que lembrar de fazer a mesma anotacao encima do atributo da classe
  @Override
  @JsonView(RestauranteView.Resumo.class)
  @GetMapping
  public List<RestauranteDTO> listar() {
    return restauranDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
  }

  // para esse filtro do json view funcionar, tem que lembrar de fazer a mesma anotacao encima do atributo da classe
  @Override
  @JsonView(RestauranteView.ApenasNome.class)
  @GetMapping(params = "projecao=apenas-nome")
  public List<RestauranteDTO> listarApenasNomes() {
    return listar();
  }

  @Override
  @GetMapping("/{restauranteId}")
  public RestauranteDTO buscar(@PathVariable Long restauranteId) {
    return restauranDTOAssembler.toDTO(cadastroRestaurante.buscarOuFalhar(restauranteId));
  }

  @Override
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

  @Override
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

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping(value = "/{restauranteId}/ativo")
  public void ativar(@PathVariable(value = "restauranteId") Long id) {
    cadastroRestaurante.ativar(id);
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/{restauranteId}/ativo")
  public void inativar(@PathVariable(value = "restauranteId") Long id) {
    cadastroRestaurante.desativar(id);
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping(value = "/ativacoes")
  public void ativarMultiplos(@RequestBody List<Long> ids) {
    cadastroRestaurante.ativar(ids);
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/ativacoes")
  public void inativarMultiplos(@RequestBody List<Long> ids) {
    cadastroRestaurante.desativar(ids);
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping(value = "/{restauranteId}/abertura")
  public void abrir(@PathVariable(value = "restauranteId") Long id) {
    cadastroRestaurante.abrir(id);
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping(value = "/{restauranteId}/fechamento")
  public void fechar(@PathVariable(value = "restauranteId") Long id) {
    cadastroRestaurante.fechar(id);
  }

}
