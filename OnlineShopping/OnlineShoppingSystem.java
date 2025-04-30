import java.text.SimpleDateFormat;
import java.util.*;

// Abstract Class: ShoppingItem
abstract class ShoppingItem {
    protected String itemId;
    protected String itemName;
    protected String itemDescription;
    protected double price;
    protected int stockAvailable;

    public ShoppingItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.stockAvailable = stockAvailable;
    }

    // Abstract methods
    public abstract void updateStock(int quantity);
    public abstract void addToCart(Customer customer);
    public abstract void generateInvoice(Customer customer);
    public abstract boolean validateItem();

    // Getters
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getItemDescription() { return itemDescription; }
    public double getPrice() { return price; }
    public int getStockAvailable() { return stockAvailable; }
}

// 1. ElectronicsItem
class ElectronicsItem extends ShoppingItem {
    private int warrantyMonths;
    private boolean isRegistered;

    public ElectronicsItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable, int warrantyMonths) {
        super(itemId, itemName, itemDescription, price, stockAvailable);
        this.warrantyMonths = warrantyMonths;
        this.isRegistered = false;
    }

    @Override
    public void updateStock(int quantity) {
        this.stockAvailable += quantity;
    }

    @Override
    public void addToCart(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter quantity for " + itemName + ": ");
        int quantity = scanner.nextInt();
        
        if (quantity <= stockAvailable) {
            customer.getShoppingCart().addItem(this, quantity);
            System.out.println(quantity + " " + itemName + "(s) added to cart.");
        } else {
            System.out.println("Not enough stock available. Only " + stockAvailable + " left.");
        }
    }

    @Override
    public void generateInvoice(Customer customer) {
        System.out.println("Electronic Item: " + itemName);
        System.out.println("Price: $" + price);
        System.out.println("Warranty: " + warrantyMonths + " months");
        if (isRegistered) {
            System.out.println("Product registration complete");
        }
    }

    @Override
    public boolean validateItem() {
        return stockAvailable > 0 && warrantyMonths > 0;
    }

    public void registerProduct() {
        this.isRegistered = true;
        System.out.println("Product registered successfully with " + warrantyMonths + " months warranty.");
    }
}

// 2. ClothingItem
class ClothingItem extends ShoppingItem {
    private String size;
    private boolean isSeasonal;
    private double seasonalDiscount;

    public ClothingItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable, String size, boolean isSeasonal, double seasonalDiscount) {
        super(itemId, itemName, itemDescription, price, stockAvailable);
        this.size = size;
        this.isSeasonal = isSeasonal;
        this.seasonalDiscount = seasonalDiscount;
    }

    @Override
    public void updateStock(int quantity) {
        this.stockAvailable += quantity;
    }

    @Override
    public void addToCart(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter quantity for " + itemName + " (size: " + size + "): ");
        int quantity = scanner.nextInt();
        
        double finalPrice = price;
        if (isSeasonal) {
            finalPrice = price * (1 - seasonalDiscount);
            System.out.println("Seasonal discount applied: " + (seasonalDiscount * 100) + "%");
        }
        
        if (quantity <= stockAvailable) {
            customer.getShoppingCart().addItem(this, quantity, finalPrice);
            System.out.println(quantity + " " + itemName + "(s) added to cart.");
        } else {
            System.out.println("Not enough stock available. Only " + stockAvailable + " left.");
        }
    }

    @Override
    public void generateInvoice(Customer customer) {
        System.out.println("Clothing Item: " + itemName);
        System.out.println("Size: " + size);
        System.out.println("Price: $" + (isSeasonal ? price * (1 - seasonalDiscount) : price));
        if (isSeasonal) {
            System.out.println("(Includes seasonal discount of " + (seasonalDiscount * 100) + "%)");
        }
    }

    @Override
    public boolean validateItem() {
        return stockAvailable > 0 && !size.isEmpty();
    }
}

// 3. GroceriesItem
class GroceriesItem extends ShoppingItem {
    private Date expirationDate;
    private double bulkDiscount;

