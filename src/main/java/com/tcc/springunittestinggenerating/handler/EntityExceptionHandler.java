package com.tcc.springunittestinggenerating.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.NonUniqueResultException;

import com.tcc.springunittestinggenerating.exceptions.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Classe que faz a internaciolizacao se conectando com o messages.properties.
   */
  @Autowired
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    String mensagemUsr =
        messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
    String mensagemDev = ex.getCause().toString();

    List<Error> erros = Arrays.asList(new Error(mensagemUsr, mensagemDev));
    return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<Error> erros = criarListaDeErros(ex.getBindingResult());
    return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<Object> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex, WebRequest request) {
    String mensagemUsr = messageSource.getMessage("recurso.operacao-nao-permitida", null,
        LocaleContextHolder.getLocale());
    String mensagemDev = ExceptionUtils.getRootCauseMessage(ex);
    List<Error> erros = Arrays.asList(new Error(mensagemUsr, mensagemDev));
    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {
    String mensagemUsr = messageSource.getMessage("recurso.dados-incompletos", null,
        LocaleContextHolder.getLocale());
    String mensagemDev = ExceptionUtils.getRootCauseMessage(ex);
    List<Error> erros = Arrays.asList(new Error(mensagemUsr, mensagemDev));
    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({NonUniqueResultException.class})
  public ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException ex,
      WebRequest request) {
    String mensagemUsr = messageSource.getMessage("recurso.cadastro-duplicado", null,
        LocaleContextHolder.getLocale());
    String mensagemDev = ExceptionUtils.getRootCauseMessage(ex);
    List<Error> erros = Arrays.asList(new Error(mensagemUsr, mensagemDev));
    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({EntityNotFoundException.class})
  public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex,
                                                               WebRequest request) {
    String mensagemUsr = messageSource.getMessage("recurso.nao-encontrado", null,
            LocaleContextHolder.getLocale());
    String mensagemDev = ExceptionUtils.getRootCauseMessage(ex);
    List<Error> erros = Arrays.asList(new Error(mensagemUsr, mensagemDev));
    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  private List<Error> criarListaDeErros(BindingResult bindingResult) {
    List<Error> erros = new ArrayList<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String mensagemUsr = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      String mensagemDev = fieldError.toString();
      erros.add(new Error(mensagemUsr, mensagemDev));
    }
    return erros;
  }

}
