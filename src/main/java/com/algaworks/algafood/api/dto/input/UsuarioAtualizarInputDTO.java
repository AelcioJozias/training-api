package com.algaworks.algafood.api.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UsuarioAtualizarInputDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;
}
