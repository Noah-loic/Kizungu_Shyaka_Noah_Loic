import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
abstract class StockItem {
    protected String itemId;
    protected String itemName;
    protected int quantityInStock;
    protected double pricePerUnit;
    protected String category;
    protected String supplier;

    public StockItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category, String supplier) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantityInStock = quantityInStock;
        this.pricePerUnit = pricePerUnit;
        this.category = category;
        this.supplier = supplier;
    }

    // Abstract methods
    public abstract void updateStock(int quantity);
    public abstract double calculateStockValue();
    public abstract String generateStockReport();
    public abstract boolean validateStock();
    
    // Getters
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public int getQuantityInStock() { return quantityInStock; }
    public double getPricePerUnit() { return pricePerUnit; }
    public String getCategory() { return category; }
    public String getSupplier() { return supplier; }
}

// Concrete Class 1: ElectronicsItem
class ElectronicsItem extends StockItem {
    private int warrantyPeriod; // in months
    private double discount;

    public ElectronicsItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String supplier, int warrantyPeriod) {
        super(itemId, itemName, quantityInStock, pricePerUnit, "Electronics", supplier);
        this.warrantyPeriod = warrantyPeriod;
        this.discount = 0.0;
    }

    @Override
    public void updateStock(int quantity) {
        if (quantityInStock + quantity < 0) {
            System.out.println("Error: Stock cannot be negative");
        } else {
            quantityInStock += quantity;
            System.out.println("Stock updated. New quantity: " + quantityInStock);
        }
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit * (1 - discount);
    }

    @Override
    public String generateStockReport() {
        return String.format("Electronics Item Report:\n" +
                "ID: %s, Name: %s, Quantity: %d, Price: $%.2f, Warranty: %d months\n" +
                "Discount: %.1f%%, Stock Value: $%.2f, Supplier: %s",
                itemId, itemName, quantityInStock, pricePerUnit, warrantyPeriod,
                discount * 100, calculateStockValue(), supplier);
    }

    @Override
    public boolean validateStock() {
        return quantityInStock > 0;
    }

    public void applyDiscount(double discountPercentage) {
        if (discountPercentage > 0.5) {
            System.out.println("Error: Discount cannot exceed 50%");
        } else {
            this.discount = discountPercentage;
            System.out.printf("Discount of %.1f%% applied to %s\n", discountPercentage * 100, itemName);
        }
    }

    public int getWarrantyPeriod() { return warrantyPeriod; }
    public double getDiscount() { return discount; }
}

// Concrete Class 2: ClothingItem
class ClothingItem extends StockItem {
    private Map<String, Map<String, Integer>> sizeColorStock; // size -> (color -> quantity)
    private double discount;

    public ClothingItem(String itemId, String itemName, double pricePerUnit, String supplier) {
        super(itemId, itemName, 0, pricePerUnit, "Clothing", supplier);
        this.sizeColorStock = new HashMap<>();
        this.discount = 0.0;
    }

    public void addSizeColorStock(String size, String color, int quantity) {
        sizeColorStock.putIfAbsent(size, new HashMap<>());
        sizeColorStock.get(size).put(color, sizeColorStock.get(size).getOrDefault(color, 0) + quantity);
        quantityInStock += quantity;
    }

    @Override
    public void updateStock(int quantity) {
        System.out.println("For Clothing items, please use addSizeColorStock method to update specific size/color combinations");
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit * (1 - discount);
    }

    @Override
    public String generateStockReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Clothing Item Report:\nID: %s, Name: %s, Total Quantity: %d, Price: $%.2f\n",
                itemId, itemName, quantityInStock, pricePerUnit));
        report.append(String.format("Discount: %.1f%%, Stock Value: $%.2f, Supplier: %s\n", discount * 100, calculateStockValue(), supplier));
        report.append("Size/Color Breakdown:\n");
        
        for (Map.Entry<String, Map<String, Integer>> sizeEntry : sizeColorStock.entrySet()) {
            for (Map.Entry<String, Integer> colorEntry : sizeEntry.getValue().entrySet()) {
                report.append(String.format("  Size: %s, Color: %s, Quantity: %d\n", 
                        sizeEntry.getKey(), colorEntry.getKey(), colorEntry.getValue()));
            }
        }
        
        return report.toString();
    }

    @Override
    public boolean validateStock() {
        return quantityInStock > 0;
    }

    public void setDiscount(double discount) {
        if (discount > 0.5) {
            System.out.println("Error: Discount cannot exceed 50%");
        } else {
            this.discount = discount;
        }
    }
}

