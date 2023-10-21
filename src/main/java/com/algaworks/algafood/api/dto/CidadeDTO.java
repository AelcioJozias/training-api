package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * CidadeDTO
 */
@Getter
@Setter
public class CidadeDTO {

    private Long id;
    private String nome;
    private EstadoCidadeDTO estado;
  
}