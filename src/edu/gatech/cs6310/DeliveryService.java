package edu.gatech.cs6310;
import edu.gatech.cs6310.exceptions.*;

import java.util.*;

public class DeliveryService {

    private final static String STORE_ALREADY_EXISTS = "store_identifier_already_exists";
    private final static String STORE_DOES_NOT_EXIST = "store_identifier_does_not_exist";
    private final static String PILOT_ALREADY_EXISTS = "pilot_identifier_already_exists";
    private final static String PILOT_LICENSE_ALREADY_EXISTS = "pilot_license_already_exists";
    private final static String PILOT_DOES_NOT_EXIST = "pilot_identifier_does_not_exist";
    private final static String CUSTOMER_ALREADY_EXISTS = "customer_identifier_already_exists";
    private final static String CUSTOMER_DOES_NOT_EXIST = "customer_identifier_does_not_exist";

    private final TreeMap<String, Store> stores;
    private final TreeMap<String, DronePilot> dronePilots;
    private final Set<String> pilotLicences;
    private final TreeMap<String, Customer> customers;

    public DeliveryService() {
        stores = new TreeMap<>();
        dronePilots = new TreeMap<>();
        pilotLicences = new HashSet<>();
        customers = new TreeMap<>();
    }

    public void makeStore(final String name, final float revenue) {
        if (storeExists(name)) {
            showError(STORE_ALREADY_EXISTS);
            return;
        }
        Store store = new Store(name, revenue);
        stores.put(name, store);
        showChangeOK();
    }

    public void displayStores() {
        displayElements(stores);
        showDisplayOK();
    }

