package com.algaworks.algafood.api.dto;

import com.algaworks.algafood.domain.model.Endereco;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaDTO cozinha;
    private boolean ativo;
    private EnderecoDTO endereco;

}
