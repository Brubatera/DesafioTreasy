package com.desafio.treasy.challenge.exceptions;

public class IdNotFoundException extends RuntimeException {
    public String getMessage() {
        return "Id not found!";
    }
}
