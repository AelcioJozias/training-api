package com.algaworks.algafood.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.api.dto.input.EstadoInputDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.model.Estado;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

  @ApiOperation("Lista os estados")
  List<EstadoDTO> listar();

  @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
  @ApiOperation("Busca um estado por ID")
  Estado buscar(@ApiParam(value = "ID de um estado", example = "1") Long estadoId);

  @ApiResponses({
    @ApiResponse(code = 201, message = "Cozinha criada"),
})
  @ApiOperation("Cadastra um estado")
  EstadoDTO adicionar(@ApiParam(value = "body", example = "Novo Estado") EstadoInputDTO estadoInputDTO);

  @ApiResponses({
    @ApiResponse(code = 200, message = "Cozinha Atualizada"),
    @ApiResponse(code = 404, message = "Cozinha Não encontrada", response = Problem.class)
})
  @ApiOperation("Atualiza um estado por ID")
  Estado atualizar(@ApiParam(value = "ID de um estado", example = "1") Long estadoId, EstadoInputDTO estadoInputDTO);

  @ApiResponses({
    @ApiResponse(code = 204, message = "Cozinha Excluída"),
    @ApiResponse(code = 404, message = "Cozinha Não encontrada", response = Problem.class)
})
  @ApiOperation("Exclui um estado por ID")
  void remover(@ApiParam(value = "ID de um estado", example = "1") Long estadoId);

}