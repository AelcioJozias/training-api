package com.algaworks.algafood.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoDTO {
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}
