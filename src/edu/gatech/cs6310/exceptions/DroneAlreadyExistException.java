package edu.gatech.cs6310.exceptions;

public class DroneAlreadyExistException extends Exception {
    public DroneAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
