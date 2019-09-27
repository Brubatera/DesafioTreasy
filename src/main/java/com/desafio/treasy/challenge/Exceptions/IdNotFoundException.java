package com.desafio.treasy.challenge.Exceptions;

public class IdNotFoundException extends RuntimeException {
    public String getMessage() {
        return "Id not found!";
    }
}