    public void sellItem(final String storeName, final String itemName, final float weight) {
        final Item item = new Item(itemName, weight);
        try {
            final Store store = getStore(storeName);
            store.addItem(item);
            showChangeOK();
        }
        catch (StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
        catch (ItemAlreadyExistException e) {
            showError(e.getMessage());
        }
    }

    public void displayItems(final String storeName) {
        try {
            Store store = getStore(storeName);
            displayElements(store.getCatalog());
            showDisplayOK();
        }
        catch (StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
    }

    public void makePilot(final String account, final String firstName, final String lastName,
                          final String phoneNumber, final String taxID, final String licenseID,
                          final int numberOfDeliveries) {
        if (pilotExists(account)) {
            showError(PILOT_ALREADY_EXISTS);
            return;
        }
        if (licenseExists(licenseID)) {
            showError(PILOT_LICENSE_ALREADY_EXISTS);
            return;
        }
        DronePilot pilot = new DronePilot(account, firstName, lastName, phoneNumber, taxID, licenseID, numberOfDeliveries);
        dronePilots.put(account, pilot);
        pilotLicences.add(licenseID);
        showChangeOK();
    }

    public void displayPilots() {
        displayElements(dronePilots);
        showDisplayOK();
    }

    public void makeDrone(final String storeName, final String id, final float weightCapacity,
                          final int remainingTrips) {
        try {
            final Store store = getStore(storeName);
            final Drone drone = new Drone(id, weightCapacity, remainingTrips);
            store.addDrone(drone);
            showChangeOK();
        }
        catch (StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
        catch (DroneAlreadyExistException e) {
            showError(e.getMessage());
        }
    }

    public void displayDrones(final String storeName) {
        Store store = null;
        try {
            store = getStore(storeName);
            displayElements(store.getDrones());
            showDisplayOK();
        }
        catch (StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
    }

    public void flyDrone(final String storeName, final String droneId, final String pilotAccount) {
        try {
            Store store = getStore(storeName);
            Drone drone = store.getDrone(droneId);
            DronePilot pilot = getDronePilot(pilotAccount);
            pilot.freeCurrentDrone();
            drone.freeCurrentPilot();
            pilot.setDrone(drone);
            drone.setPilot(pilot);
            showChangeOK();
        }
        catch (StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
        catch (DroneDoesNotExistException e) {
            showError(e.getMessage());
        }
        catch (DronePilotDoesNotExistException e) {
            showError(e.getMessage());
        }
    }

    public void makeCustomer(final String account, final String firstName, final String lastName,
                             final String phoneNumber, final int rating, final double credits) {
        if (customerExists(account)) {
            showError(CUSTOMER_ALREADY_EXISTS);
            return;
        }
        Customer customer = new Customer(account, firstName, lastName, phoneNumber, rating, credits);
        customers.put(account, customer);
        showChangeOK();
    }

    public void displayCustomers() {
        displayElements(customers);
        showDisplayOK();
    }

    public void startOrder(final String storeName, final String orderId, final String droneId,
                           final String customerAccount) {
        try {
            Store store = getStore(storeName);
            //Hack to comply with the error priority
            Customer customer = customerExists(customerAccount) ? getCustomer(customerAccount) : null;
            store.addOrder(orderId, droneId, customer);
            showChangeOK();
        }
        catch (StoreDoesNotExistException|OrderAlreadyExistException e) {
            showError(e.getMessage());
        }
        catch (DroneDoesNotExistException e) {
            showError(e.getMessage());
        }
        catch (CustomerDoesNotExistException e) {
            showError(e.getMessage());
        }
    }

    public void displayOrders(final String storeName) {
        try {
            Store store = getStore(storeName);
            TreeMap<String, Order> orders = store.getOrders();
            orders.forEach((id, order) -> {
                System.out.println(order);
                displayElements(order.getLines());
            });
            showDisplayOK();
        }
        catch (StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
    }

    public void requestItem(final String storeName, final String orderId, final String itemName, final int quantity,
                            final float price) {
        try {
            Store store = getStore(storeName);
            Order order = store.getOrder(orderId);
            Item item = store.getItem(itemName);
            order.addLine(item, quantity, price);
            showChangeOK();
        }
        catch (StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
        catch (OrderDoesNotExistException|ItemDoesNotExistException e) {
            showError(e.getMessage());
        } catch (ItemAlreadyOrderedException e) {
            showError(e.getMessage());
        } catch (DroneCantCarryNewItemException e) {
            showError(e.getMessage());
        } catch (CustomerCantAffordNewItemException e) {
            showError(e.getMessage());
        }
    }

    public void purchaseOrder(final String storeName, final String orderId) {
        try {
            Store store = getStore(storeName);
            Order order = store.getOrder(orderId);
            Drone drone = order.getDrone();
            drone.deliverOrder(order);
            store.chargeCustomer(order);
            removeOrder(store, order);
            showChangeOK();
        } catch (StoreDoesNotExistException | OrderDoesNotExistException | DroneNeedsPilotException |
                DroneNeedsFuelException | CustomerCantAffordNewItemException e) {
            showError(e.getMessage());
        }
    }

    public void cancelOrder(final String storeName, final String orderId) {
        try {
            Store store = getStore(storeName);
            Order order = store.getOrder(orderId);
            order.cancel();
            removeOrder(store, order);
            showChangeOK();
        } catch (OrderDoesNotExistException | StoreDoesNotExistException e) {
            showError(e.getMessage());
        }
    }

    private boolean storeExists(final String name) {
        return stores.containsKey(name);
    }

    private Store getStore(final String name) throws StoreDoesNotExistException {
        final Store store = stores.get(name);
        if (store == null) {
            throw new StoreDoesNotExistException(STORE_DOES_NOT_EXIST);
        }
        return store;
    }

    private boolean pilotExists(final String account) {
        return dronePilots.containsKey(account);
    }

    private boolean licenseExists(final String licenseID) {
        return pilotLicences.contains(licenseID);
    }

    private DronePilot getDronePilot(final String account) throws DronePilotDoesNotExistException {
        final DronePilot pilot = dronePilots.get(account);
        if (pilot == null) {
            throw new DronePilotDoesNotExistException(PILOT_DOES_NOT_EXIST);
        }
        return pilot;
    }

    private <K, V> void displayElements(Map<K, V> map) {
        map.values().forEach((element) -> {System.out.println(element);});
    }

    private boolean customerExists(final String account) {
        return customers.containsKey(account);
    }

    private Customer getCustomer(final String account) throws CustomerDoesNotExistException {
        Customer customer = customers.get(account);
        if (customer == null) {
            throw new CustomerDoesNotExistException(CUSTOMER_DOES_NOT_EXIST);
        }
        return customer;
    }

    private void removeOrder(final Store store, final Order order) {
        store.removeOrder(order.getId());
    }

    private void showError(final String error) {
        System.out.println("ERROR:" + error);
    }

    private void showChangeOK() {
        System.out.println("OK:change_completed");
    }

    private void showDisplayOK() {
        System.out.println("OK:display_completed");
    }
}
