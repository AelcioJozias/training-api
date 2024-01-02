package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
}
