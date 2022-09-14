package com.treesviewer.trees.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({Throwable.class})
    public String exception(final Throwable throwable, final Model model) {
        final String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        final Throwable cause = throwable.getCause();
        final StackTraceElement[] stackTrace = throwable.getStackTrace();
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("cause", cause);
        model.addAttribute("stack", stackTrace);
        return "";
    }

}
