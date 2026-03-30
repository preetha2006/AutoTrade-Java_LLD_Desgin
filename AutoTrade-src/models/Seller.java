package models;
import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    private double sellerRating;
    private List<Integer> listedCarIds;

    public Seller(int userId, String name, String email, String password, String phone, String location) {
        super(userId, name, email, password, phone, location, "SELLER");
        this.sellerRating = 0.0;
        this.listedCarIds = new ArrayList<>();
    }

    public double getSellerRating() { return sellerRating; }
    public List<Integer> getListedCarIds() { return listedCarIds; }
    public void addCarId(int carId) { listedCarIds.add(carId); }
    public void removeCarId(int carId) { listedCarIds.remove(Integer.valueOf(carId)); }

    public void updateRating(double newRating) {
        if (sellerRating == 0.0) {
            sellerRating = newRating;
        } else {
            sellerRating = (sellerRating + newRating) / 2.0;
        }
    }

    @Override
    public void displayProfile() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         SELLER PROFILE           ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf ("║  ID       : %-20d ║%n", userId);
        System.out.printf ("║  Name     : %-20s ║%n", name);
        System.out.printf ("║  Email    : %-20s ║%n", email);
        System.out.printf ("║  Phone    : %-20s ║%n", phone);
        System.out.printf ("║  Location : %-20s ║%n", location);
        System.out.printf ("║  Rating   : %-20.1f ║%n", sellerRating);
        System.out.printf ("║  Cars     : %-20d ║%n", listedCarIds.size());
        System.out.println("╚══════════════════════════════════╝");
    }
}
