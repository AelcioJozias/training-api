package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
@ToString
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
}
