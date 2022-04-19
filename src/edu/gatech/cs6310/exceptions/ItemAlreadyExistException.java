package edu.gatech.cs6310.exceptions;

public class ItemAlreadyExistException extends Exception {
    public ItemAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
