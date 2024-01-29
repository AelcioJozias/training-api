package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("FormaPagamento")
public class FormaPagamentoIdInput {

	@ApiModelProperty(value = "ID da forma de pagamento", example = "1", required = true)
	@NotNull
	private Long id;
	
}
