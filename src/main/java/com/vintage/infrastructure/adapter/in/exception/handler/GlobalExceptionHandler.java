package com.vintage.infrastructure.adapter.in.exception.handler;

import com.vintage.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(final ResourceNotFoundException e,
                                                                         final HttpServletRequest request) {

        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setInstance(URI.create(request.getServletPath()));

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(final HandlerMethodValidationException e,
                                                                            final HttpHeaders headers,
                                                                            final HttpStatusCode status,
                                                                            final WebRequest request) {

        final String errorMessage = e.getAllErrors().get(0).getDefaultMessage();
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getServletPath()));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}