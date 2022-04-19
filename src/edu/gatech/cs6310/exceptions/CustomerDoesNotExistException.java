package edu.gatech.cs6310.exceptions;

public class CustomerDoesNotExistException extends Exception {
    public CustomerDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
