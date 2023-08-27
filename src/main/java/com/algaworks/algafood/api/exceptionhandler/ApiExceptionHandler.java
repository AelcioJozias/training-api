package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntidadeNaoEncontradaException.class)
  public ResponseEntity<?> tratarEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(NegocioException.class)
  public ResponseEntity<?> tratarNegocioException(NegocioException e, WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(CidadeNaoEncontradaException.class)
  public ResponseEntity<?> tratarCidadeNaoEncontrada(CidadeNaoEncontradaException e, WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(CozinhaNaoEncontradaException.class)
  public ResponseEntity<?> tratarCozinhaNaoEncontradaException(CozinhaNaoEncontradaException e, WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(EntidadeEmUsoException.class)
  public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(EstadoNaoEncontradoException.class)
  public ResponseEntity<?> tratarEstadoNaoEncontradoException(EstadoNaoEncontradoException e, WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(RestauranteNaoEncontradoException.class)
  public ResponseEntity<?> tratarRestauranteNaoEncontradoException(RestauranteNaoEncontradoException e,
      WebRequest request) {
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    if (body == null) {
      body = Problema.builder()
          .dataErro(LocalDateTime.now())
          .menssagem(status.getReasonPhrase())
          .build();
    } else if (body instanceof String) {
      body = Problema.builder()
          .dataErro(LocalDateTime.now())
          .menssagem((String) body)
          .build();
    }

    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

}
