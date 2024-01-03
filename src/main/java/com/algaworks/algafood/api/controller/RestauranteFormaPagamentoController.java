package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;


@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteFormaPagamentoController {


    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

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
