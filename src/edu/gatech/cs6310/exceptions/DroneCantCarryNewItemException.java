package edu.gatech.cs6310.exceptions;

public class DroneCantCarryNewItemException extends Exception {
    public DroneCantCarryNewItemException(String errorMessage) {
        super(errorMessage);
    }
}
