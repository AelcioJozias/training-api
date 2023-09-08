package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
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
  private LocalDateTime timeStamp;
  private String type;
  private String title;
  private String detail;
  private String userMassege;
  private List<Fields> fields;

  @Getter
  @Builder
  public static class Fields {

    private String name;
    private String userMessage;
  }

}
