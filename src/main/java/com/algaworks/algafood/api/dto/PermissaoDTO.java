package com.algaworks.algafood.api.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PermissaoDTO {

    private Long id;
    private String nome;
    private String descricao;
}
