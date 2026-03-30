package models;
import java.time.LocalDate;

public class VehicleHistory {
    private int historyId;
    private int carId;
    private String accidentRecords;
    private String serviceHistory;
    private int ownershipCount;
    private LocalDate lastServiceDate;

    public VehicleHistory(int historyId, int carId) {
        this.historyId = historyId;
        this.carId = carId;
        this.accidentRecords = "No accidents recorded";
        this.serviceHistory = "No service records";
        this.ownershipCount = 1;
        this.lastServiceDate = LocalDate.now();
    }

    public int getHistoryId() { return historyId; }
    public int getCarId() { return carId; }
    public String getAccidentRecords() { return accidentRecords; }
    public String getServiceHistory() { return serviceHistory; }
    public int getOwnershipCount() { return ownershipCount; }
    public LocalDate getLastServiceDate() { return lastServiceDate; }

    public void updateAccidentRecord(String record) { this.accidentRecords = record; }
    public void updateServiceHistory(String history) { this.serviceHistory = history; }
    public void updateOwnership() { this.ownershipCount++; }
    public void setLastServiceDate(LocalDate date) { this.lastServiceDate = date; }

    public void viewHistory() {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│            VEHICLE HISTORY REPORT           │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf ("│  Car ID         : %-26d│%n", carId);
        System.out.printf ("│  Owners         : %-26d│%n", ownershipCount);
        System.out.printf ("│  Last Service   : %-26s│%n", lastServiceDate);
        System.out.printf ("│  Accident Rec   : %-26s│%n", accidentRecords);
        System.out.printf ("│  Service Hist   : %-26s│%n", serviceHistory);
        System.out.println("└─────────────────────────────────────────────┘");
    }
}
