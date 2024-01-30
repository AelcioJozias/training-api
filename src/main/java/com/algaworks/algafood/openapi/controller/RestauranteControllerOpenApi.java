package com.algaworks.algafood.openapi.controller;

import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.api.dto.input.RestauranteInputDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public interface RestauranteControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                    name = "projecao", paramType = "query", type = "string")
    })
    @ApiOperation(value = "Lista os restaurantes")
    List<RestauranteDTO> listar();

    @ApiIgnore
    // other option to hidden on doc is -> @ApiOperation(hidden = true)
    List<RestauranteDTO> listarApenasNomes();

    @ApiResponses({
            @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    @ApiOperation("Busca um restaurante por ID")
    RestauranteDTO buscar(@ApiParam(required = true, value = "ID de um restaurante", example = "1") Long restauranteId);

    @ApiResponses({
            @ApiResponse(code = 404, message = "Alguma associação não foi encontrada, ex: Cozinha ID inválido", response = Problem.class)
    })
    @ApiOperation(value = "Cadastra um restaurante")
    RestauranteDTO adicionar(@ApiParam(name = "body", value = "Novo Restaurante", required = true) RestauranteInputDTO restauranteDTO);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    @ApiOperation("Atualiza um restaurante existente")
    RestauranteDTO atualizar(@ApiParam(required = true, value = "ID do restaurante", example = "1") Long restauranteId,
                             @ApiParam(name = "body", value = "Novas propriedades do restaurantes") RestauranteInputDTO restauranteDTO);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso")
    })
    @ApiOperation(value = "Ativa um restaurante")
    void ativar(@ApiParam(required = true, value = "ID do restaurante", example = "1") Long id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso")
    })
    @ApiOperation(value = "Inativa um restaurante por ID")
    void inativar(@ApiParam(required = true, value = "ID do restaurante", example = "1") Long id);

    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    @ApiOperation(value = "Ativa múltiplos restaurantes")
    void ativarMultiplos(@ApiParam(required = true, value = "IDs dos restaurantes a serem ativados", example = "{1,2,3}") List<Long> ids);

    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes inativados com sucesso")
    })
    @ApiOperation(value = "Inativa múltiplos restaurantes")
    void inativarMultiplos(@ApiParam(required = true, value = "IDs dos restaurantes a serem inativados", example = "1,2,3") List<Long> ids);

    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso")
    })
    @ApiOperation(value = "Abre um restaurante por ID")
    void abrir(@ApiParam(required = true, value = "ID do restaurante", example = "1") Long id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso")
    })
    @ApiOperation(value = "Fecha um restaurante por ID")
    void fechar(@ApiParam(required = true, value = "ID do restaurante", example = "1") Long id);
}
