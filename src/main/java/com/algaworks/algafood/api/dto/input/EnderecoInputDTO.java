package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "EnderecoInput")
public class EnderecoInputDTO {

    @ApiModelProperty(value = "Cep do endereco", example = "88-118-100", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(value = "Logradouro/Nome da rua", required = true, example = "Av. Beira Mar")
    @NotBlank
    private String logradouro;

    @ApiModelProperty(value = "Numero", required = true, example = "289")
    @NotBlank
    private String numero;

    @ApiModelProperty(value = "Complemento", example = "Próximo a escola", required = true)
    @NotBlank
    private String complemento;

    @ApiModelProperty(value = "Bairro", required = true, example = "José Nitro")
    @NotBlank
    private String bairro;

    @NotNull
    @Valid
    private CidadeEnderecoteInputDTO cidade;
}
