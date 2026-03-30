import menus.*;
import models.*;
import services.*;
import storage.DataStore;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static UserService userService = new UserService();
    static DataStore ds = DataStore.getInstance();

    public static void main(String[] args) {

        boolean appRunning = true;

        while (appRunning) {
            printMainMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1" -> adminLogin();
                case "2" -> sellerLogin();
                case "3" -> sellerSignup();
                case "4" -> buyerLogin();
                case "5" -> buyerSignup();
                case "6" -> dealerLogin();
                case "7" -> dealerSignup();

                case "0" -> {
                    System.out.println("\nThank you for using Auto Trade.");
                    appRunning = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }

        sc.close();
    }

    static void printMainMenu() {
        System.out.println("\n===================================");
        System.out.println(" AUTO TRADE - Car Reselling Platform");
        System.out.println("===================================");
        System.out.println("1. Admin Login");
        System.out.println("2. Seller Login");
        System.out.println("3. Seller Sign Up");
        System.out.println("4. Buyer Login");
        System.out.println("5. Buyer Sign Up");
        System.out.println("6. Dealer Login");
        System.out.println("7. Dealer Sign Up");
        System.out.println("0. Exit");
        System.out.print("\nEnter choice: ");
    }

    static void adminLogin() {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Email: ");    String email    = sc.nextLine();
        System.out.print("Password: "); String password = sc.nextLine();
        User user = userService.login(email, password);
        if (user != null && user.getRole().equals("ADMIN"))
            new AdminMenu((Admin) user, sc).show();
        else
            System.out.println("Invalid admin credentials.");
    }

    static void sellerLogin() {
        System.out.println("\n--- Seller Login ---");
        System.out.print("Email: ");    String email    = sc.nextLine();
        System.out.print("Password: "); String password = sc.nextLine();
        User user = userService.login(email, password);
        if (user != null && user.getRole().equals("SELLER"))
            new SellerMenu((Seller) user, sc).show();
        else
            System.out.println("Invalid seller credentials.");
    }

    static void buyerLogin() {
        System.out.println("\n--- Buyer Login ---");
        System.out.print("Email: ");    String email    = sc.nextLine();
        System.out.print("Password: "); String password = sc.nextLine();
        User user = userService.login(email, password);
        if (user != null && user.getRole().equals("BUYER"))
            new BuyerMenu((Buyer) user, sc).show();
        else
            System.out.println("Invalid buyer credentials.");
    }

    static void dealerLogin() {
        System.out.println("\n--- Dealer Login ---");
        System.out.print("Email: ");    String email    = sc.nextLine();
        System.out.print("Password: "); String password = sc.nextLine();
        User user = userService.login(email, password);
        if (user != null && user.getRole().equals("DEALER"))
            new DealerMenu((Dealer) user, sc).show();
        else
            System.out.println("Invalid dealer credentials.");
    }

    static void buyerSignup() {
        System.out.println("\n--- Buyer Registration ---");
        System.out.print("Name: ");      String name     = sc.nextLine();
        System.out.print("Email: ");     String email    = sc.nextLine();
        if (userService.emailExists(email)) { System.out.println("Email already exists."); return; }
        System.out.print("Password: ");  String password = sc.nextLine();
        System.out.print("Phone: ");     String phone    = sc.nextLine();
        System.out.print("Location: ");  String location = sc.nextLine();
        Buyer b = userService.registerBuyer(name, email, password, phone, location);
        System.out.println("Buyer registered successfully. ID: " + b.getUserId());
    }

    static void sellerSignup() {
        System.out.println("\n--- Seller Registration ---");
        System.out.print("Name: ");      String name     = sc.nextLine();
        System.out.print("Email: ");     String email    = sc.nextLine();
        if (userService.emailExists(email)) { System.out.println("Email already exists."); return; }
        System.out.print("Password: ");  String password = sc.nextLine();
        System.out.print("Phone: ");     String phone    = sc.nextLine();
        System.out.print("Location: ");  String location = sc.nextLine();
        Seller s = userService.registerSeller(name, email, password, phone, location);
        System.out.println("Seller registered successfully. ID: " + s.getUserId());
    }

    static void dealerSignup() {
        System.out.println("\n--- Dealer Registration ---");
        System.out.print("Name: ");           String name        = sc.nextLine();
        System.out.print("Email: ");          String email       = sc.nextLine();
        if (userService.emailExists(email)) { System.out.println("Email already exists."); return; }
        System.out.print("Password: ");       String password    = sc.nextLine();
        System.out.print("Phone: ");          String phone       = sc.nextLine();
        System.out.print("Location: ");       String location    = sc.nextLine();
        System.out.print("Dealership Name: "); String dealership = sc.nextLine();
        Dealer d = userService.registerDealer(name, email, password, phone, location, dealership);
        System.out.println("Dealer registered successfully. ID: " + d.getUserId());
    }
}
