import java.util.*;
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
