```java
package menus;

import models.*;
import services.*;
import storage.DataStore;

import java.util.List;
import java.util.Scanner;

public class DealerMenu {

    private Dealer dealer;
    private CarService carService = new CarService();
    private OfferService offerService = new OfferService();
    private MessageService messageService = new MessageService();
    private DataStore ds = DataStore.getInstance();
    private Scanner sc;

    public DealerMenu(Dealer dealer, Scanner sc) {
        this.dealer = dealer;
        this.sc = sc;
    }

    public void show() {

        boolean running = true;

        while (running) {

            System.out.println("\n--- Dealer Dashboard ---");
            System.out.println("Welcome, " + dealer.getName() + "!");

            System.out.println("1. Add Car to Inventory");
            System.out.println("2. View My Inventory");
            System.out.println("3. Update Car Price");
            System.out.println("4. Remove Car");
            System.out.println("5. View Offers");
            System.out.println("6. Accept / Reject Offer");
            System.out.println("7. View Conversations");
            System.out.println("8. Send Message");
            System.out.println("9. My Profile");
            System.out.println("0. Logout");

            System.out.print("Enter choice: ");

            String input = sc.nextLine();

            switch (input) {

                case "1" -> addCar();
                case "2" -> viewInventory();
                case "3" -> updateCar();
                case "4" -> removeCar();
                case "5" -> viewOffers();
                case "6" -> manageOffer();
                case "7" -> viewMessages();
                case "8" -> sendMessage();
                case "9" -> dealer.displayProfile();
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
                    dealer.getDealerId()
            );

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- VIEW INVENTORY ----------------

    private void viewInventory() {

        System.out.println("\n-- My Inventory --");

        List<Car> cars = carService.getCarsByOwner(dealer.getDealerId());

        if (cars.isEmpty()) {
            System.out.println("No cars available.");
            return;
        }

        for (Car c : cars)
            c.displayCarDetails();
    }

    // ---------------- UPDATE CAR ----------------

    private void updateCar() {
        System.out.println("\n-- Your Cars --");
        viewInventory();

        try {

            System.out.print("\nEnter Car ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            Car car = carService.getCarById(id);

            if(car == null) {
                System.out.println("Car not found");
                return;
            }

            System.out.println("\nPress ENTER to keep current value");

            System.out.print("Brand (" + car.getBrand() + "):");
            String brand = sc.nextLine();
            if (!brand.isEmpty()) car.setBrand(brand);

            System.out.print("Model (" + car.getModel() + "):");
            String model = sc.nextLine();
            if (!model.isEmpty()) car.setModel(model);

            System.out.print("Year (" + car.getYear() + "):");
            String yearInput = sc.nextLine();
            if (!yearInput.isEmpty())
                car.setYear(Integer.parseInt(yearInput));

            System.out.print("Fuel (" + car.getFuelType() + "):");
            String fuel = sc.nextLine();
            if (!fuel.isEmpty()) car.setFuelType(fuel);

            System.out.print("Condition (" + car.getCondition() + "): ");
            String condition = sc.nextLine();

            if (!condition.isEmpty())
                car.setCondition(condition);
            System.out.print("Price (" + car.getPrice() + "): ");
            String priceInput = sc.nextLine();
            if (!priceInput.isEmpty())
                car.setPrice(Double.parseDouble(priceInput));
            System.out.println("\nCar updated successfully.");
        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- REMOVE CAR ----------------

    private void removeCar() {

        viewInventory();

        try {

            System.out.print("\nEnter Car ID to remove: ");
            int id = Integer.parseInt(sc.nextLine());

            carService.removeCar(id);

            System.out.println("Car removed successfully.");

        }

        catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    // ---------------- VIEW OFFERS ----------------

    private void viewOffers() {

        List<Offer> offers = offerService.getOffersForSeller(dealer.getDealerId());

        if (offers.isEmpty()) {
            System.out.println("No offers yet.");
            return;
        }

        System.out.println("\n-- Offers on Your Cars --");

        for (Offer o : offers)
            o.displayOffer();
    }

    // ---------------- ACCEPT / REJECT OFFER ----------------

    private void manageOffer() {

        viewOffers();

        try {

            System.out.print("\nEnter Offer ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.println("1. Accept Offer");
            System.out.println("2. Reject Offer");

            System.out.print("Choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1)
                offerService.acceptOffer(id);
            else if (choice == 2)
                offerService.rejectOffer(id);
            else
                System.out.println("Invalid choice.");

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- VIEW CONVERSATIONS ----------------

    private void viewMessages() {

        List<User> contacts = messageService.getContacts(dealer.getDealerId());

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
                    dealer.getDealerId(),
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

            if (u.getUserId() != dealer.getDealerId()) {

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
                    dealer.getDealerId(),
                    id,
                    dealer.getName(),
                    text
            );

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}
```
