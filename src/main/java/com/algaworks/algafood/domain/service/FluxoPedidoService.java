package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.apache.tomcat.jni.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	private static final String STATUS_DO_PEDIDO_DE_ID_X_NAO_PODE_SER_ALTERADO_DE_Y_PARA_Z = "Status do pedido de id %d não pode ser alterado de %s para %s";
  @Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
		
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(
					String.format(STATUS_DO_PEDIDO_DE_ID_X_NAO_PODE_SER_ALTERADO_DE_Y_PARA_Z,
							pedido.getId(), pedido.getStatus().getDescricao(), 
							StatusPedido.CONFIRMADO.getDescricao()));
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}

  @Transactional
  public void cancelar(Long pedidoId) {
    
    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
    
    if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
      throw new NegocioException(
					String.format(STATUS_DO_PEDIDO_DE_ID_X_NAO_PODE_SER_ALTERADO_DE_Y_PARA_Z,
							pedido.getId(), pedido.getStatus().getDescricao(), 
							StatusPedido.CANCELADO.getDescricao()));
		}

    pedido.setStatus(StatusPedido.CANCELADO);
  }

  @Transactional
  public void entregue(Long pedidoId) {
    
    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
    
    if(!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
      throw new NegocioException(
					String.format(STATUS_DO_PEDIDO_DE_ID_X_NAO_PODE_SER_ALTERADO_DE_Y_PARA_Z,
							pedido.getId(), pedido.getStatus().getDescricao(), 
							StatusPedido.ENTREGUE.getDescricao()));
		}

    pedido.setStatus(StatusPedido.ENTREGUE);
  }


	
}
