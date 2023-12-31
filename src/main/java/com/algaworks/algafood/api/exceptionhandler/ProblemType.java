package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

  MENSAGEM_IMCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
  RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
  ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
  ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
  PARAMETRO_INVALIDO("/parametro-invaliado", "Parâmetro inválido"),
  ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
  DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

  private String title;
  private String uri;

  ProblemType(String path, String title) {
    this.title = title;

    this.uri = "https://algafood.com.br" + path;
  }

}
