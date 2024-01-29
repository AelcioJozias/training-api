package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel("Endereco")
public class EnderecoInput {

    @ApiModelProperty(value = "Cep do endereço", example = "88110153", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(value = "Nome da rua", example = "Av. Brasil", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(value = "Número da casa", example = "1580", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(value = "Complemento do endereço", example = "Casa")
    private String complemento;

    @ApiModelProperty(value = "Bairro", example = "Bela Vista", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;
}
