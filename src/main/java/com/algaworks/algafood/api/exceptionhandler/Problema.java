package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problema {
  
  private LocalDateTime dataErro;
  private String menssagem;

}
