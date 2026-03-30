package models;
import java.time.LocalDateTime;

public class Offer {
    private int offerId;
    private int buyerId;
    private int carId;
    private int sellerId;
    private double offerPrice;
    private String status; // PENDING, ACCEPTED, REJECTED
    private LocalDateTime createdAt;
    private boolean testDriveRequested;

    public Offer(int offerId, int buyerId, int carId, int sellerId, double offerPrice) {
        this.offerId = offerId;
        this.buyerId = buyerId;
        this.carId = carId;
        this.sellerId = sellerId;
        this.offerPrice = offerPrice;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
        this.testDriveRequested = false;
    }

    public int getOfferId() { return offerId; }
    public int getBuyerId() { return buyerId; }
    public int getCarId() { return carId; }
    public int getSellerId() { return sellerId; }
    public double getOfferPrice() { return offerPrice; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isTestDriveRequested() { return testDriveRequested; }

    public void setStatus(String status) { this.status = status; }
    public void setTestDriveRequested(boolean v) { this.testDriveRequested = v; }

    public void displayOffer() {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.printf ("│  Offer ID     : %-28d│%n", offerId);
        System.out.printf ("│  Car ID       : %-28d│%n", carId);
        System.out.printf ("│  Buyer ID     : %-28d│%n", buyerId);
        System.out.printf ("│  Offer Price  : ₹%-27.2f│%n", offerPrice);
        System.out.printf ("│  Status       : %-28s│%n", status);
        System.out.printf ("│  Test Drive   : %-28s│%n", testDriveRequested ? "Requested" : "Not Requested");
        System.out.printf ("│  Date         : %-28s│%n", createdAt.toLocalDate());
        System.out.println("└─────────────────────────────────────────────┘");
    }
}
