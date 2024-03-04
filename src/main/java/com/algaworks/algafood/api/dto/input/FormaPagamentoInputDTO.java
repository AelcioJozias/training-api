package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "FormaDePagamentoInput")
public class FormaPagamentoInputDTO {

    @ApiModelProperty(value = "Descrição da forma de pagamento", example = "Bitcoin", required = true)
    @NotBlank
    private String descricao;
}
