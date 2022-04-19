package edu.gatech.cs6310;

import edu.gatech.cs6310.exceptions.DroneCantCarryNewItemException;
import edu.gatech.cs6310.exceptions.DroneNeedsFuelException;
import edu.gatech.cs6310.exceptions.DroneNeedsPilotException;


public class Drone {

    private final String DRONE_CANT_CARRY_NEW_ITEM = "drone_cant_carry_new_item";
    private final String DRONE_NEEDS_PILOT = "drone_needs_pilot";
    private final String DRONE_NEEDS_FUEL = "drone_needs_fuel";

    private final String id;
    private final float weightCapacity;
    private float payload;
    private float remainingCapacity;
    private int remainingTrips;
    private DronePilot pilot;
    private int numberOfOrders;

    public Drone(final String id, final float weightCapacity, final int remainingTrips) {
        this.id = id;
        this.weightCapacity = weightCapacity;
        this.remainingTrips = remainingTrips;
        this.pilot = null;
        this.payload = 0;
        this.remainingCapacity = weightCapacity;
        this.numberOfOrders = 0;
    }

    public String getId() {
        return id;
    }

    public DronePilot getPilot() {
        return pilot;
    }

    public void clearPilot() {
        setPilot(null);
    }

    public void freeCurrentPilot() {
        if (pilot == null) {
            return;
        }
        pilot.clearDrone();
        clearPilot();
    }

    public void setPilot(DronePilot pilot) {
        this.pilot = pilot;
    }

    @Override
    public String toString() {
        if (pilot != null) {
            return String.format("droneID:%s,total_cap:%d,num_orders:%d,remaining_cap:%d,trips_left:%d,flown_by:%s_%s",
                    id, (int)weightCapacity, numberOfOrders, (int)remainingCapacity, remainingTrips, pilot.firstName, pilot.lastName);
        }
        return String.format("droneID:%s,total_cap:%d,num_orders:%d,remaining_cap:%d,trips_left:%d",
                id, (int)weightCapacity,numberOfOrders, (int)remainingCapacity, remainingTrips);
    }

    public void load(final float weight) throws DroneCantCarryNewItemException {
        if (!canCarryWeight(weight)) {
            throw new DroneCantCarryNewItemException(DRONE_CANT_CARRY_NEW_ITEM);
        }
        payload += weight;
        remainingCapacity -= weight;
    }

    public void deliverOrder(Order order) throws DroneNeedsPilotException, DroneNeedsFuelException {
        if (pilot == null) {
            throw new DroneNeedsPilotException(DRONE_NEEDS_PILOT);
        }
        if (remainingTrips < 1) {
            throw new DroneNeedsFuelException(DRONE_NEEDS_FUEL);
        }
        remainingTrips--;
        pilot.increaseExperience();
        decreaseOrders();
        unload(order.getWeight());
    }

    public void increaseOrders() {
        numberOfOrders++;
    }

    public void decreaseOrders() {
        numberOfOrders--;
    }

    public boolean canCarryWeight(final float weight) {
        return weight <= remainingCapacity;
    }

    public void unload(final float weight) {
        payload -= weight;
        remainingCapacity += weight;
    }
}
