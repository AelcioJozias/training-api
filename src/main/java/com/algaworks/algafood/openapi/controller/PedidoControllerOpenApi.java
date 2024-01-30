package com.algaworks.algafood.openapi.controller;

import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.api.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string",
                    value = "nome das propriedades para filtrar nas respostas, separar por vírgula",
                    example = "cliente.nome"
            )
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id do pedido inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiOperation("Pesquisa os pedidos")
    Page<PedidoResumoDTO> pesquisar(PedidoFilter pedidoFilter, Pageable pageable);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Id do pedido inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string",
                    value = "nome das propriedades para filtrar nas respostas, separar por vírgula"
            )
    })
    @ApiOperation("Busca um pedido por ID")
    PedidoDTO buscar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String codigoPedido);

    @ApiResponses({
            @ApiResponse(code = 404, message = "Algum item de associação não foi encontrado, ex:(RestauranteId 1000 -> não existe.",
                    response = Problem.class)
    })
    @ApiOperation("Registra um pedido")
    PedidoDTO emitirPedido(@ApiParam(value = "Corpo do pedido", required = true) PedidoInput pedidoInput);

}
