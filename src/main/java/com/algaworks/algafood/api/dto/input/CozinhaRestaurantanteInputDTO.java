package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "CozinhaRestaurantanteInput")
public class CozinhaRestaurantanteInputDTO {

        @ApiModelProperty(value = "Id da Cozinha", example = "1", required = true)
        @NotNull
        private Long id;

}
