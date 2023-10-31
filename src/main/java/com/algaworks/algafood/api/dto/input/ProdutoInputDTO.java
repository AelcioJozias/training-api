package com.algaworks.algafood.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class ProdutoInputDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @PositiveOrZero
    private BigDecimal preco;

    @NotNull
    private Boolean ativo ;
}
