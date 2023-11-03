package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaExcpetion extends EntidadeNaoEncontradaException {


    public static final String NAO_FOI_ENCONTRADO_UMA_PERMISSAO_COM_O_ID_X = "Não foi encontrado uma permissão com o id %s";

    public PermissaoNaoEncontradaExcpetion(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaExcpetion(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public PermissaoNaoEncontradaExcpetion(Long id) {
        this(String.format(NAO_FOI_ENCONTRADO_UMA_PERMISSAO_COM_O_ID_X, id));
    }
}
