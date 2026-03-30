package services;

import models.*;
import storage.DataStore;

import java.util.ArrayList;
import java.util.List;

public class CarService {

    private DataStore ds = DataStore.getInstance();

    public Car addCar(String brand, String model, int year, double price,
                      double mileage, String fuelType, String condition, int ownerId, String ownerRole) {

        Car car = new Car(
                ds.nextCarId(),
                brand,
                model,
                year,
                price,
                mileage,
                fuelType,
                condition,
                ownerId,
                ownerRole
        );

        ds.addCar(car);

        VehicleHistory history =
                new VehicleHistory(
                        ds.nextHistoryId(),
                        car.getCarId()
                );

        ds.addHistory(history);

        return car;
    }


    public boolean updateCar(int carId, double price, double mileage, String condition) {

        Car car = ds.findCarById(carId);

        if (car == null)
            return false;

        car.setPrice(price);
        car.setMileage(mileage);
        car.setCondition(condition);

        return true;
    }


    public boolean removeCar(int carId, int ownerId) {

        Car car = ds.findCarById(carId);

        if (car == null || car.getOwnerId() != ownerId)
            return false;

        ds.removeCar(carId);

        return true;
    }


    // ----------- MODIFIED METHOD (ADMIN VIEW) -----------

    public void listAllCars() {

        List<Car> available = new ArrayList<>();

        for (Car c : ds.getCars()) {
            if (c.isAvailable())
                available.add(c);
        }

        if (available.isEmpty()) {

            System.out.println("  No cars available.");

            return;
        }

        System.out.println("\n  ┌────┬──────────┬────────────┬──────┬────────────┬─────────┬──────────┬─────────┬─────────────────┐");

        System.out.println("  │ ID │ Brand    │ Model      │ Year │ Price(₹)   │ Fuel    │ Condition│ Mileage │ Last Service    │");

        System.out.println("  ├────┼──────────┼────────────┼──────┼────────────┼─────────┼──────────┼─────────┼─────────────────┤");

        for (Car c : available) {

            VehicleHistory history =
                    ds.findHistoryByCarId(
                            c.getCarId()
                    );

            String serviceDate = "N/A";

            if (history != null && history.getLastServiceDate() != null) {
                serviceDate = history.getLastServiceDate().toString();
            }

            System.out.printf(
                    "  │ %-2d │ %-8s │ %-10s │ %-4d │ %-10.0f │ %-7s │ %-8s │ %-7.0f │ %-15s │%n",
                    c.getCarId(),
                    c.getBrand(),
                    c.getModel(),
                    c.getYear(),
                    c.getPrice(),
                    c.getFuelType(),
                    c.getCondition(),
                    c.getMileage(),
                    serviceDate
            );
        }

        System.out.println("  └────┴──────────┴────────────┴──────┴────────────┴─────────┴──────────┴─────────┴─────────────────┘");
    }


    public List<Car> searchCars(String brand, String fuelType, String condition, double maxPrice) {

        List<Car> result = new ArrayList<>();

        for (Car c : ds.getCars()) {

            if (!c.isAvailable())
                continue;

            if (brand != null && !brand.isEmpty()
                    && !c.getBrand().equalsIgnoreCase(brand))
                continue;

            if (fuelType != null && !fuelType.isEmpty()
                    && !c.getFuelType().equalsIgnoreCase(fuelType))
                continue;

            if (condition != null && !condition.isEmpty()
                    && !c.getCondition().equalsIgnoreCase(condition))
                continue;

            if (maxPrice > 0 && c.getPrice() > maxPrice)
                continue;

            result.add(c);
        }

        return result;
    }


    public List<Car> getCarsByOwner(int ownerId) {

        List<Car> result = new ArrayList<>();

        for (Car c : ds.getCars()) {

            if (c.getOwnerId() == ownerId)
                result.add(c);
        }

        return result;
    }


    public Car getCarById(int carId) {

        return ds.findCarById(carId);
    }


    public VehicleHistory getHistory(int carId) {

        return ds.findHistoryByCarId(carId);
    }


    public void rateSeller(int sellerId, double rating) {

        User u = ds.findUserById(sellerId);

        if (u instanceof Seller)
            ((Seller) u).updateRating(rating);

        if (u instanceof Dealer)
            ((Dealer) u).updateRating(rating);
    }
}