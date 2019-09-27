package com.desafio.treasy.challenge.Exceptions;

public class FatherCantBecomeSonOfHimself extends Exception {
    public FatherCantBecomeSonOfHimself(String errorMessage) {
        super(errorMessage);
    }
}
