package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(EntidadeNaoEncontradaException.class)
  public ResponseEntity<?> tratarEntidadeNaoEncontrada(EntidadeNaoEncontradaException e) {

    Problema problema = obterMensagemDoProblema(e);

    // vai retornar o toString de problema??
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);

  }

  @ExceptionHandler(NegocioException.class)
  public ResponseEntity<?> tratarNegocioException(NegocioException e){
    Problema problema = obterMensagemDoProblema(e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
  }

  @ExceptionHandler(CidadeNaoEncontradaException.class)
  public ResponseEntity<?> tratarCidadeNaoEncontrada(CidadeNaoEncontradaException e) {
    Problema problema = obterMensagemDoProblema(e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
  }

  @ExceptionHandler(CozinhaNaoEncontradaException.class)
  public ResponseEntity<?> tratarCozinhaNaoEncontradaException(CozinhaNaoEncontradaException e) {
    Problema problema = obterMensagemDoProblema(e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
  }

  @ExceptionHandler(EntidadeEmUsoException.class)
  public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException e) {
    Problema problema = obterMensagemDoProblema(e);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
  }

  @ExceptionHandler(EstadoNaoEncontradoException.class)
  public ResponseEntity<?> tratarEstadoNaoEncontradoException(EstadoNaoEncontradoException e) {
    Problema problema = obterMensagemDoProblema(e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
  }

  @ExceptionHandler(RestauranteNaoEncontradoException.class)
  public ResponseEntity<?> tratarRestauranteNaoEncontradoException(RestauranteNaoEncontradoException e) {
    Problema problema = obterMensagemDoProblema(e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
  }

  private Problema obterMensagemDoProblema(Exception e) {
    Problema problema = Problema.builder()
        .dataErro(LocalDateTime.now())
        .menssagem(e.getMessage())
        .build();
    return problema;
  }
}
