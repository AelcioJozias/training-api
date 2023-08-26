package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
}
