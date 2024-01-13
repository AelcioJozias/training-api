package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

    @Autowired
	private EmissaoPedidoService emissaoPedido;

    @Autowired
    private EnvioEmailService envioEmailService;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();

        var menssagem = EnvioEmailService.Menssagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("O pedido de código <strong>"
                        + pedido.getCodigo() + "</strong> foi confirmado!")
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(menssagem);
	}

  @Transactional
  public void cancelar(String codigoPedido) {
    Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
    pedido.cancelar();
  }

  @Transactional
  public void entregue(String codigoPedido) {
    Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

    pedido.entregar();
  }


	
}
