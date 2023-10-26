package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{

  private static final String GRUPO_NAO_ENCONTRADO = "Não foi encontrado um grupo com o id %d";

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format(GRUPO_NAO_ENCONTRADO, id));
    }
}
