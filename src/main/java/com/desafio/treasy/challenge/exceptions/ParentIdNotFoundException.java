package com.desafio.treasy.challenge.exceptions;

public class ParentIdNotFoundException extends RuntimeException {
    public String getMessage() {
        return "ParentId doesn't exist!";
    }
}
