package edu.gatech.cs6310;

import edu.gatech.cs6310.exceptions.CustomerCantAffordNewItemException;
import edu.gatech.cs6310.exceptions.DroneCantCarryNewItemException;
import edu.gatech.cs6310.exceptions.ItemAlreadyOrderedException;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class Order {

    private final String ITEM_ALREADY_ORDERED = "item_already_ordered";
    private final String CUSTOMER_CANT_AFFORD_NEW_ITEM = "customer_cant_afford_new_item";
    private final String DRONE_CANT_CARRY_NEW_ITEM = "drone_cant_carry_new_item";

    private final String id;
    private final TreeMap<String, Line> lines;
    private final Set<String> items;
    private final Customer customer;
    private final Drone drone;
    private float cost;
    private float weight;

    public Order(final String id, final Customer customer, final Drone drone) {
        this.id = id;
        this.customer = customer;
        this.drone = drone;
        this.lines = new TreeMap<>();
        this.cost = 0;
        this.items = new HashSet<>();
        drone.increaseOrders();
    }

    @Override
    public String toString() {
        return String.format("orderID:%s", id);
    }

    public String getId() {
        return id;
    }

    public TreeMap<String, Line> getLines() {
        return lines;
    }

    public Drone getDrone() {
        return drone;
    }

    public float getCost() {
        return cost;
    }

    public float getWeight() {
        return weight;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addLine(final Item item, final int quantity, final float price)
            throws ItemAlreadyOrderedException, CustomerCantAffordNewItemException, DroneCantCarryNewItemException {
        final String itemName = item.getName();
        final float totalCost = quantity * price;
        final float totalWeight = quantity * item.getWeight();
        validateItem(itemName);
        validateCustomerCredit(cost + totalCost);
        validateDroneCapacity(totalWeight);
        addLineToDrone(totalWeight);
        final Line line = new Line(item, quantity, totalWeight, totalCost);
        lines.put(itemName, line);
        items.add(itemName);
        this.cost += totalCost;
        this.weight += totalWeight;
    }

    public void cancel() {
        drone.decreaseOrders();
        drone.unload(weight);
    }

    private void validateItem(final String itemName) throws ItemAlreadyOrderedException {
        if (items.contains(itemName)) {
            throw new ItemAlreadyOrderedException(ITEM_ALREADY_ORDERED);
        }
    }

    private void validateCustomerCredit(final float amount) throws CustomerCantAffordNewItemException {
        if (!customer.canAffordAmount(amount)) {
            throw new CustomerCantAffordNewItemException(CUSTOMER_CANT_AFFORD_NEW_ITEM);
        }
    }

    private void validateDroneCapacity(final float weight) throws DroneCantCarryNewItemException {
        if(!drone.canCarryWeight(weight)) {
            throw new DroneCantCarryNewItemException(DRONE_CANT_CARRY_NEW_ITEM);
        }
    }

    private void addLineToDrone(final float weight) throws DroneCantCarryNewItemException {
        drone.load(weight);
    }
}
