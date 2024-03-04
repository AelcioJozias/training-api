package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "Problema")
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {

  @ApiModelProperty(example = "404")
  private Integer status;

  @ApiModelProperty(example = "2024-01-23T00:22:18.0352197Z", position = 5)
  private OffsetDateTime timeStamp;

  @ApiModelProperty(example = "2024-01-23T00:22:18.0352197Z", position = 10)
  private String type;

  @ApiModelProperty(example = "Recurso não encontrado", position = 15)
  private String title;

  @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faço o preenchimento correto e tente novamente", position = 20)
  private String detail;

  @ApiModelProperty(example = "Ocorreu um erro interno inesperado no sistem. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema", position = 25)
  private String userMassege;

  @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 25)
  private List<Object> fields;

  @ApiModel(value = "ObjetoProblema")
  @Getter
  @Builder
  public static class Object {

    @ApiModelProperty(example = "campoX", position = 5)
    private String name;

    @ApiModelProperty(example = "O campoX é obrigatório", position = 10)
    private String userMessage;
  }

}
