package com.algaworks.algafood.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeEnderecoteInputDTO {

    @NotNull
    private Long id;

}
