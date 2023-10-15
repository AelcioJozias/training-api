package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTODisassembler {

    public Restaurante toModel(RestauranteInputDTO restauranteInputDTO) {
        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInputDTO.getCozinha().getId());

        return Restaurante.builder()
                .nome(restauranteInputDTO.getNome())
                .taxaFrete(restauranteInputDTO.getTaxaFrete())
                .cozinha(cozinha)
                .build();
    }

}
