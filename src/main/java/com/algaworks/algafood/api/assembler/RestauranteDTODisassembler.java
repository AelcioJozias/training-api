package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toModel(RestauranteInputDTO restauranteInputDTO) {
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInputDTO, restaurante);
    }
}
