package edu.gatech.cs6310.exceptions;

public class DroneNeedsFuelException extends Exception {
    public DroneNeedsFuelException(String errorMessage) {
        super(errorMessage);
    }
}
