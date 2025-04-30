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


// 2. ClothingItem


// 3. GroceriesItem

// 4. BooksItem


// 5. AccessoriesItem


// Encapsulation Classes






