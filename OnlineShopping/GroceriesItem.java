import java.text.SimpleDateFormat;
import java.util.*;
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

