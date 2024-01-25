package com.algaworks.algafood.openapi.controller;

import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.input.CidadeInputDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.model.Cidade;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
    @ApiOperation("Lista as cidades")
    List<Cidade> listar();

    @ApiOperation("Busca cidade por id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade Não encontrada", response = Problem.class)
    })
        // @ApiParam: Atencao quando for um tipo inteiro, colocar um exemplo pra não dar excecao,
        // talvez tenha que fazer isso pra String também, quando é um objeto nao precisa
    CidadeDTO buscar(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId);

    @ApiOperation("Cria uma cidade")
    @ApiResponses({
            // aqui podemos emitir o response, o spring fox já sabe que por retornar uma objeto cidade dto, esse será o tipo à representar no response
            @ApiResponse(code = 201, message = "Cidade criada"),
    })
    CidadeDTO adicionar(CidadeInputDTO cidadeInputDTO);

    @ApiOperation("Atualiza uma cidade existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "CidadeAtualizada"),
            @ApiResponse(code = 404, message = "Cidade Não encontrada", response = Problem.class)
    })
    CidadeDTO atualizar(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId,
                        @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                        CidadeInputDTO cidadeInputDTO);

    @ApiOperation("Exclui uma cidade")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade Excluída"),
            @ApiResponse(code = 404, message = "Cidade Não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId);
}
