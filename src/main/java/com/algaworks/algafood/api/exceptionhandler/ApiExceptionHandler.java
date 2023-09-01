package com.algaworks.algafood.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String O_CORPO_DA_REQUISICAO_ESTA_INVALIDO_VERIFIQUE_ERRO_DE_SINTAXE = "O corpo da requisição está inválido. Verifique erro de sintaxe";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericsException(Exception ex, WebRequest request) {
    String detail = "Ocorreu um erro interno inesperado no sistem. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    Problem bodyResponse = createProblemBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ProblemType.ERRO_DE_SISTEMA, detail)
        .build();
    return handleExceptionInternal(ex, bodyResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    if (ex instanceof MethodArgumentTypeMismatchException) {
      return handleMethodArgumentTypeMismatchException(
          (MethodArgumentTypeMismatchException) ex, headers, status, request);
    }
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    String url = String.format("O recurso '%s, que você tentou acessar, é inexistente", ex.getRequestURL());
    Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, url).build();

    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    String parametro = ex.getName();
    String valorPassado = String.valueOf(ex.getValue());
    String tipoDoParametroEsperado = ex.getRequiredType().getSimpleName();

    String detalhe = String
        .format("O parâmetro de URL '%s' recebeu o valor '%s', que é do tipo inválido. Corrija e informe"
            + " um valor compatível com o tipo '%s'", parametro, valorPassado, tipoDoParametroEsperado);

    Problem corpoDaMessagem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detalhe).build();

    return handleExceptionInternal(ex, corpoDaMessagem, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    Throwable rootCause = ExceptionUtils.getRootCause(exception);

    if (rootCause instanceof InvalidFormatException) {
      return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
    } else if (rootCause instanceof PropertyBindingException) {
      return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
    }

    ProblemType problemType = ProblemType.MENSAGEM_IMCOMPREENSIVEL;
    String detail = O_CORPO_DA_REQUISICAO_ESTA_INVALIDO_VERIFIQUE_ERRO_DE_SINTAXE;

    Problem problem = createProblemBuilder(status, problemType, detail).build();

    return handleExceptionInternal(exception, problem, headers, status, request);
  }

  private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    String path = ex.getPath().stream().map(Reference::getFieldName).collect(Collectors.joining("."));

    String detail = String.format("A propriedade '%s' não existe. "
        + "Corrija ou remova essa propriedade e tente novamente.", path);

    Problem body = createProblemBuilder(status, ProblemType.MENSAGEM_IMCOMPREENSIVEL, detail).build();

    return handleExceptionInternal(ex, body, headers, status, request);
  }

  private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    String path = ex.getPath().stream()
        .map(ref -> ref.getFieldName())
        .collect(Collectors.joining("."));

    String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é do tipo inválido. "
        + "Corrija e informe o valor compatível com o tipo '%s'", path, ex.getValue(),
        ex.getTargetType().getSimpleName());

    Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_IMCOMPREENSIVEL, detail).build();

    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  @ExceptionHandler(EntidadeNaoEncontradaException.class)
  public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request) {

    HttpStatus status = HttpStatus.NOT_FOUND;

    Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, e.getMessage()).build();

    return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(NegocioException.class)
  public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {

    HttpStatus status = HttpStatus.BAD_REQUEST;

    Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, e.getMessage()).build();

    return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(EntidadeEmUsoException.class)
  public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
    HttpStatus status = HttpStatus.CONFLICT;

    Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, e.getMessage()).build();

    return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    if (body == null) {
      body = Problem.builder()
          .title(status.getReasonPhrase())
          .status(status.value())
          .build();
    } else if (body instanceof String) {
      body = Problem.builder()
          .title((String) body)
          .status(status.value())
          .build();
    }

    return super.handleExceptionInternal(exception, body, headers, status, request);
  }

  private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
    return Problem
        .builder()
        .status(status.value())
        .title(problemType.getTitle())
        .type(problemType.getUri())
        .detail(detail);
  }

}
