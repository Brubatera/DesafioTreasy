package com.desafio.treasy.challenge.exceptions;

public class FatherCantBecomeSonOfHimself extends Exception {
    public FatherCantBecomeSonOfHimself(String errorMessage) {
        super(errorMessage);
    }
}
