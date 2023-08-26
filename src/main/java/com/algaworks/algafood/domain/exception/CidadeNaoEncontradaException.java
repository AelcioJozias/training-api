package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
