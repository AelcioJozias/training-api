package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;

import javax.annotation.Resource;

@Service
public class FluxoPedidoService {

    @Autowired
	private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();

        // adicionado o save explicitamente para que o evento seja registrado.( registerEvent(new PedidoConfirmadoEvent(this)); )
        pedidoRepository.save(pedido);


//        var menssagem = EnvioEmailService.Menssagem.builder()
//                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
//                .corpo("pedido-confirmado.html")
//                .variavel("pedido", pedido)
//                .destinatario(pedido.getCliente().getEmail())
//                .build();
//
//        envioEmailService.enviar(menssagem);
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
