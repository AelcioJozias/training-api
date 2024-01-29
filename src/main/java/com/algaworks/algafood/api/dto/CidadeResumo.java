package com.algaworks.algafood.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumo {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Uberlândia")
    private String nome;

    @JsonProperty("estado")
    @ApiModelProperty(example = "Minas Gerais")
    private String nomeEstado;
}
