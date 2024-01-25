package com.algaworks.algafood.openapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Anotando aqui para ficar bem claro...
 * Essa substituição do pageable que foi feita, é apenas para customizar a entrada dos parâmetros.
 * Os params que podem ser passados na requisição, e nesse caso aqui, já vai alterar para todos
 */
@ApiModel(value = "Pageable", description = "Objeto responsável pela pela ordenação")
@Getter
@Setter
public class PageableDtoOpenApi {

    @ApiModelProperty(value = "Número da página (começa em 0)", example = "0")
    private int page;

    @ApiModelProperty(value = "Quantidade de elementos por página", example = "10")
    private int size;

    @ApiModelProperty(value = "Nome da propriedade para ordenação", example = "nome,asc")
    private List<String> sort = new ArrayList<>();

}
