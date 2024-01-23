package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.input.GrupoInputDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenapi {

    @ApiResponses({
            @ApiResponse(code = 404, message = "Grupo não econtrado", response = Problem.class),
            @ApiResponse(code = 400, message = "ID inváliado", response = Problem.class)
    })
    @ApiOperation(value = "Busca um grupo por ID")
    GrupoDTO pesquisar(@PathVariable Long id);


    @ApiOperation(value = "Lista os grupos")
    List<GrupoDTO> listar();

    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo criado")
    })
    @ApiOperation(value = "Cadastra um grupo")
    GrupoDTO salvar(@Valid @RequestBody GrupoInputDTO grupoInputDTO);


    @ApiResponses({
            @ApiResponse(code = 404, message = "Grupo não econtrado", response = Problem.class),
            @ApiResponse(code = 400, message = "ID inváliado", response = Problem.class)
    })
    @ApiOperation(value = "Atualiza um grupo por ID")
    GrupoDTO atualizar(@RequestBody @Valid GrupoInputDTO grupoInputDTO, @PathVariable Long id);


    @ApiResponses({
            @ApiResponse(code = 404, message = "Grupo não econtrado", response = Problem.class),
            @ApiResponse(code = 400, message = "ID inváliado", response = Problem.class)
    })
    @ApiOperation(value = "Excluir um grupo por ID")
    void excluir(@PathVariable Long id);
}
