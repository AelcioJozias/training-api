package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController {

    @Autowired
    CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscarPorId(@PathVariable Long id, ServletWebRequest request) {
        String eTag = cadastroFormaPagamentoService.genereteEtag(request);
        if(request.checkNotModified(eTag)) {
            return null;
        }
        FormaPagamentoDTO formaPagamento = cadastroFormaPagamentoService.buscarPorId(id);
        return ResponseEntity.ok()
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamento);
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listarFormasPagamento(ServletWebRequest request) {
        String eTag = cadastroFormaPagamentoService.genereteEtag(request);

        // verifica se não ouve modificacao no eTeg, se não houve retornar null,
        // o método abaixo também adiciona o Eteg no cabeçalho e já faz o set do status http
        // É recomendado por documentacao apenas dar o retorno null, como o método já fez todos os presets
        // necessários para quando for true
        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamentoDTO> formasPagamento = cadastroFormaPagamentoService.listarFormasPagamento();
        return ResponseEntity.ok()
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamento);
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@Valid @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
        return cadastroFormaPagamentoService.salvar(formaPagamentoInputDTO);
    }

    @PutMapping(value = "/{id}")
    public FormaPagamentoDTO atualizar(@PathVariable Long id, @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
        return cadastroFormaPagamentoService.atualizarRecursoCompleto(id, formaPagamentoInputDTO);
    }

    @DeleteMapping(value = "/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cadastroFormaPagamentoService.excluir(id);
    }

}
