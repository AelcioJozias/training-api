package com.algaworks.algafood.openapi.controller;

import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModel
public interface CozinhaControllerOpenApi {

    @ApiOperation(value = "Lista as cozinhas com paginação")
    Page<CozinhaDTO> listar(Pageable pageable);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    @ApiOperation(value = "Busca uma cozinha por ID")
    CozinhaDTO buscar(Long cozinhaId);

    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha criada"),
    })
    @ApiOperation(value = "Cadastra uma cozinha")
    CozinhaDTO adicionar(CozinhaDTO cozinhaDTO);

    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha Atualizada"),
            @ApiResponse(code = 404, message = "Cozinha Não encontrada", response = Problem.class)
    })
    @ApiOperation(value = "Atualiza uma cozinha por ID")
    CozinhaDTO atualizar(Long cozinhaId,
                         CozinhaDTO cozinhaDTO);

    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha Excluída"),
            @ApiResponse(code = 404, message = "Cozinha Não encontrada", response = Problem.class)
    })
    @ApiOperation(value = "Exclui uma cozinha por ID")
    void remover(Long cozinhaId);
}
