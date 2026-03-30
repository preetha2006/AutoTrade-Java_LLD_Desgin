package menus;
import models.*;
import services.*;
import storage.DataStore;
import java.util.Scanner;

public class AdminMenu {
    private Admin admin;
    private UserService userService = new UserService();
    private CarService carService = new CarService();
    private PaymentService paymentService = new PaymentService();
    private DataStore ds = DataStore.getInstance();
    private Scanner sc;

    public AdminMenu(Admin admin, Scanner sc) {
        this.admin = admin;
        this.sc = sc;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║          ADMIN DASHBOARD             ║");
            System.out.printf ("║  Logged in as: %-21s ║%n", admin.getName());
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. View All Users                   ║");
            System.out.println("║  2. Remove a User                    ║");
            System.out.println("║  3. Block a User                     ║");
            System.out.println("║  4. View All Car Listings            ║");
            System.out.println("║  5. Remove a Car Listing             ║");
            System.out.println("║  6. View All Offers                  ║");
            System.out.println("║  7. Monitor All Transactions         ║");
            System.out.println("║  8. Platform Summary                 ║");
            System.out.println("║  9. My Profile                       ║");
            System.out.println("║  0. Logout                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Enter choice: ");

            String input = sc.nextLine().trim();
            switch (input) {
                case "1" -> viewAllUsers();
                case "2" -> removeUser();
                case "3" -> blockUser();
                case "4" -> viewAllCars();
                case "5" -> removeCarListing();
                case "6" -> viewAllOffers();
                case "7" -> paymentService.listAllPayments();
                case "8" -> platformSummary();
                case "9" -> admin.displayProfile();
                case "0" -> { System.out.println("\n  Logged out. Goodbye, " + admin.getName() + "!"); running = false; }
                default  -> System.out.println("  ✗ Invalid option.");
            }
        }
    }

    private void viewAllUsers() {
        System.out.println("\n  ══ All Registered Users ══");
        userService.listAllUsers();
    }

    private void removeUser() {
        System.out.print("\n  Enter User ID to remove: ");
        try {
            int userId = Integer.parseInt(sc.nextLine().trim());
            boolean ok = userService.removeUser(userId);
            System.out.println(ok ? "  ✓ User removed." : "  ✗ User not found or cannot remove admin.");
        } catch (NumberFormatException e) {
            System.out.println("  ✗ Invalid ID.");
        }
    }

    private void blockUser() {
        System.out.print("\n  Enter User ID to block: ");
        try {
            int userId = Integer.parseInt(sc.nextLine().trim());
            boolean ok = userService.blockUser(userId);
            System.out.println(ok ? "  ✓ User blocked." : "  ✗ User not found.");
        } catch (NumberFormatException e) {
            System.out.println("  ✗ Invalid ID.");
        }
    }

    private void viewAllCars() {
        System.out.println("\n  ══ All Car Listings ══");
        carService.listAllCars();
    }

    private void removeCarListing() {
        System.out.print("\n  Enter Car ID to remove: ");
        try {
            int carId = Integer.parseInt(sc.nextLine().trim());
            Car car = carService.getCarById(carId);
            if (car == null) { System.out.println("  ✗ Car not found."); return; }
            // Admin can remove any car — pass ownerId directly
            boolean ok = carService.removeCar(carId, car.getOwnerId());
            System.out.println(ok ? "  ✓ Car listing removed." : "  ✗ Failed to remove.");
        } catch (NumberFormatException e) {
            System.out.println("  ✗ Invalid ID.");
        }
    }

    private void viewAllOffers() {
        System.out.println("\n  ══ All Offers ══");
        if (ds.getOffers().isEmpty()) { System.out.println("  No offers yet."); return; }
        for (Offer o : ds.getOffers()) o.displayOffer();
    }

    private void platformSummary() {
        long buyers  = ds.getUsers().stream().filter(u -> u.getRole().equals("BUYER")).count();
        long sellers = ds.getUsers().stream().filter(u -> u.getRole().equals("SELLER")).count();
        long dealers = ds.getUsers().stream().filter(u -> u.getRole().equals("DEALER")).count();
        long available = ds.getCars().stream().filter(Car::isAvailable).count();
        long sold      = ds.getCars().stream().filter(c -> !c.isAvailable()).count();
        long pending   = ds.getOffers().stream().filter(o -> o.getStatus().equals("PENDING")).count();
        long accepted  = ds.getOffers().stream().filter(o -> o.getStatus().equals("ACCEPTED")).count();
        double revenue = ds.getPayments().stream().mapToDouble(Payment::getAmount).sum();

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║           PLATFORM SUMMARY               ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Buyers           : %-20d║%n", buyers);
        System.out.printf ("║  Sellers          : %-20d║%n", sellers);
        System.out.printf ("║  Dealers          : %-20d║%n", dealers);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Cars Available   : %-20d║%n", available);
        System.out.printf ("║  Cars Sold        : %-20d║%n", sold);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Pending Offers   : %-20d║%n", pending);
        System.out.printf ("║  Accepted Offers  : %-20d║%n", accepted);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Total Revenue    : ₹%-19.2f║%n", revenue);
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
