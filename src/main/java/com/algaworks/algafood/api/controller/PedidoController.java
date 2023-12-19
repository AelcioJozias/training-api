package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.api.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.dto.input.PedidoInput;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidosSpecs;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoDTOAssembler pedidoModelAssembler;

    @Autowired
    PedidoResumoDTOAssembler pedidoResumoDTOAssembler;

    @Autowired
    PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size = 10) Pageable pageable) {
        pageable = traduzirPageable(pageable);
            Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidosSpecs.usandoFiltro(pedidoFilter), pageable);
        List<PedidoResumoDTO> pedidoDTO = pedidoResumoDTOAssembler.toCollectionModel(pedidosPage.getContent());
        return new PageImpl<>(pedidoDTO, pageable, pedidosPage.getTotalElements());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        return pedidoModelAssembler.toDTO(pedido);
    }

    @PostMapping
    public PedidoDTO emitirPedido(@RequestBody PedidoInput pedidoInput) {

        Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(1L);
        pedido = emissaoPedido.emitir(pedido);

        return pedidoModelAssembler.toDTO(pedido);
    }

    /*
    * esse translate foi feita para traduzir as propriedades que o usuário passa na api como params para ordernacao
    */
    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restauranteNome", "restaurante.nome",
                "cliente.nome", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}