package menus;

import models.*;
import services.*;
import storage.DataStore;

import java.util.List;
import java.util.Scanner;

public class BuyerMenu {

    private Buyer buyer;
    private CarService carService = new CarService();
    private OfferService offerService = new OfferService();
    private MessageService messageService = new MessageService();
    private PaymentService paymentService = new PaymentService();
    private DataStore ds = DataStore.getInstance();
    private Scanner sc;

    public BuyerMenu(Buyer buyer, Scanner sc) {
        this.buyer = buyer;
        this.sc = sc;
    }

    public void show() {

        boolean running = true;

        while (running) {

            System.out.println("\n--- Buyer Dashboard ---");
            System.out.println("Welcome, " + buyer.getName() + "!");

            System.out.println("1.  Browse All Cars");
            System.out.println("2.  Search / Filter Cars");
            System.out.println("3.  View Car Details");
            System.out.println("4.  View Vehicle History");
            System.out.println("5.  Make an Offer");
            System.out.println("6.  Request Test Drive");
            System.out.println("7.  My Offers");
            System.out.println("8.  Message a Seller / Dealer");
            System.out.println("9.  View Conversation");
            System.out.println("10. Pay for Accepted Offer");
            System.out.println("11. View / Pay Loan Schedule");
            System.out.println("12. View / Pay EMI Schedule");
            System.out.println("13. Rate a Seller / Dealer");
            System.out.println("14. My Profile");
            System.out.println("0.  Logout");

            System.out.print("Enter choice: ");

            String input = sc.nextLine().trim();

            switch (input) {

                case "1" -> browseAllCars();
                case "2" -> searchCars();
                case "3" -> viewCarDetails();
                case "4" -> viewVehicleHistory();
                case "5" -> makeOffer();
                case "6" -> requestTestDrive();
                case "7" -> myOffers();
                case "8" -> sendMessage();
                case "9" -> viewConversation();
                case "10" -> payForAcceptedOffer();
                case "11" -> viewLoanSchedule();
                case "12" -> viewEmiSchedule();
                case "13" -> rateSeller();
                case "14" -> buyer.displayProfile();
                case "0" -> {
                    System.out.println("\nLogged out. Goodbye, " + buyer.getName() + "!");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ---------------- PAY FOR ACCEPTED OFFER ----------------

    private void payForAcceptedOffer() {

        List<Offer> accepted = offerService
                .getOffersByBuyer(buyer.getBuyerId())
                .stream()
                .filter(o -> o.getStatus().equals("ACCEPTED"))
                .toList();

        if (accepted.isEmpty()) {
            System.out.println("No accepted offers.");
            return;
        }

        System.out.println("\nAccepted Offers:");

        for (Offer o : accepted)
            o.displayOffer();

        System.out.print("\nEnter Offer ID: ");

        try {

            int offerId = Integer.parseInt(sc.nextLine());

            Offer chosen = accepted.stream()
                    .filter(o -> o.getOfferId() == offerId)
                    .findFirst()
                    .orElse(null);

            if (chosen == null) {
                System.out.println("Invalid offer.");
                return;
            }

            System.out.println("\nPayment Method");
            System.out.println("1. Cash");
            System.out.println("2. Loan");
            System.out.println("3. EMI");

            System.out.print("Choice: ");
            int method = Integer.parseInt(sc.nextLine());

            if (method == 1) {

                System.out.println("\nCar Details:");
                Car car = carService.getCarById(chosen.getCarId());
                car.displayCarDetails();

                paymentService.processCashPayment(
                        offerId,
                        buyer.getBuyerId(),
                        chosen.getSellerId(),
                        chosen.getOfferPrice()
                );

                System.out.println("Payment successful!");

            }

            else if (method == 2) {

                System.out.println("\nBank Loan Options");
                System.out.println("Bank : HDFC Bank");
                System.out.println("Interest : 8.5% per annum");

                paymentService.showLoanOptions(chosen.getOfferPrice());

                System.out.print("Choose tenure months: ");
                int months = Integer.parseInt(sc.nextLine());

                System.out.println("\nMonthly payment details will be calculated.");

                System.out.print("Confirm Loan (yes/no): ");
                String confirm = sc.nextLine();

                if (confirm.equalsIgnoreCase("yes")) {

                    paymentService.createLoan(
                            offerId,
                            buyer.getBuyerId(),
                            chosen.getSellerId(),
                            chosen.getOfferPrice(),
                            months
                    );

                    System.out.println("Loan applied successfully!");
                }

            }

            else if (method == 3) {

                System.out.println("\nEMI Options");
                System.out.println("Interest : 10%");

                paymentService.showEmiOptions(chosen.getOfferPrice());

                System.out.print("Choose EMI months: ");
                int months = Integer.parseInt(sc.nextLine());

                System.out.print("Confirm EMI plan (yes/no): ");
                String confirm = sc.nextLine();

                if (confirm.equalsIgnoreCase("yes")) {

                    paymentService.createEmi(
                            offerId,
                            buyer.getBuyerId(),
                            chosen.getSellerId(),
                            chosen.getOfferPrice(),
                            months
                    );

                    System.out.println("EMI plan created successfully!");
                }
            }

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- LOAN SCHEDULE ----------------

    private void viewLoanSchedule() {

        try {

            System.out.print("Enter Loan ID: ");
            int loanId = Integer.parseInt(sc.nextLine());

            paymentService.viewLoanSchedule(loanId);

            System.out.print("\nDo you want to pay this month's EMI? (yes/no): ");
            String ans = sc.nextLine();

            if (ans.equalsIgnoreCase("yes")) {

                System.out.print("Enter Month Number to Pay: ");
                int month = Integer.parseInt(sc.nextLine());

                paymentService.payLoanMonth(loanId, month);
            }

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- EMI SCHEDULE ----------------

    private void viewEmiSchedule() {

        try {

            System.out.print("Enter EMI Plan ID: ");
            int emiId = Integer.parseInt(sc.nextLine());

            paymentService.viewEmiSchedule(emiId);

            System.out.print("\nDo you want to pay EMI? (yes/no): ");
            String ans = sc.nextLine();

            if (ans.equalsIgnoreCase("yes")) {

                System.out.print("Enter Month Number to Pay: ");
                int month = Integer.parseInt(sc.nextLine());

                paymentService.payEmiMonth(emiId, month);
            }

        }

        catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- REMAINING METHODS ----------------

    private void browseAllCars() {
        System.out.println("\n-- All Available Cars --");
        carService.listAllCars();
    }

    private void searchCars() {

        System.out.println("\n-- Search Cars --");

        System.out.print("Brand: ");
        String brand = sc.nextLine();

        System.out.print("Fuel: ");
        String fuel = sc.nextLine();

        System.out.print("Condition: ");
        String cond = sc.nextLine();

        System.out.print("Max Price: ");
        double max = Double.parseDouble(sc.nextLine());

        List<Car> results = carService.searchCars(brand, fuel, cond, max);

        for (Car c : results)
            c.displayCarDetails();
    }

    private void viewCarDetails() {
        System.out.println("\n-- AVilable Cars ---");
        carService.listAllCars();

        try {

            System.out.print("Enter Car ID: ");
            int id = Integer.parseInt(sc.nextLine());

            Car car = carService.getCarById(id);

            if (car != null)
                car.displayCarDetails();
            else
                System.out.println("Car not found");

        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    // -------- MODIFIED --------

    private void viewVehicleHistory() {

        System.out.println("\n-- Available Cars --");
        carService.listAllCars();

        try {

            System.out.print("\nEnter Car ID: ");
            int id = Integer.parseInt(sc.nextLine());

            VehicleHistory h = carService.getHistory(id);

            if (h != null)
                h.viewHistory();
            else
                System.out.println("History not found.");

        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    private void makeOffer() {
        System.out.println("\n-- Available Cars --");
        carService.listAllCars();

        try {

            System.out.print("Car ID: ");
            int carId = Integer.parseInt(sc.nextLine());

            Car car = carService.getCarById(carId);

            System.out.print("Offer Price: ");
            double price = Double.parseDouble(sc.nextLine());

            offerService.createOffer(
                    buyer.getBuyerId(),
                    carId,
                    car.getOwnerId(),
                    price
            );

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private void requestTestDrive() {

        try {

            System.out.print("Offer ID: ");
            int id = Integer.parseInt(sc.nextLine());

            offerService.requestTestDrive(id, buyer.getBuyerId());

        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    private void myOffers() {

        List<Offer> offers = offerService.getOffersByBuyer(buyer.getBuyerId());

        for (Offer o : offers)
            o.displayOffer();
    }

    // -------- MODIFIED --------

    private void sendMessage() {

        System.out.println("\n-- Sellers / Dealers --");

        for (User u : ds.getUsers()) {

            if (u.getRole().equals("SELLER") || u.getRole().equals("DEALER")) {

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
                    buyer.getBuyerId(),
                    id,
                    buyer.getName(),
                    text
            );

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // -------- MODIFIED --------

    private void viewConversation() {

        List<User> contacts = messageService.getContacts(buyer.getBuyerId());

        if (contacts.isEmpty()) {
            System.out.println("No conversations yet.");
            return;
        }

        System.out.println("\n-- Your Conversations --");

        for (User u : contacts)
            System.out.println(u.getUserId() + " - " + u.getName());

        try {

            System.out.print("\nEnter ID to open chat: ");
            int id = Integer.parseInt(sc.nextLine());

            messageService.displayConversation(
                    buyer.getBuyerId(),
                    id
            );

        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    // -------- MODIFIED --------

    private void rateSeller() {

        System.out.println("\n-- Sellers / Dealers --");

        for (User u : ds.getUsers()) {

            if (u.getRole().equals("SELLER") || u.getRole().equals("DEALER")) {

                System.out.println(
                        u.getUserId() + " - " +
                        u.getName() + " (" +
                        u.getRole() + ")"
                );
            }
        }

        try {

            System.out.print("\nEnter ID to rate: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Rating (0-5): ");
            double rating = Double.parseDouble(sc.nextLine());

            carService.rateSeller(id, rating);

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}