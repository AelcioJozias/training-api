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

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    UsuarioDTOAssembler usuarioDTOAssembler;

    @GetMapping(value = "{restauranteId}/responsaveis")
    public List<UsuarioDTO> listarUsariosResponsaveis(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        return usuarioDTOAssembler.toCollectionDTO(restaurante.getResponsaveis());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "{restauranteId}/responsaveis/{usuarioId}")
    public void adicionarReponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        cadastroRestaurante.adicionarResponsavel(restauranteId, usuarioId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{restauranteId}/responsaveis/{usuarioId}")
    public void removerReponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        cadastroRestaurante.removerResponsavel(restauranteId, usuarioId);
    }


}
