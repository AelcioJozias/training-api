package com.algaworks.algafood.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
public class EstadoInputDTO {

    @ApiModelProperty(value = "Nome do estado", example = "Santa Catarina")
    @NotBlank
    private String nome;
}
