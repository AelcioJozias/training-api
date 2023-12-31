package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final String NAO_EXISTE_UMA_FORMA_DE_PAGAMENTO_COM_O_ID = "Não existe uma forma de pagamento com o id %s";

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format(NAO_EXISTE_UMA_FORMA_DE_PAGAMENTO_COM_O_ID, id));
    }
}
