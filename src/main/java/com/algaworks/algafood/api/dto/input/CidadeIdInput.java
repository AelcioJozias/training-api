package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel("Cidade")
public class CidadeIdInput {

    @ApiModelProperty(value = "ID do cidade", example = "1", required = true)
    @NotNull
    private Long id;

}
