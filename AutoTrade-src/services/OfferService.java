package services;
import models.*;
import storage.DataStore;
import java.util.List;

public class OfferService {
    private DataStore ds = DataStore.getInstance();

    public Offer createOffer(int buyerId, int carId, int sellerId, double offerPrice) {
        Car car = ds.findCarById(carId);
        if (car == null || !car.isAvailable()) return null;
        Offer offer = new Offer(ds.nextOfferId(), buyerId, carId, sellerId, offerPrice);
        ds.addOffer(offer);
        return offer;
    }

    public boolean requestTestDrive(int offerId, int buyerId) {
        for (Offer o : ds.getOffers()) {
            if (o.getOfferId() == offerId && o.getBuyerId() == buyerId) {
                o.setTestDriveRequested(true);
                return true;
            }
        }
        return false;
    }

    public boolean acceptOffer(int offerId, int sellerId) {
        for (Offer o : ds.getOffers()) {
            if (o.getOfferId() == offerId && o.getSellerId() == sellerId) {
                o.setStatus("ACCEPTED");
                Car car = ds.findCarById(o.getCarId());
                if (car != null) car.setAvailable(false);
                // Reject all other offers on this car
                for (Offer other : ds.getOffers()) {
                    if (other.getCarId() == o.getCarId() && other.getOfferId() != offerId) {
                        other.setStatus("REJECTED");
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean rejectOffer(int offerId, int sellerId) {
        for (Offer o : ds.getOffers()) {
            if (o.getOfferId() == offerId && o.getSellerId() == sellerId) {
                o.setStatus("REJECTED");
                return true;
            }
        }
        return false;
    }

    public List<Offer> getOffersByBuyer(int buyerId) {
        return ds.findOffersByBuyerId(buyerId);
    }

    public List<Offer> getOffersBySeller(int sellerId) {
        return ds.findOffersBySellerId(sellerId);
    }

    public Offer findById(int offerId) {
        for (Offer o : ds.getOffers()) if (o.getOfferId() == offerId) return o;
        return null;
    }
}
