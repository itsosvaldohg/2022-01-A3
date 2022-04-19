package edu.gatech.cs6310.exceptions;

public class OrderDoesNotExistException extends Exception {
    public OrderDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
