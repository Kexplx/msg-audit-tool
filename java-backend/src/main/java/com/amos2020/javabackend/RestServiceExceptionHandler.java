package com.amos2020.javabackend;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;

@Component
@RestControllerAdvice
public class RestServiceExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    protected ModelAndView handleValidationException(
            ValidationException ex, HttpServletRequest request, HttpServletResponse response, @Nullable Object handler)
            throws IOException {
        System.out.println("In CustomExceptionHandlerResolver");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return new ModelAndView();
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ModelAndView handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request, HttpServletResponse response, @Nullable Object handler)
            throws IOException {
        System.out.println("In CustomExceptionHandlerResolver");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return new ModelAndView();
    }
}
