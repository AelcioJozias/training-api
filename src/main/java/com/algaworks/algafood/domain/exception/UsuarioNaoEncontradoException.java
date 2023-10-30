package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private final static String menssagemErro = "Não foi encontrado um usuário com o id %s";

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(String.format(menssagemErro, id));
    }

}
