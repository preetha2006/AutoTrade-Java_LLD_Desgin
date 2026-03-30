package services;
import models.*;
import storage.DataStore;

public class UserService {
    private DataStore ds = DataStore.getInstance();

    public User login(String email, String password) {
        User u = ds.findUserByEmail(email);
        if (u != null && u.getPassword().equals(password)) return u;
        return null;
    }

    public boolean emailExists(String email) {
        return ds.findUserByEmail(email) != null;
    }

    public Buyer registerBuyer(String name, String email, String password, String phone, String location) {
        Buyer b = new Buyer(ds.nextUserId(), name, email, password, phone, location);
        ds.addUser(b);
        return b;
    }

    public Seller registerSeller(String name, String email, String password, String phone, String location) {
        Seller s = new Seller(ds.nextUserId(), name, email, password, phone, location);
        ds.addUser(s);
        return s;
    }

    public Dealer registerDealer(String name, String email, String password, String phone, String location, String dealershipName) {
        Dealer d = new Dealer(ds.nextUserId(), name, email, password, phone, location, dealershipName);
        ds.addUser(d);
        return d;
    }

    public boolean updateProfile(int userId, String phone, String location) {
        User u = ds.findUserById(userId);
        if (u == null) return false;
        u.setPhone(phone);
        u.setLocation(location);
        return true;
    }

    public boolean changePassword(int userId, String oldPass, String newPass) {
        User u = ds.findUserById(userId);
        if (u == null || !u.getPassword().equals(oldPass)) return false;
        u.setPassword(newPass);
        return true;
    }

    public void listAllUsers() {
        System.out.println("\n  ┌──────┬────────────────────┬──────────┬──────────────────────┐");
        System.out.println("  │  ID  │ Name               │ Role     │ Email                │");
        System.out.println("  ├──────┼────────────────────┼──────────┼──────────────────────┤");
        for (User u : ds.getUsers()) {
            System.out.printf("  │ %-4d │ %-18s │ %-8s │ %-20s │%n",
                u.getUserId(), u.getName(), u.getRole(), u.getEmail());
        }
        System.out.println("  └──────┴────────────────────┴──────────┴──────────────────────┘");
    }

    public boolean removeUser(int userId) {
        User u = ds.findUserById(userId);
        if (u == null || u.getRole().equals("ADMIN")) return false;
        ds.removeUser(userId);
        return true;
    }

    public boolean blockUser(int userId) {
        User u = ds.findUserById(userId);
        if (u == null) return false;
        // Mark by prepending [BLOCKED] to name as simple flag
        if (!u.getName().startsWith("[BLOCKED]"))
            u.setName("[BLOCKED] " + u.getName());
        return true;
    }
}
