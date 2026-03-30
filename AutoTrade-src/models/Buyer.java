package models;

public class Buyer extends User {
    private int buyerId;

    public Buyer(int userId, String name, String email, String password, String phone, String location) {
        super(userId, name, email, password, phone, location, "BUYER");
        this.buyerId = userId;
    }

    public int getBuyerId() { return buyerId; }

    @Override
    public void displayProfile() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         BUYER PROFILE            ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf ("║  ID       : %-20d ║%n", userId);
        System.out.printf ("║  Name     : %-20s ║%n", name);
        System.out.printf ("║  Email    : %-20s ║%n", email);
        System.out.printf ("║  Phone    : %-20s ║%n", phone);
        System.out.printf ("║  Location : %-20s ║%n", location);
        System.out.println("╚══════════════════════════════════╝");
    }
}
