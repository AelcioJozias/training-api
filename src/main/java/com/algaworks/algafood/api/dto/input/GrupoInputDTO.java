package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GrupoInputDTO {

    @NotBlank
    @ApiModelProperty(example = "Gerente", required = true)
    private String nome;

}
