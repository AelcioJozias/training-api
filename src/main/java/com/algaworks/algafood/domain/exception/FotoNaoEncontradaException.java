package com.algaworks.algafood.domain.exception;

public class FotoNaoEncontradaException extends  EntidadeNaoEncontradaException {

    public static final String FOTO_NAO_EXISTE_MSG = "Não existe um cadastro de foto do produto com o código %s para o restaurante de código %s";

    public FotoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }
    public FotoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format(FOTO_NAO_EXISTE_MSG, produtoId, restauranteId));
    }
}
