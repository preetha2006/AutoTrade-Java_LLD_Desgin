package menus;

import models.*;
import services.*;
import storage.DataStore;

import java.util.List;
import java.util.Scanner;

public class SellerMenu {

    private Seller seller;
    private CarService carService = new CarService();
    private OfferService offerService = new OfferService();
    private MessageService messageService = new MessageService();
    private DataStore ds = DataStore.getInstance();
    private Scanner sc;

    public SellerMenu(Seller seller, Scanner sc) {
        this.seller = seller;
        this.sc = sc;
    }

    public void show() {

        boolean running = true;

        while (running) {

            System.out.println("\n--- Seller Dashboard ---");
            System.out.println("Welcome, " + seller.getName() + "!");

            System.out.println("1. Add Car");
            System.out.println("2. View My Cars");
            System.out.println("3. Update Car");
            System.out.println("4. Remove Car");
            System.out.println("5. View Offers on My Cars");
            System.out.println("6. Accept / Reject Offer");
            System.out.println("7. View Messages");
            System.out.println("8. Send Message");
            System.out.println("9. My Profile");
            System.out.println("0. Logout");

            System.out.print("Enter choice: ");

            String input = sc.nextLine();

            switch (input) {

                case "1" -> addCar();
                case "2" -> viewMyCars();
                case "3" -> updateCar();
                case "4" -> removeCar();
                case "5" -> viewOffers();
                case "6" -> manageOffer();
                case "7" -> viewMessages();
                case "8" -> sendMessage();
                case "9" -> seller.displayProfile();
                case "0" -> {
                    System.out.println("Logged out.");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ---------------- ADD CAR ----------------

    private void addCar() {

        try {

            System.out.print("Brand: ");
            String brand = sc.nextLine();

            System.out.print("Model: ");
            String model = sc.nextLine();

            System.out.print("Year: ");
            int year = Integer.parseInt(sc.nextLine());

            System.out.print("Fuel Type: ");
            String fuel = sc.nextLine();

            System.out.print("Condition (NEW / USED): ");
            String cond = sc.nextLine();

            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine());

            carService.addCar(
                    brand,
                    model,
                    year,
                    fuel,
                    cond,
                    price,
                    seller.getSellerId()
            );

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- VIEW MY CARS ----------------

    private void viewMyCars() {

        System.out.println("\n-- My Cars --");

        List<Car> cars = carService.getCarsByOwner(seller.getSellerId());

        if (cars.isEmpty()) {
            System.out.println("No cars listed.");
            return;
        }

        for (Car c : cars)
            c.displayCarDetails();
    }

    // ---------------- UPDATE CAR ----------------

    private void updateCar() {

        viewMyCars();

        try {

            System.out.print("\nEnter Car ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("New Price: ");
            double price = Double.parseDouble(sc.nextLine());

            carService.updateCarPrice(id, price);

            System.out.println("Car updated.");

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- REMOVE CAR ----------------

    private void removeCar() {

        viewMyCars();

        try {

            System.out.print("\nEnter Car ID to remove: ");
            int id = Integer.parseInt(sc.nextLine());

            carService.removeCar(id);

            System.out.println("Car removed.");

        }

        catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    // ---------------- VIEW OFFERS ----------------

    private void viewOffers() {

        List<Offer> offers = offerService.getOffersForSeller(seller.getSellerId());

        if (offers.isEmpty()) {
            System.out.println("No offers yet.");
            return;
        }

        for (Offer o : offers)
            o.displayOffer();
    }

    // ---------------- ACCEPT / REJECT OFFER ----------------

    private void manageOffer() {

        viewOffers();

        try {

            System.out.print("\nEnter Offer ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.println("1. Accept");
            System.out.println("2. Reject");

            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1)
                offerService.acceptOffer(id);
            else if (ch == 2)
                offerService.rejectOffer(id);
            else
                System.out.println("Invalid choice.");

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- VIEW MESSAGES ----------------

    private void viewMessages() {

        List<User> contacts = messageService.getContacts(seller.getSellerId());

        if (contacts.isEmpty()) {
            System.out.println("No conversations yet.");
            return;
        }

        System.out.println("\n-- Conversations --");

        for (User u : contacts)
            System.out.println(u.getUserId() + " - " + u.getName());

        try {

            System.out.print("\nEnter ID to open chat: ");
            int id = Integer.parseInt(sc.nextLine());

            messageService.displayConversation(
                    seller.getSellerId(),
                    id
            );

        }

        catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    // ---------------- SEND MESSAGE ----------------

    private void sendMessage() {

        System.out.println("\n-- Users --");

        for (User u : ds.getUsers()) {

            if (u.getUserId() != seller.getSellerId()) {

                System.out.println(
                        u.getUserId() + " - " +
                        u.getName() + " (" +
                        u.getRole() + ")"
                );
            }
        }

        try {

            System.out.print("\nEnter ID to message: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Message: ");
            String text = sc.nextLine();

            messageService.sendMessage(
                    seller.getSellerId(),
                    id,
                    seller.getName(),
                    text
            );

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}