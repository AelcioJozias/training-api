package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("ItemPedido")
public class ItemPedidoInput {

	@ApiModelProperty(value = "ID da forma de pagamento", example = "1", required = true)
	@NotNull
	private Long produtoId;

	@ApiModelProperty(value = "Quantidade de itens", example = "2", required = true)
	@NotNull
	@PositiveOrZero
	private Integer quantidade;

	@ApiModelProperty(value = "Uma observação para o pedido", example = "Mal passado, por favor")
	private String observacao;
	
}
