package edu.gatech.cs6310;

import java.util.Scanner;

public class DeliveryServiceCLI {

    private final DeliveryService service;

    public DeliveryServiceCLI() {
        service = new DeliveryService();
    }

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                if (wholeInputLine.charAt(0) == '/' && wholeInputLine.charAt(1) == '/') {
                    continue;
                } else if (tokens[0].equals("make_store")) {
                    service.makeStore(tokens[1], Float.parseFloat(tokens[2]));

                } else if (tokens[0].equals("display_stores")) {
                    service.displayStores();

                } else if (tokens[0].equals("sell_item")) {
                    service.sellItem(tokens[1], tokens[2], Float.parseFloat(tokens[3]));

                } else if (tokens[0].equals("display_items")) {
                    service.displayItems(tokens[1]);

                } else if (tokens[0].equals("make_pilot")) {
                    service.makePilot(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6],
                            Integer.parseInt(tokens[7]));

                } else if (tokens[0].equals("display_pilots")) {
                    service.displayPilots();

                } else if (tokens[0].equals("make_drone")) {
                    service.makeDrone(tokens[1], tokens[2], Float.parseFloat(tokens[3]), Integer.parseInt(tokens[4]));

                } else if (tokens[0].equals("display_drones")) {
                    service.displayDrones(tokens[1]);

                } else if (tokens[0].equals("fly_drone")) {
                    service.flyDrone(tokens[1], tokens[2], tokens[3]);

                } else if (tokens[0].equals("make_customer")) {
                    service.makeCustomer(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]),
                            Double.parseDouble(tokens[6]));

                } else if (tokens[0].equals("display_customers")) {
                    service.displayCustomers();

                } else if (tokens[0].equals("start_order")) {
                    service.startOrder(tokens[1], tokens[2], tokens[3], tokens[4]);

                } else if (tokens[0].equals("display_orders")) {
                    service.displayOrders(tokens[1]);

                } else if (tokens[0].equals("request_item")) {
                    service.requestItem(tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]),
                            Float.parseFloat(tokens[5]));

                } else if (tokens[0].equals("purchase_order")) {
                    service.purchaseOrder(tokens[1], tokens[2]);

                } else if (tokens[0].equals("cancel_order")) {
                    service.cancelOrder(tokens[1], tokens[2]);

                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