// Concrete Class 3: GroceryItem
class GroceryItem extends StockItem {
    private LocalDate expirationDate;
    private boolean nearExpiration;

    public GroceryItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String supplier, LocalDate expirationDate) {
        super(itemId, itemName, quantityInStock, pricePerUnit, "Grocery", supplier);
        this.expirationDate = expirationDate;
        checkExpiration();
    }

    private void checkExpiration() {
        LocalDate today = LocalDate.now();
        nearExpiration = expirationDate.isBefore(today.plusDays(7));
    }

    @Override
    public void updateStock(int quantity) {
        if (quantityInStock + quantity < 0) {
            System.out.println("Error: Stock cannot be negative");
        } else {
            quantityInStock += quantity;
            System.out.println("Stock updated. New quantity: " + quantityInStock);
        }
    }

    @Override
    public double calculateStockValue() {
        double multiplier = nearExpiration ? 0.7 : 1.0; // 30% discount if near expiration
        return quantityInStock * pricePerUnit * multiplier;
    }

    @Override
    public String generateStockReport() {
        String status = expirationDate.isBefore(LocalDate.now()) ? "EXPIRED" : 
                       nearExpiration ? "Near Expiration" : "OK";
        return String.format("Grocery Item Report:\n" +
                "ID: %s, Name: %s, Quantity: %d, Price: $%.2f\n" +
                "Expiration: %s, Status: %s, Stock Value: $%.2f, Supplier: %s",
                itemId, itemName, quantityInStock, pricePerUnit,
                expirationDate.format(DateTimeFormatter.ISO_DATE), status, calculateStockValue(), supplier);
    }

    @Override
    public boolean validateStock() {
        return !expirationDate.isBefore(LocalDate.now()) && quantityInStock > 0;
    }

    public LocalDate getExpirationDate() { return expirationDate; }
    public boolean isNearExpiration() { return nearExpiration; }
}

// Concrete Class 4: FurnitureItem
class FurnitureItem extends StockItem {
    private double weight; // in kg
    private boolean isPacked;

    public FurnitureItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String supplier, double weight) {
        super(itemId, itemName, quantityInStock, pricePerUnit, "Furniture", supplier);
        this.weight = weight;
        this.isPacked = false;
    }

    @Override
    public void updateStock(int quantity) {
        if (quantityInStock + quantity < 0) {
            System.out.println("Error: Stock cannot be negative");
        } else {
            quantityInStock += quantity;
            System.out.println("Stock updated. New quantity: " + quantityInStock);
        }
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit;
    }

    @Override
    public String generateStockReport() {
        return String.format("Furniture Item Report:\n" +
                "ID: %s, Name: %s, Quantity: %d, Price: $%.2f\n" +
                "Weight: %.2f kg, Packed: %s, Stock Value: $%.2f, Supplier: %s",
                itemId, itemName, quantityInStock, pricePerUnit,
                weight, isPacked ? "Yes" : "No", calculateStockValue(), supplier);
    }

    @Override
    public boolean validateStock() {
        return quantityInStock > 0;
    }

    public void packForDelivery() {
        isPacked = true;
        System.out.println(itemName + " has been packed for delivery");
    }

    public double calculateShippingCost(double costPerKg) {
        return weight * costPerKg;
    }
}

// Concrete Class 5: PerishableItem
class PerishableItem extends StockItem {
    private LocalDate expirationDate;
    private int shelfLife; // in days

