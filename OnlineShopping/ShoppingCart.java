import java.util.*;
class ShoppingCart {
    private String cartId;
    private Map<ShoppingItem, Integer> cartItems;
    private double totalPrice;
    private Customer customer;

    public ShoppingCart(String cartId, Customer customer) {
        this.cartId = cartId;
        this.customer = customer;
        this.cartItems = new HashMap<>();
        this.totalPrice = 0;
    }

    public void addItem(ShoppingItem item, int quantity) {
        addItem(item, quantity, item.getPrice());
    }

    public void addItem(ShoppingItem item, int quantity, double price) {
        cartItems.merge(item, quantity, Integer::sum);
        totalPrice += quantity * price;
    }

    public void removeItem(ShoppingItem item, int quantity) {
        if (cartItems.containsKey(item)) {
            int currentQty = cartItems.get(item);
            if (quantity >= currentQty) {
                totalPrice -= currentQty * item.getPrice();
                cartItems.remove(item);
            } else {
                cartItems.put(item, currentQty - quantity);
                totalPrice -= quantity * item.getPrice();
            }
        }
    }

    public void displayCart() {
        System.out.println("\nShopping Cart Contents:");
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            cartItems.forEach((item, qty) -> {
                System.out.println(item.getItemName() + " - Qty: " + qty + " - Price: $" + (item.getPrice() * qty));
            });
            System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
        }
    }

    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println("Cannot checkout - cart is empty.");
            return;
        }

        System.out.println("\nProceeding to checkout...");
        System.out.println("Total amount to pay: $" + String.format("%.2f", totalPrice));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select payment method (1. Credit Card, 2. PayPal, 3. Bank Transfer): ");
        int paymentMethodChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        String paymentMethod;
        switch (paymentMethodChoice) {
            case 1: paymentMethod = "Credit Card"; break;
            case 2: paymentMethod = "PayPal"; break;
            case 3: paymentMethod = "Bank Transfer"; break;
            default: paymentMethod = "Unknown"; break;
        }

        Payment payment = new Payment(
            UUID.randomUUID().toString(),
            paymentMethod,
            totalPrice,
            new Date()
        );

        if (payment.processPayment()) {
            customer.addPayment(payment);
            System.out.println("Payment successful!");
            
            // Generate invoices for all items
            System.out.println("\nInvoice Details:");
            cartItems.forEach((item, qty) -> {
                System.out.println("\n--- " + item.getItemName() + " ---");
                item.generateInvoice(customer);
                System.out.println("Quantity: " + qty);
                System.out.println("Subtotal: $" + (item.getPrice() * qty));
            });
            
            System.out.println("\nTotal Amount Paid: $" + String.format("%.2f", totalPrice));
            System.out.println("Payment Method: " + paymentMethod);
            System.out.println("Thank you for your purchase, " + customer.getCustomerName() + "!");
            
            // Clear the cart after successful payment
            cartItems.clear();
            totalPrice = 0;
        } else {
            System.out.println("Payment failed. Please try again.");
        }
    }

    // Getters
    public String getCartId() { return cartId; }
    public Map<ShoppingItem, Integer> getCartItems() { return cartItems; }
    public double getTotalPrice() { return totalPrice; }
    public Customer getCustomer() { return customer; }
}
