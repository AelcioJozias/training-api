package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {

  private Integer status;
  private OffsetDateTime timeStamp;
  private String type;
  private String title;
  private String detail;
  private String userMassege;
  private List<Object> fields;

  @Getter
  @Builder
  public static class Object {

    private String name;
    private String userMessage;
  }

}
