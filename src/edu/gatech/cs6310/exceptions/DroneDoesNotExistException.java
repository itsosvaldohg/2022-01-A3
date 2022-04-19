package edu.gatech.cs6310.exceptions;

public class DroneDoesNotExistException extends Exception {
    public DroneDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
