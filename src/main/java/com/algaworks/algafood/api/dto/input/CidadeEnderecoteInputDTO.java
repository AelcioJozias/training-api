package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "CidadeEnderecoInput")
public class CidadeEnderecoteInputDTO {

    @ApiModelProperty(value = "ID da cidade", required = true)
    @NotNull
    private Long id;

}
