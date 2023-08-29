package com.algaworks.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

  private static final long serialVersionUID = 1L;

  protected EntidadeNaoEncontradaException(String mensagem) {
    super(mensagem);
  }

  protected EntidadeNaoEncontradaException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }

}
