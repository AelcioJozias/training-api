package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputDTO {

  @ApiModelProperty(example = "Florianópolis", required = true)
  @NotBlank
  private String nome;

  @NotNull
  @Valid
  private EstadoCidadeInput estado;

}
