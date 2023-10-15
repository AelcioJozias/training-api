package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTODisassembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    Restaurante restaurante = restauranteDTODisassembler.toModel(restauranteDTO);
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
      Restaurante restaurante = restauranteDTODisassembler.toModel(restauranteDTO);

      Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

      BeanUtils.copyProperties(restaurante, restauranteAtual,
              "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

      return restauranDTOAssembler.toDTO(cadastroRestaurante.salvar(restauranteAtual));
    } catch (CozinhaNaoEncontradaException e) {
      throw new NegocioException(e.getMessage());
    }
  }
}
