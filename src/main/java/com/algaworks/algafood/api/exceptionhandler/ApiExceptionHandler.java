package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @Autowired
  private MessageSource messageSource;

  private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistem. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

  private static final String O_CORPO_DA_REQUISICAO_ESTA_INVALIDO_VERIFIQUE_ERRO_DE_SINTAXE = "O corpo da requisição está inválido. Verifique erro de sintaxe";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericsException(Exception ex, WebRequest request) {

    String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    Problem bodyResponse = createProblemBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ProblemType.ERRO_DE_SISTEMA, detail)
        .userMassege(detail)
        .build();
    return handleExceptionInternal(ex, bodyResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity.status(status).headers(headers).build();
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
  }

  private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
                                                          HttpHeaders headers,
                                                          HttpStatus status, WebRequest request) {

    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
        .map(objectError -> {
          String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

          String name = objectError.getObjectName();

          /* aqui estou verificando que não é um erro de objeto, mas sim um field do objeto, é que em algumas situações
          * o erro pode ser o objeto, só que agora de cabeça, não me lembro um exemplo
          * */
          if (objectError instanceof FieldError) {
            name = ((FieldError) objectError).getField();
          }

          return Problem.Object.builder()
              .name(name)
              .userMessage(message)
              .build();
        })
        .collect(Collectors.toList());

    ProblemType problemType = ProblemType.DADOS_INVALIDOS;

    Problem problem = createProblemBuilder(status, problemType, detail)
        .userMassege(detail)
        .fields(problemObjects)
        .build();

    return handleExceptionInternal(ex, problem, headers, status, request);
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
    String detail = String.format("O recurso '%s, que você tentou acessar, é inexistente", ex.getRequestURL());
    Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail)
        .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
        .build();

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

    Problem corpoDaMessagem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detalhe)
        .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
        .build();

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

    Problem problem = createProblemBuilder(status, problemType, detail)
        .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
        .build();

    return handleExceptionInternal(exception, problem, headers, status, request);
  }

  private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    String path = ex.getPath().stream().map(Reference::getFieldName).collect(Collectors.joining("."));

    String detail = String.format("A propriedade '%s' não existe. "
        + "Corrija ou remova essa propriedade e tente novamente.", path);

    Problem body = createProblemBuilder(status, ProblemType.MENSAGEM_IMCOMPREENSIVEL, detail)
        .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
        .build();

    return handleExceptionInternal(ex, body, headers, status, request);
  }

  private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    String path = ex.getPath().stream()
        .map(Reference::getFieldName)
        .collect(Collectors.joining("."));

    String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é do tipo inválido. "
        + "Corrija e informe o valor compatível com o tipo '%s'", path, ex.getValue(),
        ex.getTargetType().getSimpleName());

    Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_IMCOMPREENSIVEL, detail)
        .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
        .build();

    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  @ExceptionHandler(EntidadeNaoEncontradaException.class)
  public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request) {

    HttpStatus status = HttpStatus.NOT_FOUND;

    Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, e.getMessage())
        .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
        .build();

    return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(NegocioException.class)
  public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {

    HttpStatus status = HttpStatus.BAD_REQUEST;

    Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, e.getMessage())
        .userMassege(e.getMessage())
        .build();

    return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(EntidadeEmUsoException.class)
  public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
    HttpStatus status = HttpStatus.CONFLICT;

    Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, e.getMessage())
        .userMassege(e.getMessage())
        .build();

    return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    if (body == null) {
      body = Problem.builder()
          .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
          .title(status.getReasonPhrase())
          .status(status.value())

          .build();
    } else if (body instanceof String) {
      body = Problem.builder()
          .title((String) body)
          .status(status.value())
          .userMassege(MSG_ERRO_GENERICA_USUARIO_FINAL)
          .build();
    }

    return super.handleExceptionInternal(exception, body, headers, status, request);
  }

  private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
    return Problem
        .builder()
        .timeStamp(OffsetDateTime.now())
        .status(status.value())
        .title(problemType.getTitle())
        .type(problemType.getUri())
        .detail(detail);
  }

}
