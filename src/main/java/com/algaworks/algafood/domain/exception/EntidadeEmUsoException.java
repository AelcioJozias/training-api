package com.algaworks.algafood.domain.exception;

public class EntidadeEmUsoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	private static final String menssagemErroGerica = "Não foi possível excluir o recurso de id %s, pois está em uso. (Foreign Key)";

	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

	public EntidadeEmUsoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public EntidadeEmUsoException(Long id) {
		this(String.format(menssagemErroGerica, id));
	}
}
