package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel
public class PedidoFilter {

	@ApiModelProperty(value = "ID do cliente", example = "1")
	private Long clienteId;

	@ApiModelProperty(value = "ID do restaurante", example = "1")
	private Long restauranteId;

	@ApiModelProperty(value = "Data de criação início do pedido", example = "2023-10-20T12:00:00Z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;

	@ApiModelProperty(value = "Data de criação fim do pedido", example = "2023-11-20T05:08:14z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
	
}
