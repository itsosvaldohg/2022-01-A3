package edu.gatech.cs6310;

public class DronePilot extends Employee {

    private final String account;
    private final String licenseID;
    private int numberOfDeliveries;
    private Drone drone;

    public DronePilot(final String account, final String firstName, final String lastName, final String phoneNumber,
                      final String taxID, final String licenseID, final int numberOfDeliveries) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.taxID = taxID;
        this.licenseID = licenseID;
        this.numberOfDeliveries = numberOfDeliveries;
        this.drone = null;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public void clearDrone() {
        setDrone(null);
    }

    public void freeCurrentDrone() {
        if (drone == null) {
            return;
        }
        drone.clearPilot();
        clearDrone();
    }

    public void increaseExperience() {
        numberOfDeliveries++;
    }

    @Override
    public String toString() {
        return String.format("name:%s_%s,phone:%s,taxID:%s,licenseID:%s,experience:%d",
                firstName, lastName, phoneNumber, taxID, licenseID, numberOfDeliveries);
    }

}