    public PerishableItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String supplier, LocalDate expirationDate, int shelfLife) {
        super(itemId, itemName, quantityInStock, pricePerUnit, "Perishable", supplier);
        this.expirationDate = expirationDate;
        this.shelfLife = shelfLife;
    }

    @Override
    public void updateStock(int quantity) {
        if (quantityInStock + quantity < 0) {
            System.out.println("Error: Stock cannot be negative");
        } else {
            quantityInStock += quantity;
            System.out.println("Stock updated. New quantity: " + quantityInStock);
        }
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit;
    }

    @Override
    public String generateStockReport() {
        String status = expirationDate.isBefore(LocalDate.now()) ? "EXPIRED - NEEDS DISPOSAL" : 
                       expirationDate.isBefore(LocalDate.now().plusDays(2)) ? "URGENT - Near Expiration" : "OK";
        return String.format("Perishable Item Report:\n" +
                "ID: %s, Name: %s, Quantity: %d, Price: $%.2f\n" +
                "Expiration: %s, Shelf Life: %d days, Status: %s\n" +
                "Stock Value: $%.2f, Supplier: %s",
                itemId, itemName, quantityInStock, pricePerUnit,
                expirationDate.format(DateTimeFormatter.ISO_DATE), shelfLife, status, calculateStockValue(), supplier);
    }

    @Override
    public boolean validateStock() {
        return !expirationDate.isBefore(LocalDate.now()) && quantityInStock > 0;
    }

    public boolean needsDisposal() {
        return expirationDate.isBefore(LocalDate.now());
    }

    public LocalDate getExpirationDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExpirationDate'");
    }
}

// Encapsulation Class 1: Product
class Product {
    private String productId;
    private String productName;
    private String brand;
    private String supplier;
    private int stockQuantity;

    public Product(String productId, String productName, String brand, String supplier, int stockQuantity) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.supplier = supplier;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters with validation
    public String getProductId() { return productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.productName = productName;
    }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        this.brand = brand;
    }
    
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return String.format("Product[ID: %s, Name: %s, Brand: %s, Supplier: %s, Quantity: %d]",
                productId, productName, brand, supplier, stockQuantity);
    }
}

// Encapsulation Class 2: Supplier
class Supplier {
    private String supplierId;
    private String companyName;
    private String contactPerson;
    private String phone;
    private String email;

    public Supplier(String supplierId, String companyName, String contactPerson, String phone, String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }
        
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Getters and Setters with validation
    public String getSupplierId() { return supplierId; }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }
        this.phone = phone;
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Supplier[ID: %s, Company: %s, Contact: %s, Phone: %s, Email: %s]",
                supplierId, companyName, contactPerson, phone, email);
    }
}

// Encapsulation Class 3: Warehouse
class Warehouse {
    private String warehouseId;
    private String location;
    private double capacity; // in cubic meters
    private String managerName;
    private List<StockItem> inventory;

    public Warehouse(String warehouseId, String location, double capacity, String managerName) {
        this.warehouseId = warehouseId;
        this.location = location;
        this.capacity = capacity;
        this.managerName = managerName;
        this.inventory = new ArrayList<>();
    }

    public void addItemToInventory(StockItem item) {
        inventory.add(item);
        System.out.println(item.getItemName() + " added to warehouse inventory");
    }

    public void removeItemFromInventory(StockItem item) {
        if (inventory.remove(item)) {
            System.out.println(item.getItemName() + " removed from warehouse inventory");
        } else {
            System.out.println("Item not found in warehouse inventory");
        }
    }

    public double calculateUsedCapacity() {
        // Simplified calculation - in real system would consider item dimensions
        return inventory.size() * 0.1; // Assuming each item takes 0.1 cubic meters
    }

    public String generateInventoryReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Warehouse %s (%s) Inventory Report\n", warehouseId, location));
        report.append(String.format("Manager: %s, Capacity: %.1f/%.1f cubic meters\n", 
                managerName, calculateUsedCapacity(), capacity));
        report.append("Items in Inventory:\n");
        
        for (StockItem item : inventory) {
            report.append("  ").append(item.generateStockReport()).append("\n");
        }
        
        return report.toString();
    }

    // Getters and Setters
    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public double getCapacity() { return capacity; }
    public void setCapacity(double capacity) { this.capacity = capacity; }
    
    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    
    public List<StockItem> getInventory() { return new ArrayList<>(inventory); }
}

// Main Method to Test the System



