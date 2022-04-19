package edu.gatech.cs6310.exceptions;

public class DroneNeedsPilotException extends Exception {
    public DroneNeedsPilotException(String errorMessage) {
        super(errorMessage);
    }
}
