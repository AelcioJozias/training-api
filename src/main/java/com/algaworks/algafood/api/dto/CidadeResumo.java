package com.algaworks.algafood.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumo {
    private Long id;
    private String nome;
    @JsonProperty(value = "estado")
    private String nomeEstado;
}