    public GroceriesItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable, Date expirationDate, double bulkDiscount) {
        super(itemId, itemName, itemDescription, price, stockAvailable);
        this.expirationDate = expirationDate;
        this.bulkDiscount = bulkDiscount;
    }

    @Override
    public void updateStock(int quantity) {
        this.stockAvailable += quantity;
    }

    @Override
    public void addToCart(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter quantity for " + itemName + ": ");
        int quantity = scanner.nextInt();
        
        double finalPrice = price;
        if (quantity > 5) { // bulk purchase
            finalPrice = price * (1 - bulkDiscount);
            System.out.println("Bulk discount applied: " + (bulkDiscount * 100) + "%");
        }
        
        if (quantity <= stockAvailable) {
            customer.getShoppingCart().addItem(this, quantity, finalPrice);
            System.out.println(quantity + " " + itemName + "(s) added to cart.");
        } else {
            System.out.println("Not enough stock available. Only " + stockAvailable + " left.");
        }
    }

    @Override
    public void generateInvoice(Customer customer) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Grocery Item: " + itemName);
        System.out.println("Price: $" + price);
        System.out.println("Expiration Date: " + sdf.format(expirationDate));
    }

    @Override
    public boolean validateItem() {
        Date today = new Date();
        return stockAvailable > 0 && expirationDate.after(today);
    }
}

// 4. BooksItem
class BooksItem extends ShoppingItem {
    private String isbn;
    private String edition;
    private String printQuality;

    public BooksItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable, String isbn, String edition, String printQuality) {
        super(itemId, itemName, itemDescription, price, stockAvailable);
        this.isbn = isbn;
        this.edition = edition;
        this.printQuality = printQuality;
    }

    @Override
    public void updateStock(int quantity) {
        this.stockAvailable += quantity;
    }

    @Override
    public void addToCart(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter quantity for " + itemName + " (" + edition + " edition): ");
        int quantity = scanner.nextInt();
        
        if (quantity <= stockAvailable) {
            customer.getShoppingCart().addItem(this, quantity);
            System.out.println(quantity + " " + itemName + "(s) added to cart.");
        } else {
            System.out.println("Not enough stock available. Only " + stockAvailable + " left.");
        }
    }

    @Override
    public void generateInvoice(Customer customer) {
        System.out.println("Book: " + itemName);
        System.out.println("Edition: " + edition);
        System.out.println("ISBN: " + isbn);
        System.out.println("Print Quality: " + printQuality);
        System.out.println("Price: $" + price);
    }

    @Override
    public boolean validateItem() {
        return stockAvailable > 0 && !isbn.isEmpty() && !edition.isEmpty();
    }
}

// 5. AccessoriesItem
class AccessoriesItem extends ShoppingItem {
    private String accessoryType;
    private double averageRating;
    private int reviewCount;

    public AccessoriesItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable, String accessoryType) {
        super(itemId, itemName, itemDescription, price, stockAvailable);
        this.accessoryType = accessoryType;
        this.averageRating = 0;
        this.reviewCount = 0;
    }

    @Override
    public void updateStock(int quantity) {
        this.stockAvailable += quantity;
    }

    @Override
    public void addToCart(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter quantity for " + itemName + " (" + accessoryType + "): ");
        int quantity = scanner.nextInt();
        
        if (quantity <= stockAvailable) {
            customer.getShoppingCart().addItem(this, quantity);
            System.out.println(quantity + " " + itemName + "(s) added to cart.");
        } else {
            System.out.println("Not enough stock available. Only " + stockAvailable + " left.");
        }
    }

    @Override
    public void generateInvoice(Customer customer) {
        System.out.println("Accessory: " + itemName);
        System.out.println("Type: " + accessoryType);
        System.out.println("Price: $" + price);
        if (reviewCount > 0) {
            System.out.println("Average Rating: " + String.format("%.1f", averageRating) + " (" + reviewCount + " reviews)");
        }
    }

    @Override
    public boolean validateItem() {
        return stockAvailable > 0 && !accessoryType.isEmpty();
    }

    public void addReview(double rating) {
        averageRating = (averageRating * reviewCount + rating) / (reviewCount + 1);
        reviewCount++;
    }
}

// Encapsulation Classes
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

class Payment {
    private String paymentId;
    private String paymentMethod;
    private double amountPaid;
    private Date transactionDate;
    private boolean isSuccessful;

    public Payment(String paymentId, String paymentMethod, double amountPaid, Date transactionDate) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
        this.transactionDate = transactionDate;
        this.isSuccessful = false;
    }

    public boolean processPayment() {
        // Simulate payment processing
        if (validatePayment()) {
            this.isSuccessful = true;
            return true;
        }
        return false;
    }

    private boolean validatePayment() {
        if (amountPaid <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        
        if (!paymentMethod.equals("Credit Card") && 
            !paymentMethod.equals("PayPal") && 
            !paymentMethod.equals("Bank Transfer")) {
            System.out.println("Invalid payment method.");
            return false;
        }
        
        return true;
    }

    // Getters
    public String getPaymentId() { return paymentId; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getAmountPaid() { return amountPaid; }
    public Date getTransactionDate() { return transactionDate; }
    public boolean isSuccessful() { return isSuccessful; }
}


