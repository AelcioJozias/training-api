package com.algaworks.algafood.openapi.controller;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    @ApiOperation(value = "Busca uma forma de pagamento por ID")
    ResponseEntity<FormaPagamentoDTO> buscarPorId(@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long id,
                                                  ServletWebRequest request);

    @ApiOperation(value = "Lista as formas de pagamentos")
    ResponseEntity<List<FormaPagamentoDTO>> listarFormasPagamento(ServletWebRequest request);

    @ApiOperation(value = "Cria uma nova forma de pagamento")
    FormaPagamentoDTO salvar(FormaPagamentoInputDTO formaPagamentoInputDTO);

    @ApiResponses({
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    @ApiOperation(value = "Atualiza uma forma de pagamento por ID")
    FormaPagamentoDTO atualizar(@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long id, FormaPagamentoInputDTO formaPagamentoInputDTO);

    @ApiResponses({
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    @ApiOperation(value = "Excluir uma forma de pagamento por ID")
    void excluir(@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long id);
}
