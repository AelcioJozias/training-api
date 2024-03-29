package com.algaworks.algafood.openapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedDtoOpenApi<T> {
    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página", position = 10)
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de registros", position = 20)
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total de páginas", position = 30)
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)", position = 40)
    private Long number;
}
