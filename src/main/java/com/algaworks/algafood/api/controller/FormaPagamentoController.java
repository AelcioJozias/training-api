package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @GetMapping(value = "/{id}")
    public FormaPagamentoDTO buscarPorId(@PathVariable Long id) {
        return cadastroFormaPagamentoService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listarFormasPagamento() {
        List<FormaPagamentoDTO> formasPagamento = cadastroFormaPagamentoService.listarFormasPagamento();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamento);
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@Valid @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
        return cadastroFormaPagamentoService.salvar(formaPagamentoInputDTO);
    }

    @PutMapping(value = "/{id}")
    public FormaPagamentoDTO buscarPorId(@PathVariable Long id, @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
        return cadastroFormaPagamentoService.atualizarRecursoCompleto(id, formaPagamentoInputDTO);
    }

    @DeleteMapping(value = "/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cadastroFormaPagamentoService.excluir(id);
    }

}
