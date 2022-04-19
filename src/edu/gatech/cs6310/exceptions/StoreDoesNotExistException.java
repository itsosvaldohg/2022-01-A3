package edu.gatech.cs6310.exceptions;

public class StoreDoesNotExistException extends Exception {
    public StoreDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
