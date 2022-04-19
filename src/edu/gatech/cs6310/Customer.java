package edu.gatech.cs6310;

import edu.gatech.cs6310.exceptions.CustomerCantAffordNewItemException;


public class Customer extends User {

    private final String CUSTOMER_CANT_AFFORD_NEW_ITEM = "customer_cant_afford_new_item";

    private final String account;
    private int rating;
    private double credits;

    public Customer(final String account, final String firstName, final String lastName, final String phoneNumber,
                    final int rating, final double credits) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.credits = credits;
    }

    @Override
    public String toString() {
        return String.format("name:%s_%s,phone:%s,rating:%d,credit:%d",
                firstName, lastName, phoneNumber, rating, (int)credits);
    }

    public void subtractCredit(final float amount) throws CustomerCantAffordNewItemException {
        if (!canAffordAmount(amount)) {
            throw new CustomerCantAffordNewItemException(CUSTOMER_CANT_AFFORD_NEW_ITEM);
        }
        credits -= amount;
    }

    public boolean canAffordAmount(final float amount) {
        return amount <= credits;
    }

}
