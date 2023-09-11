package com.algaworks.algafood.domain.exception;

import org.springframework.validation.BeanPropertyBindingResult;

import lombok.Getter;

@Getter
public class ValidacaoException extends RuntimeException {

  private BeanPropertyBindingResult bindingResult;

  public ValidacaoException(BeanPropertyBindingResult bindingResult) {

    this.bindingResult = bindingResult;

  }

}
