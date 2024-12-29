package com.example.api_rest_clientes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class PhoneAlreadyExistsException extends ResponseStatusException {
    public PhoneAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
