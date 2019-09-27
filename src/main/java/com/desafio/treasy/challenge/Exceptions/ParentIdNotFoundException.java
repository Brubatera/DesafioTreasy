package com.desafio.treasy.challenge.Exceptions;

public class ParentIdNotFoundException extends RuntimeException {
    public String getMessage() {
        return "ParentId doesn't exist!";
    }
}
