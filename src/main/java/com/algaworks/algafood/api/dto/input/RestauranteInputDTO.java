package com.algaworks.algafood.api.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

@Getter
@Setter
@ApiModel(value = "RestauranteInput")
public class RestauranteInputDTO {

    @ApiModelProperty(value = "Nome do restaurante", required = true, example = "Akilo")
    @NotBlank
    private String nome;

    @ApiModelProperty(value = "Taxa frete", required = true, example = "00.00")
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaRestaurantanteInputDTO cozinha;

    @NotNull
    @Valid
    private EnderecoInputDTO endereco;

}
