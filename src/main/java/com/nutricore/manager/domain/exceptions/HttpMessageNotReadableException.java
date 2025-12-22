package com.nutricore.manager.domain.exceptions;

public class HttpMessageNotReadableException extends ResourceNotFoundException{

    private static final long serialVersionUID = 1L;

    public HttpMessageNotReadableException(String message) {
        super(message);
    }
}
