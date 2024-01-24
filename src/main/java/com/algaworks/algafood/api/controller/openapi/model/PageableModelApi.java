package com.algaworks.algafood.api.controller.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Pageable", description = "Objeto responsável pela pela ordenação")
@Getter
@Setter
public class PageableModelApi {

    @ApiModelProperty(value = "Número da página (começa em 0)", example = "0")
    private int page;

    @ApiModelProperty(value = "Quantidade de elementos por página", example = "10")
    private int size;

    @ApiModelProperty(value = "Nome da propriedade para ordenação", example = "nome,asc")
    private List<String> sort = new ArrayList<>();

}
