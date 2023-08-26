package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
