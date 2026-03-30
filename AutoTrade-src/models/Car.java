package models;

public class Car {
    private int carId;
    private String brand;
    private String model;
    private int year;
    private double price;
    private double mileage;
    private String fuelType;
    private String condition;
    private int ownerId;
    private String ownerRole;
    private boolean available;

    public Car(int carId, String brand, String model, int year, double price,
               double mileage, String fuelType, String condition, int ownerId, String ownerRole) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.condition = condition;
        this.ownerId = ownerId;
        this.ownerRole = ownerRole;
        this.available = true;
    }

    public int getCarId() { return carId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public double getMileage() { return mileage; }
    public String getFuelType() { return fuelType; }
    public String getCondition() { return condition; }
    public int getOwnerId() { return ownerId; }
    public String getOwnerRole() { return ownerRole; }
    public boolean isAvailable() { return available; }

    public void setPrice(double price) { this.price = price; }
    public void setMileage(double mileage) { this.mileage = mileage; }
    public void setCondition(String condition) { this.condition = condition; }
    public void setAvailable(boolean available) { this.available = available; }

    public void displayCarDetails() {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.printf ("│  Car ID   : %-31d│%n", carId);
        System.out.printf ("│  Brand    : %-31s│%n", brand);
        System.out.printf ("│  Model    : %-31s│%n", model);
        System.out.printf ("│  Year     : %-31d│%n", year);
        System.out.printf ("│  Price    : ₹%-30.2f│%n", price);
        System.out.printf ("│  Mileage  : %-27.1f km │%n", mileage);
        System.out.printf ("│  Fuel     : %-31s│%n", fuelType);
        System.out.printf ("│  Condition: %-31s│%n", condition);
        System.out.printf ("│  Status   : %-31s│%n", available ? "Available" : "Sold");
        System.out.println("└─────────────────────────────────────────────┘");
    }
}
