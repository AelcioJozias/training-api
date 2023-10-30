package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;


@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private RestauranteDTOAssembler restauranDTOAssembler;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @GetMapping(value = "/{restauranteId}/formas-pagamento")
    public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        return formaPagamentoAssembler.toCollectionDTO(restaurante.getFormasPagamento());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{restuaranteId}/formas-pagamento/{formaPagamentoId}")
    public void adicionarFormaPagamento(@PathVariable Long restuaranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associarFormaPagamento(restuaranteId, formaPagamentoId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{restuaranteId}/formas-pagamento/{formaPagamentoId}")
    public void removerFormaPagamento(@PathVariable Long restuaranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociarFormaPagamento(restuaranteId, formaPagamentoId);
    }




}
