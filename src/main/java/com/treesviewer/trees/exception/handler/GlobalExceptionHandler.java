package com.treesviewer.trees.exception.handler;

import com.treesviewer.trees.exception.OperationNotSupportedException;
import com.treesviewer.trees.exception.TreeNotFoundException;
import com.treesviewer.trees.exception.UserAlreadyExists;
import com.treesviewer.trees.exception.UserNotFoundException;
import com.treesviewer.trees.exception.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    public ModelAndView handleUserAlreadyExists(final UserAlreadyExists ex) {
        return setUpModel(ex, NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(final UserNotFoundException ex) {
        return setUpModel(ex, NOT_FOUND);
    }

    @ExceptionHandler(TreeNotFoundException.class)
    public ModelAndView handleTreeNotFoundException(final TreeNotFoundException ex) {
        return setUpModel(ex, NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(final ValidationException ex) {
        return setUpModel(ex, BAD_REQUEST);
    }

    @ExceptionHandler(OperationNotSupportedException.class)
    public ModelAndView handleOperationNotSupportedException(final OperationNotSupportedException ex) {
        return setUpModel(ex, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handelUnhandledExceptions(final Exception ex) {
        return setUpModel(ex, INTERNAL_SERVER_ERROR);
    }


    private ModelAndView setUpModel(final Exception ex, final HttpStatus status) {
        final ModelAndView mav = new ModelAndView();
        mav.addObject("timestamp", LocalDateTime.now());
        mav.addObject("status", status);
        mav.addObject("message", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }

}
