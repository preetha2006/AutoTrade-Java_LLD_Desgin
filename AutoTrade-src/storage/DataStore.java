package storage;
import models.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore instance;

    private List<User> users = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
    private List<VehicleHistory> vehicleHistories = new ArrayList<>();
    private List<Offer> offers = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    private int userIdCounter = 1;
    private int carIdCounter = 1;
    private int historyIdCounter = 1;
    private int offerIdCounter = 1;
    private int messageIdCounter = 1;
    private int paymentIdCounter = 1;

    private DataStore() {
        seedData();
    }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    // ── ID generators ──────────────────────────────────────
    public int nextUserId()    { return userIdCounter++; }
    public int nextCarId()     { return carIdCounter++; }
    public int nextHistoryId() { return historyIdCounter++; }
    public int nextOfferId()   { return offerIdCounter++; }
    public int nextMessageId() { return messageIdCounter++; }
    public int nextPaymentId() { return paymentIdCounter++; }

    // ── Getters ────────────────────────────────────────────
    public List<User> getUsers()                   { return users; }
    public List<Car> getCars()                     { return cars; }
    public List<VehicleHistory> getHistories()     { return vehicleHistories; }
    public List<Offer> getOffers()                 { return offers; }
    public List<Message> getMessages()             { return messages; }
    public List<Payment> getPayments()             { return payments; }

    // ── Adders ─────────────────────────────────────────────
    public void addUser(User u)              { users.add(u); }
    public void addCar(Car c)               { cars.add(c); }
    public void addHistory(VehicleHistory h) { vehicleHistories.add(h); }
    public void addOffer(Offer o)           { offers.add(o); }
    public void addMessage(Message m)       { messages.add(m); }
    public void addPayment(Payment p)       { payments.add(p); }

    // ── Finders ────────────────────────────────────────────
    public User findUserByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public User findUserById(int id) {
        return users.stream().filter(u -> u.getUserId() == id).findFirst().orElse(null);
    }

    public Car findCarById(int id) {
        return cars.stream().filter(c -> c.getCarId() == id).findFirst().orElse(null);
    }

    public VehicleHistory findHistoryByCarId(int carId) {
        return vehicleHistories.stream().filter(h -> h.getCarId() == carId).findFirst().orElse(null);
    }

    public List<Offer> findOffersByBuyerId(int buyerId) {
        List<Offer> result = new ArrayList<>();
        for (Offer o : offers) if (o.getBuyerId() == buyerId) result.add(o);
        return result;
    }

    public List<Offer> findOffersBySellerId(int sellerId) {
        List<Offer> result = new ArrayList<>();
        for (Offer o : offers) if (o.getSellerId() == sellerId) result.add(o);
        return result;
    }

    public List<Message> findMessagesBetween(int userId1, int userId2) {
        List<Message> result = new ArrayList<>();
        for (Message m : messages) {
            if ((m.getSenderId() == userId1 && m.getReceiverId() == userId2) ||
                (m.getSenderId() == userId2 && m.getReceiverId() == userId1)) {
                result.add(m);
            }
        }
        return result;
    }

    public void removeCar(int carId) {
        cars.removeIf(c -> c.getCarId() == carId);
    }

    public void removeUser(int userId) {
        users.removeIf(u -> u.getUserId() == userId);
    }

    // ── Seed data ──────────────────────────────────────────
    private void seedData() {
        // Admin
        Admin admin = new Admin(nextUserId(), "Admin", "admin@autotrade.com", "admin123", "9000000000", "Chennai");
        users.add(admin);

        // Seller
        Seller seller = new Seller(nextUserId(), "Ravi Kumar", "ravi@mail.com", "ravi123", "9111111111", "Chennai");
        users.add(seller);

        // Dealer
        Dealer dealer = new Dealer(nextUserId(), "Priya Sharma", "priya@mail.com", "priya123", "9222222222", "Bangalore", "Priya Motors");
        users.add(dealer);

        // Buyer
        Buyer buyer = new Buyer(nextUserId(), "Arun Raj", "arun@mail.com", "arun123", "9333333333", "Coimbatore");
        users.add(buyer);

        // Cars for seller
        Car c1 = new Car(nextCarId(), "Toyota", "Innova", 2019, 1200000, 45000, "Petrol", "Used", seller.getUserId(), "SELLER");
        Car c2 = new Car(nextCarId(), "Maruti", "Swift", 2021, 650000, 20000, "Petrol", "Used", seller.getUserId(), "SELLER");
        cars.add(c1); cars.add(c2);
        seller.addCarId(c1.getCarId()); seller.addCarId(c2.getCarId());

        // Cars for dealer
        Car c3 = new Car(nextCarId(), "Honda", "City", 2023, 1500000, 0, "Petrol", "New", dealer.getUserId(), "DEALER");
        Car c4 = new Car(nextCarId(), "Hyundai", "Creta", 2022, 1800000, 5000, "Diesel", "Used", dealer.getUserId(), "DEALER");
        cars.add(c3); cars.add(c4);
        dealer.addCarId(c3.getCarId()); dealer.addCarId(c4.getCarId());

        // Vehicle histories
        vehicleHistories.add(new VehicleHistory(nextHistoryId(), c1.getCarId()));
        vehicleHistories.add(new VehicleHistory(nextHistoryId(), c2.getCarId()));
        vehicleHistories.add(new VehicleHistory(nextHistoryId(), c3.getCarId()));
        vehicleHistories.add(new VehicleHistory(nextHistoryId(), c4.getCarId()));
    }
}
