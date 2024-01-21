package br.com.stoom.store.exceptions;

import br.com.stoom.store.dto.ErrorOutputDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ErrorOutputDTO(ex.getStatus(), ex.getMessage(), ex.getDetails()),
                new HttpHeaders(),
                HttpStatus.valueOf(ex.getStatus()),
                request
        );
    }

}
