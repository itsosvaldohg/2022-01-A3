package edu.gatech.cs6310.exceptions;

public class ItemAlreadyOrderedException extends Exception {
    public ItemAlreadyOrderedException(String errorMessage) {
        super(errorMessage);
    }
}
