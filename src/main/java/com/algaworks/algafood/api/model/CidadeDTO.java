package com.algaworks.algafood.api.model;

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