import java.util.*;
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