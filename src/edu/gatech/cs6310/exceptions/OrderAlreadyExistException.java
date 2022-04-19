package edu.gatech.cs6310.exceptions;

public class OrderAlreadyExistException extends Exception {
    public OrderAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
