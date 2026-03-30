package models;
public abstract class User {
    protected int userId;
    protected String name;
    protected String email;
    protected String password;
    protected String phone;
    protected String location;
    protected String role;

    public User(int userId, String name, String email, String password, String phone, String location, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getLocation() { return location; }
    public String getRole() { return role; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setLocation(String location) { this.location = location; }
    public void setPassword(String password) { this.password = password; }

    public abstract void displayProfile();
}
