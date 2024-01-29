package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(value = "Restaurante")
public class RestauranteIdInput {

    @NotNull
    @ApiModelProperty(value = "ID do restaurante", example = "1", required = true)
    private Long id;

}
