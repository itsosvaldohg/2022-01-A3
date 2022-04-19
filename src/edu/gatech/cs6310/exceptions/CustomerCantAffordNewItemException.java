package edu.gatech.cs6310.exceptions;

public class CustomerCantAffordNewItemException extends Exception{
    public CustomerCantAffordNewItemException(String errorMessage) {
        super(errorMessage);
    }
}
