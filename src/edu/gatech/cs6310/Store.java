package edu.gatech.cs6310;

import edu.gatech.cs6310.exceptions.*;

import java.util.TreeMap;

public class Store {

    private final static String ITEM_ALREADY_EXISTS = "item_identifier_already_exists";
    private final static String ITEM_DOES_NOT_EXIST = "item_identifier_does_not_exist";
    private final static String DRONE_ALREADY_EXISTS = "drone_identifier_already_exists";
    private final static String DRONE_DOES_NOT_EXIST = "drone_identifier_does_not_exist";
    private final static String ORDER_ALREADY_EXISTS = "order_identifier_already_exists";
    private final static String ORDER_DOES_NOT_EXIST = "order_identifier_does_not_exist";
    private final static String CUSTOMER_DOES_NOT_EXIST = "customer_identifier_does_not_exist";

    private final String name;
    private float revenue;
    private final TreeMap<String, Item> catalog;
    private final TreeMap<String, Drone> drones;
    private final TreeMap<String, Order> orders;

    public Store(final String name, final float revenue) {
        this.name = name;
        this.revenue = revenue;
        this.catalog = new TreeMap<>();
        this.drones = new TreeMap<>();
        this.orders = new TreeMap<>();
    }

    @Override
    public String toString() {
        return String.format("name:%s,revenue:%d", name, (int)revenue);
    }

    public void addItem(final Item item) throws ItemAlreadyExistException {
        String itemName = item.getName();
        if (itemExists(itemName)) {
            throw new ItemAlreadyExistException(ITEM_ALREADY_EXISTS);
        }
        catalog.put(itemName, item);
    }

    public void addDrone(final Drone drone) throws DroneAlreadyExistException {
        String droneId = drone.getId();
        if (droneExists(droneId)) {
            throw new DroneAlreadyExistException(DRONE_ALREADY_EXISTS);
        }
        drones.put(droneId, drone);
    }

    public void addOrder(final String orderId, final String droneId, final Customer customer)
            throws OrderAlreadyExistException, DroneDoesNotExistException, CustomerDoesNotExistException {
        if (orderExists(orderId)) {
            throw new OrderAlreadyExistException(ORDER_ALREADY_EXISTS);
        }
        Drone drone = getDrone(droneId);
        if (customer == null) {
            throw new CustomerDoesNotExistException(CUSTOMER_DOES_NOT_EXIST);
        }
        Order order = new Order(orderId, customer, drone);
        orders.put(orderId, order);
    }

    public TreeMap<String, Item> getCatalog() {
        return catalog;
    }

    public TreeMap<String, Drone> getDrones() {
        return drones;
    }

    public TreeMap<String, Order> getOrders() {
        return orders;
    }

    public Drone getDrone(final String id) throws DroneDoesNotExistException {
        Drone drone = drones.get(id);
        if (drone == null) {
            throw new DroneDoesNotExistException(DRONE_DOES_NOT_EXIST);
        }
        return drone;
    }

    public Order getOrder(final String id) throws OrderDoesNotExistException {
        Order order = orders.get(id);
        if (order == null) {
            throw new OrderDoesNotExistException(ORDER_DOES_NOT_EXIST);
        }
        return order;
    }

    public Item getItem(final String name) throws ItemDoesNotExistException {
        Item item = catalog.get(name);
        if (item == null) {
            throw new ItemDoesNotExistException(ITEM_DOES_NOT_EXIST);
        }
        return item;
    }

    public boolean orderExists(final String id) {
        return orders.containsKey(id);
    }

    public void removeOrder(final String orderId) {
        orders.remove(orderId);
    }

    public void chargeCustomer(final Order order) throws CustomerCantAffordNewItemException {
        final float total = order.getCost();
        order.getCustomer().subtractCredit(total);
        increaseRevenue(total);
    }

    private boolean itemExists(final String name) {
        return catalog.containsKey(name);
    }

    private boolean droneExists(final String id) {
        return drones.containsKey(id);
    }

    private void increaseRevenue(final float amount) {
        revenue += amount;
    }
}
