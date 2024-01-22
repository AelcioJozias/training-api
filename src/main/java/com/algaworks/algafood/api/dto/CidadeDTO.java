package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CidadeDTO
 */

// não precisaria da descricao abaixo, fazendo apenas por didática e deixar registrado
    //isso serve para descrever um model, lá... embaixo no documento. Models.
@ApiModel(value = "CidadeDTO", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeDTO {

    @ApiModelProperty(value = "Id da cidade", example = "1")
    private Long id;

    @ApiModelProperty(example = "Uberlandia")
    private String nome;

    private EstadoCidadeDTO estado;
  
}