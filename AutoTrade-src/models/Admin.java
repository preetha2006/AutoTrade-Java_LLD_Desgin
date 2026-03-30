package models;

public class Admin extends User {
    private int adminId;

    public Admin(int userId, String name, String email, String password, String phone, String location) {
        super(userId, name, email, password, phone, location, "ADMIN");
        this.adminId = userId;
    }

    public int getAdminId() { return adminId; }

    @Override
    public void displayProfile() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         ADMIN PROFILE            ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf ("║  Admin ID : %-20d ║%n", adminId);
        System.out.printf ("║  Name     : %-20s ║%n", name);
        System.out.printf ("║  Email    : %-20s ║%n", email);
        System.out.printf ("║  Phone    : %-20s ║%n", phone);
        System.out.println("╚══════════════════════════════════╝");
    }
}
