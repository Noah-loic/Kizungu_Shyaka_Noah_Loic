import java.util.*;
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
