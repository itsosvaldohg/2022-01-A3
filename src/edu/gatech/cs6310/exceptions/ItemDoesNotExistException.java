package edu.gatech.cs6310.exceptions;

public class ItemDoesNotExistException extends Exception {
    public ItemDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
