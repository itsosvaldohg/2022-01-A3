package edu.gatech.cs6310.exceptions;

public class DronePilotDoesNotExistException extends Exception {
    public DronePilotDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
