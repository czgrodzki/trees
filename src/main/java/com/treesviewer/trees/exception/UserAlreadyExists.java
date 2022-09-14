package com.treesviewer.trees.exception;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(final String message) {
        super(message);
    }
}
