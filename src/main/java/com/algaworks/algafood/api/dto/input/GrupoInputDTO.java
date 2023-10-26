package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GrupoInputDTO {

    @NotBlank
    private String nome;
}
