package models;
import java.util.ArrayList;
import java.util.List;

public class Dealer extends User {
    private String dealershipName;
    private double dealerRating;
    private List<Integer> inventoryCarIds;

    public Dealer(int userId, String name, String email, String password, String phone, String location, String dealershipName) {
        super(userId, name, email, password, phone, location, "DEALER");
        this.dealershipName = dealershipName;
        this.dealerRating = 0.0;
        this.inventoryCarIds = new ArrayList<>();
    }

    public String getDealershipName() { return dealershipName; }
    public double getDealerRating() { return dealerRating; }
    public List<Integer> getInventoryCarIds() { return inventoryCarIds; }
    public void addCarId(int carId) { inventoryCarIds.add(carId); }
    public void removeCarId(int carId) { inventoryCarIds.remove(Integer.valueOf(carId)); }

    public void updateRating(double newRating) {
        if (dealerRating == 0.0) dealerRating = newRating;
        else dealerRating = (dealerRating + newRating) / 2.0;
    }

    @Override
    public void displayProfile() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         DEALER PROFILE           ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf ("║  ID         : %-18d ║%n", userId);
        System.out.printf ("║  Name       : %-18s ║%n", name);
        System.out.printf ("║  Dealership : %-18s ║%n", dealershipName);
        System.out.printf ("║  Email      : %-18s ║%n", email);
        System.out.printf ("║  Phone      : %-18s ║%n", phone);
        System.out.printf ("║  Location   : %-18s ║%n", location);
        System.out.printf ("║  Rating     : %-18.1f ║%n", dealerRating);
        System.out.printf ("║  Inventory  : %-18d ║%n", inventoryCarIds.size());
        System.out.println("╚══════════════════════════════════╝");
    }
}
