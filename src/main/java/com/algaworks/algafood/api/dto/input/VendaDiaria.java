package com.algaworks.algafood.api.dto.input;

import java.math.BigDecimal;
import java.util.Date;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {

	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
	
}
