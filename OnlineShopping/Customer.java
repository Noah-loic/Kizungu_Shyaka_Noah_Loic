import java.util.*;
class Customer {
    private String customerId;
    private String customerName;
    private String email;
    private String address;
    private String phone;
    private ShoppingCart shoppingCart;
    private List<Payment> paymentHistory;

    public Customer(String customerId, String customerName, String email, String address, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.shoppingCart = new ShoppingCart(UUID.randomUUID().toString(), this);
        this.paymentHistory = new ArrayList<>();
    }

    // Getters
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public List<Payment> getPaymentHistory() { return paymentHistory; }

    // Validation methods
    public boolean validateDetails() {
        return !customerName.isEmpty() && 
               email.contains("@") && 
               !address.isEmpty() && 
               phone.length() >= 10;
    }

    public void addPayment(Payment payment) {
        paymentHistory.add(payment);
    }

    public void displayCustomerInfo() {
        System.out.println("\nCustomer Information:");
        System.out.println("Name: " + customerName);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
    }
}