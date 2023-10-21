package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * CozinhaInputDTO
 */
@Getter
@Setter
public class CozinhaInputDTO {

  @NotBlank
  private String nome; 
}