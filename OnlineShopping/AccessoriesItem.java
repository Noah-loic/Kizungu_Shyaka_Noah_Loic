import java.util.*;
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
