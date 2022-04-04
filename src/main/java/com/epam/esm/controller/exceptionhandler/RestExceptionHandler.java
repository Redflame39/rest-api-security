package com.epam.esm.controller.exceptionhandler;

import com.epam.esm.exception.EntityNotCreatedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityNotUpdatedException;
import com.epam.esm.exception.ExceptionData;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(value = EntityNotCreatedException.class)
    protected ResponseEntity<Object> handleNotCreated(EntityNotCreatedException exception, WebRequest request) {
        log.error("Caught an EntityNotCreatedException, error code 400", exception);
        String message = messageSource.getMessage("exception.not-created", new Object[]{}, request.getLocale());
        ExceptionData exceptionData = new ExceptionData(HttpStatus.BAD_REQUEST.getReasonPhrase(), message,
                exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionData, new HttpHeaders(), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(EntityNotFoundException exception, WebRequest request) {
        log.error("Caught an EntityNotFoundException, error code 404", exception);
        String message = messageSource.getMessage("exception.not-found", new Object[]{}, request.getLocale());
        ExceptionData exceptionData = new ExceptionData(HttpStatus.NOT_FOUND.getReasonPhrase(), message,
                exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionData, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityNotUpdatedException.class)
    protected ResponseEntity<Object> handleNotUpdated(EntityNotUpdatedException exception, WebRequest request) {
        log.error("Caught an EntityNotUpdatedException, error code 400", exception);
        String message = messageSource.getMessage("exception.not-updated", new Object[]{}, request.getLocale());
        ExceptionData exceptionData = new ExceptionData(HttpStatus.BAD_REQUEST.getReasonPhrase(), message,
                exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionData, new HttpHeaders(), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Object> handleGeneric(Throwable exception, WebRequest request) {
        log.error("Caught an exception, error code 400", exception);
        String message = messageSource.getMessage("exception.generic", new Object[]{}, request.getLocale());
        ExceptionData exceptionData = new ExceptionData(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message,
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
