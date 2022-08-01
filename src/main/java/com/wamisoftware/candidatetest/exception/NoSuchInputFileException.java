package com.wamisoftware.candidatetest.exception;

public class NoSuchInputFileException extends Exception {

    public NoSuchInputFileException() {
    }

    public NoSuchInputFileException(String message) {
        super(message);
    }

    public NoSuchInputFileException(String message, Throwable t) {
        super(message, t);
    }
}
