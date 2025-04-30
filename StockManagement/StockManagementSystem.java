import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StockManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<StockItem> stockItems = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Supplier> suppliers = new ArrayList<>();
        List<Warehouse> warehouses = new ArrayList<>();

        System.out.println("Advanced Stock Management System");
        System.out.println("--------------------------------");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Manage Stock Items");
            System.out.println("2. Manage Products");
            System.out.println("3. Manage Suppliers");
            System.out.println("4. Manage Warehouses");
            System.out.println("5. Generate Reports");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    manageStockItems(scanner, stockItems);
                    break;
                case 2:
                    manageProducts(scanner, products);
                    break;
                case 3:
                    manageSuppliers(scanner, suppliers);
                    break;
                case 4:
                    manageWarehouses(scanner, warehouses, stockItems);
                    break;
                case 5:
                    generateReports(scanner, stockItems, products, suppliers, warehouses);
                    break;
                case 6:
                    System.out.println("Exiting Stock Management System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageStockItems(Scanner scanner, List<StockItem> stockItems) {
        while (true) {
            System.out.println("\nStock Items Management:");
            System.out.println("1. Add Electronic Item");
            System.out.println("2. Add Clothing Item");
            System.out.println("3. Add Grocery Item");
            System.out.println("4. Add Furniture Item");
            System.out.println("5. Add Perishable Item");
            System.out.println("6. Update Stock Quantity");
            System.out.println("7. View All Stock Items");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addElectronicItem(scanner, stockItems);
                    break;
                case 2:
                    addClothingItem(scanner, stockItems);
                    break;
                case 3:
                    addGroceryItem(scanner, stockItems);
                    break;
                case 4:
                    addFurnitureItem(scanner, stockItems);
                    break;
                case 5:
                    addPerishableItem(scanner, stockItems);
                    break;
                case 6:
                    updateStockQuantity(scanner, stockItems);
                    break;
                case 7:
                    viewAllStockItems(stockItems);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addElectronicItem(Scanner scanner, List<StockItem> stockItems) {
        System.out.println("\nAdding New Electronic Item:");
        System.out.print("Enter Item ID: ");
        String itemId = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Quantity in Stock: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price per Unit: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Supplier: ");
        String supplier = scanner.nextLine();
        System.out.print("Enter Warranty Period (months): ");
        int warranty = scanner.nextInt();
        
        ElectronicsItem item = new ElectronicsItem(itemId, itemName, quantity, price, supplier, warranty);
        stockItems.add(item);
        System.out.println("Electronic item added successfully!");
    }

    private static void addClothingItem(Scanner scanner, List<StockItem> stockItems) {
        System.out.println("\nAdding New Clothing Item:");
        System.out.print("Enter Item ID: ");
        String itemId = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Price per Unit: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Supplier: ");
        String supplier = scanner.nextLine();
        
        ClothingItem item = new ClothingItem(itemId, itemName, price, supplier);
        
        while (true) {
            System.out.print("Add size/color/quantity (or 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;
            
            String[] parts = input.split("/");
            if (parts.length != 3) {
                System.out.println("Invalid format. Use size/color/quantity");
                continue;
            }
            
            try {
                int qty = Integer.parseInt(parts[2]);
                item.addSizeColorStock(parts[0], parts[1], qty);
                System.out.println("Size/color/quantity added.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Please enter a number.");
            }
        }
        
        stockItems.add(item);
        System.out.println("Clothing item added successfully!");
    }

    private static void addGroceryItem(Scanner scanner, List<StockItem> stockItems) {
        System.out.println("\nAdding New Grocery Item:");
        System.out.print("Enter Item ID: ");
        String itemId = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Quantity in Stock: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price per Unit: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Supplier: ");
        String supplier = scanner.nextLine();
        System.out.print("Enter Expiration Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate expirationDate = LocalDate.parse(dateStr);
        
        GroceryItem item = new GroceryItem(itemId, itemName, quantity, price, supplier, expirationDate);
        stockItems.add(item);
        System.out.println("Grocery item added successfully!");
    }

    private static void addFurnitureItem(Scanner scanner, List<StockItem> stockItems) {
        System.out.println("\nAdding New Furniture Item:");
        System.out.print("Enter Item ID: ");
        String itemId = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Quantity in Stock: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price per Unit: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Supplier: ");
        String supplier = scanner.nextLine();
        System.out.print("Enter Weight (kg): ");
        double weight = scanner.nextDouble();
        
        FurnitureItem item = new FurnitureItem(itemId, itemName, quantity, price, supplier, weight);
        stockItems.add(item);
        System.out.println("Furniture item added successfully!");
    }

    private static void addPerishableItem(Scanner scanner, List<StockItem> stockItems) {
        System.out.println("\nAdding New Perishable Item:");
        System.out.print("Enter Item ID: ");
        String itemId = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Quantity in Stock: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price per Unit: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Supplier: ");
        String supplier = scanner.nextLine();
        System.out.print("Enter Expiration Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate expirationDate = LocalDate.parse(dateStr);
        System.out.print("Enter Shelf Life (days): ");
        int shelfLife = scanner.nextInt();
        
        PerishableItem item = new PerishableItem(itemId, itemName, quantity, price, supplier, expirationDate, shelfLife);
        stockItems.add(item);
        System.out.println("Perishable item added successfully!");
    }

    private static void updateStockQuantity(Scanner scanner, List<StockItem> stockItems) {
        if (stockItems.isEmpty()) {
            System.out.println("No stock items available to update.");
            return;
        }
        
        System.out.println("\nSelect Item to Update:");
        for (int i = 0; i < stockItems.size(); i++) {
            System.out.printf("%d. %s (ID: %s, Current Qty: %d)\n",
                    i + 1, stockItems.get(i).getItemName(), 
                    stockItems.get(i).getItemId(), 
                    stockItems.get(i).getQuantityInStock());
        }
        
        System.out.print("Enter item number: ");
        int itemNum = scanner.nextInt();
        if (itemNum < 1 || itemNum > stockItems.size()) {
            System.out.println("Invalid item number.");
            return;
        }
        
        System.out.print("Enter quantity to add (use negative to subtract): ");
        int quantity = scanner.nextInt();
        
        StockItem item = stockItems.get(itemNum - 1);
        item.updateStock(quantity);
    }

    private static void viewAllStockItems(List<StockItem> stockItems) {
        if (stockItems.isEmpty()) {
            System.out.println("No stock items available.");
            return;
        }
        
        System.out.println("\nAll Stock Items:");
        for (StockItem item : stockItems) {
            System.out.println(item.generateStockReport());
            System.out.println("-----------------------------");
        }
    }

    private static void manageProducts(Scanner scanner, List<Product> products) {
        while (true) {
            System.out.println("\nProducts Management:");
            System.out.println("1. Add New Product");
            System.out.println("2. Update Product");
            System.out.println("3. View All Products");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addProduct(scanner, products);
                    break;
                case 2:
                    updateProduct(scanner, products);
                    break;
                case 3:
                    viewAllProducts(products);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner, List<Product> products) {
        System.out.println("\nAdding New Product:");
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Supplier: ");
        String supplier = scanner.nextLine();
        System.out.print("Enter Stock Quantity: ");
        int quantity = scanner.nextInt();
        
        try {
            Product product = new Product(productId, productName, brand, supplier, quantity);
            products.add(product);
            System.out.println("Product added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateProduct(Scanner scanner, List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products available to update.");
            return;
        }
        
        System.out.println("\nSelect Product to Update:");
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, products.get(i));
        }
        
        System.out.print("Enter product number: ");
        int productNum = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (productNum < 1 || productNum > products.size()) {
            System.out.println("Invalid product number.");
            return;
        }
        
        Product product = products.get(productNum - 1);
        
        System.out.println("Current Product Details:");
        System.out.println(product);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Product Name");
        System.out.println("2. Brand");
        System.out.println("3. Supplier");
        System.out.println("4. Stock Quantity");
        System.out.println("5. Cancel");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            switch (choice) {
                case 1:
                    System.out.print("Enter new Product Name: ");
                    String newName = scanner.nextLine();
                    product.setProductName(newName);
                    System.out.println("Product name updated.");
                    break;
                case 2:
                    System.out.print("Enter new Brand: ");
                    String newBrand = scanner.nextLine();
                    product.setBrand(newBrand);
                    System.out.println("Brand updated.");
                    break;
                case 3:
                    System.out.print("Enter new Supplier: ");
                    String newSupplier = scanner.nextLine();
                    product.setSupplier(newSupplier);
                    System.out.println("Supplier updated.");
                    break;
                case 4:
                    System.out.print("Enter new Stock Quantity: ");
                    int newQty = scanner.nextInt();
                    product.setStockQuantity(newQty);
                    System.out.println("Stock quantity updated.");
                    break;
                case 5:
                    System.out.println("Update cancelled.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        
        System.out.println("\nAll Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void manageSuppliers(Scanner scanner, List<Supplier> suppliers) {
        while (true) {
            System.out.println("\nSuppliers Management:");
            System.out.println("1. Add New Supplier");
            System.out.println("2. Update Supplier");
            System.out.println("3. View All Suppliers");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addSupplier(scanner, suppliers);
                    break;
                case 2:
                    updateSupplier(scanner, suppliers);
                    break;
                case 3:
                    viewAllSuppliers(suppliers);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addSupplier(Scanner scanner, List<Supplier> suppliers) {
        System.out.println("\nAdding New Supplier:");
        System.out.print("Enter Supplier ID: ");
        String supplierId = scanner.nextLine();
        System.out.print("Enter Company Name: ");
        String companyName = scanner.nextLine();
        System.out.print("Enter Contact Person: ");
        String contactPerson = scanner.nextLine();
        System.out.print("Enter Phone (10 digits): ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        try {
            Supplier supplier = new Supplier(supplierId, companyName, contactPerson, phone, email);
            suppliers.add(supplier);
            System.out.println("Supplier added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateSupplier(Scanner scanner, List<Supplier> suppliers) {
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available to update.");
            return;
        }
        
        System.out.println("\nSelect Supplier to Update:");
        for (int i = 0; i < suppliers.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, suppliers.get(i));
        }
        
        System.out.print("Enter supplier number: ");
        int supplierNum = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (supplierNum < 1 || supplierNum > suppliers.size()) {
            System.out.println("Invalid supplier number.");
            return;
        }
        
        Supplier supplier = suppliers.get(supplierNum - 1);
        
        System.out.println("Current Supplier Details:");
        System.out.println(supplier);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Company Name");
        System.out.println("2. Contact Person");
        System.out.println("3. Phone");
        System.out.println("4. Email");
        System.out.println("5. Cancel");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            switch (choice) {
                case 1:
                    System.out.print("Enter new Company Name: ");
                    String newCompany = scanner.nextLine();
                    supplier.setCompanyName(newCompany);
                    System.out.println("Company name updated.");
                    break;
                case 2:
                    System.out.print("Enter new Contact Person: ");
                    String newContact = scanner.nextLine();
                    supplier.setContactPerson(newContact);
                    System.out.println("Contact person updated.");
                    break;
                case 3:
                    System.out.print("Enter new Phone: ");
                    String newPhone = scanner.nextLine();
                    supplier.setPhone(newPhone);
                    System.out.println("Phone updated.");
                    break;
                case 4:
                    System.out.print("Enter new Email: ");
                    String newEmail = scanner.nextLine();
                    supplier.setEmail(newEmail);
                    System.out.println("Email updated.");
                    break;
                case 5:
                    System.out.println("Update cancelled.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllSuppliers(List<Supplier> suppliers) {
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available.");
            return;
        }
        
        System.out.println("\nAll Suppliers:");
        for (Supplier supplier : suppliers) {
            System.out.println(supplier);
        }
    }

    private static void manageWarehouses(Scanner scanner, List<Warehouse> warehouses, List<StockItem> stockItems) {
        while (true) {
            System.out.println("\nWarehouses Management:");
            System.out.println("1. Add New Warehouse");
            System.out.println("2. Add Item to Warehouse");
            System.out.println("3. View Warehouse Inventory");
            System.out.println("4. View All Warehouses");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addWarehouse(scanner, warehouses);
                    break;
                case 2:
                    addItemToWarehouse(scanner, warehouses, stockItems);
                    break;
                case 3:
                    viewWarehouseInventory(scanner, warehouses);
                    break;
                case 4:
                    viewAllWarehouses(warehouses);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addWarehouse(Scanner scanner, List<Warehouse> warehouses) {
        System.out.println("\nAdding New Warehouse:");
        System.out.print("Enter Warehouse ID: ");
        String warehouseId = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Capacity (cubic meters): ");
        double capacity = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Manager Name: ");
        String managerName = scanner.nextLine();
        
        Warehouse warehouse = new Warehouse(warehouseId, location, capacity, managerName);
        warehouses.add(warehouse);
        System.out.println("Warehouse added successfully!");
    }

    private static void addItemToWarehouse(Scanner scanner, List<Warehouse> warehouses, List<StockItem> stockItems) {
        if (warehouses.isEmpty()) {
            System.out.println("No warehouses available.");
            return;
        }
        if (stockItems.isEmpty()) {
            System.out.println("No stock items available to add.");
            return;
        }
        
        System.out.println("\nSelect Warehouse:");
        for (int i = 0; i < warehouses.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, 
                    warehouses.get(i).getWarehouseId(), 
                    warehouses.get(i).getLocation());
        }
        System.out.print("Enter warehouse number: ");
        int warehouseNum = scanner.nextInt();
        if (warehouseNum < 1 || warehouseNum > warehouses.size()) {
            System.out.println("Invalid warehouse number.");
            return;
        }
        
        Warehouse warehouse = warehouses.get(warehouseNum - 1);
        
        System.out.println("\nSelect Item to Add:");
        for (int i = 0; i < stockItems.size(); i++) {
            System.out.printf("%d. %s (ID: %s)\n", i + 1, 
                    stockItems.get(i).getItemName(), 
                    stockItems.get(i).getItemId());
        }
        System.out.print("Enter item number: ");
        int itemNum = scanner.nextInt();
        if (itemNum < 1 || itemNum > stockItems.size()) {
            System.out.println("Invalid item number.");
            return;
        }
        
        StockItem item = stockItems.get(itemNum - 1);
        warehouse.addItemToInventory(item);
    }

    private static void viewWarehouseInventory(Scanner scanner, List<Warehouse> warehouses) {
        if (warehouses.isEmpty()) {
            System.out.println("No warehouses available.");
            return;
        }
        
        System.out.println("\nSelect Warehouse:");
        for (int i = 0; i < warehouses.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, 
                    warehouses.get(i).getWarehouseId(), 
                    warehouses.get(i).getLocation());
        }
        System.out.print("Enter warehouse number: ");
        int warehouseNum = scanner.nextInt();
        if (warehouseNum < 1 || warehouseNum > warehouses.size()) {
            System.out.println("Invalid warehouse number.");
            return;
        }
        
        Warehouse warehouse = warehouses.get(warehouseNum - 1);
        System.out.println(warehouse.generateInventoryReport());
    }

    private static void viewAllWarehouses(List<Warehouse> warehouses) {
        if (warehouses.isEmpty()) {
            System.out.println("No warehouses available.");
            return;
        }
        
        System.out.println("\nAll Warehouses:");
        for (Warehouse warehouse : warehouses) {
            System.out.printf("Warehouse %s at %s (Manager: %s)\n", 
                    warehouse.getWarehouseId(), 
                    warehouse.getLocation(), 
                    warehouse.getManagerName());
            System.out.printf("Capacity: %.1f/%.1f cubic meters\n", 
                    warehouse.calculateUsedCapacity(), 
                    warehouse.getCapacity());
            System.out.println("-----------------------------");
        }
    }

    private static void generateReports(Scanner scanner, List<StockItem> stockItems, 
                                      List<Product> products, List<Supplier> suppliers, 
                                      List<Warehouse> warehouses) {
        while (true) {
            System.out.println("\nReport Generation:");
            System.out.println("1. Stock Items Report");
            System.out.println("2. Products Report");
            System.out.println("3. Suppliers Report");
            System.out.println("4. Warehouses Report");
            System.out.println("5. Comprehensive Inventory Report");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    generateStockItemsReport(stockItems);
                    break;
                case 2:
                    generateProductsReport(products);
                    break;
                case 3:
                    generateSuppliersReport(suppliers);
                    break;
                case 4:
                    generateWarehousesReport(warehouses);
                    break;
                case 5:
                    generateComprehensiveReport(stockItems, products, suppliers, warehouses);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void generateStockItemsReport(List<StockItem> stockItems) {
        if (stockItems.isEmpty()) {
            System.out.println("No stock items available for report.");
            return;
        }
        
        System.out.println("\nStock Items Report:");
        System.out.println("===================");
        
        double totalValue = 0;
        Map<String, Integer> categoryCount = new HashMap<>();
        Map<String, Double> categoryValue = new HashMap<>();
        
        for (StockItem item : stockItems) {
            System.out.println(item.generateStockReport());
            System.out.println("-----------------------------");
            
            totalValue += item.calculateStockValue();
            categoryCount.put(item.getCategory(), categoryCount.getOrDefault(item.getCategory(), 0) + 1);
            categoryValue.put(item.getCategory(), categoryValue.getOrDefault(item.getCategory(), 0.0) + item.calculateStockValue());
        }
        
        System.out.println("\nSummary:");
        System.out.printf("Total Stock Value: $%.2f\n", totalValue);
        System.out.println("Items by Category:");
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.printf("  %s: %d items (Value: $%.2f)\n", 
                    entry.getKey(), entry.getValue(), categoryValue.get(entry.getKey()));
        }
    }

    private static void generateProductsReport(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products available for report.");
            return;
        }
        
        System.out.println("\nProducts Report:");
        System.out.println("================");
        
        int totalQuantity = 0;
        Map<String, Integer> brandCount = new HashMap<>();
        
        for (Product product : products) {
            System.out.println(product);
            totalQuantity += product.getStockQuantity();
            brandCount.put(product.getBrand(), brandCount.getOrDefault(product.getBrand(), 0) + 1);
        }
        
        System.out.println("\nSummary:");
        System.out.printf("Total Products: %d\n", products.size());
        System.out.printf("Total Stock Quantity: %d\n", totalQuantity);
        System.out.println("Products by Brand:");
        for (Map.Entry<String, Integer> entry : brandCount.entrySet()) {
            System.out.printf("  %s: %d products\n", entry.getKey(), entry.getValue());
        }
    }

    private static void generateSuppliersReport(List<Supplier> suppliers) {
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available for report.");
            return;
        }
        
        System.out.println("\nSuppliers Report:");
        System.out.println("================");
        
        for (Supplier supplier : suppliers) {
            System.out.println(supplier);
        }
        
        System.out.println("\nSummary:");
        System.out.printf("Total Suppliers: %d\n", suppliers.size());
    }

    private static void generateWarehousesReport(List<Warehouse> warehouses) {
        if (warehouses.isEmpty()) {
            System.out.println("No warehouses available for report.");
            return;
        }
        
        System.out.println("\nWarehouses Report:");
        System.out.println("=================");
        
        double totalCapacity = 0;
        double usedCapacity = 0;
        
        for (Warehouse warehouse : warehouses) {
            System.out.println(warehouse.generateInventoryReport());
            System.out.println("-----------------------------");
            
            totalCapacity += warehouse.getCapacity();
            usedCapacity += warehouse.calculateUsedCapacity();
        }
        
        System.out.println("\nSummary:");
        System.out.printf("Total Warehouses: %d\n", warehouses.size());
        System.out.printf("Total Capacity: %.1f cubic meters\n", totalCapacity);
        System.out.printf("Used Capacity: %.1f cubic meters (%.1f%% utilization)\n", 
                usedCapacity, (usedCapacity / totalCapacity) * 100);
    }

    private static void generateComprehensiveReport(List<StockItem> stockItems, 
                                                 List<Product> products, 
                                                 List<Supplier> suppliers, 
                                                 List<Warehouse> warehouses) {
        System.out.println("\nComprehensive Inventory Report");
        System.out.println("==============================");
        System.out.println("Generated on: " + LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        
        // Stock Items Summary
        System.out.println("\nStock Items Summary:");
        System.out.println("-------------------");
        if (stockItems.isEmpty()) {
            System.out.println("No stock items available.");
        } else {
            double totalStockValue = stockItems.stream().mapToDouble(StockItem::calculateStockValue).sum();
            System.out.printf("Total Items: %d, Total Stock Value: $%.2f\n", stockItems.size(), totalStockValue);
            
            Map<String, Long> categoryCount = stockItems.stream()
                .collect(Collectors.groupingBy(StockItem::getCategory, Collectors.counting()));
            
            System.out.println("Items by Category:");
            categoryCount.forEach((category, count) -> 
                System.out.printf("  %s: %d items\n", category, count));
        }
        
        // Products Summary
        System.out.println("\nProducts Summary:");
        System.out.println("----------------");
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            int totalProducts = products.size();
            int totalProductQuantity = products.stream().mapToInt(Product::getStockQuantity).sum();
            System.out.printf("Total Products: %d, Total Quantity: %d\n", totalProducts, totalProductQuantity);
        }
        
        // Suppliers Summary
        System.out.println("\nSuppliers Summary:");
        System.out.println("-----------------");
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available.");
        } else {
            System.out.printf("Total Suppliers: %d\n", suppliers.size());
        }
        
        // Warehouses Summary
        System.out.println("\nWarehouses Summary:");
        System.out.println("------------------");
        if (warehouses.isEmpty()) {
            System.out.println("No warehouses available.");
        } else {
            double totalCapacity = warehouses.stream().mapToDouble(Warehouse::getCapacity).sum();
            double usedCapacity = warehouses.stream().mapToDouble(Warehouse::calculateUsedCapacity).sum();
            System.out.printf("Total Warehouses: %d\n", warehouses.size());
            System.out.printf("Total Capacity: %.1f cubic meters\n", totalCapacity);
            System.out.printf("Used Capacity: %.1f cubic meters (%.1f%% utilization)\n", 
                    usedCapacity, (usedCapacity / totalCapacity) * 100);
        }
        
        // Alert Section
        System.out.println("\nAlerts:");
        System.out.println("-------");
        
        // Expired or near-expiration items
        if (!stockItems.isEmpty()) {
            System.out.println("Expired or Near-Expiration Items:");
            boolean hasAlerts = false;
            
            for (StockItem item : stockItems) {
                if (item instanceof GroceryItem) {
                    GroceryItem grocery = (GroceryItem) item;
                    if (grocery.getExpirationDate().isBefore(LocalDate.now())) {
                        System.out.printf("  EXPIRED: %s (ID: %s) - Expired on %s\n", 
                                grocery.getItemName(), grocery.getItemId(),
                                grocery.getExpirationDate().format(DateTimeFormatter.ISO_DATE));
                        hasAlerts = true;
                    } else if (grocery.isNearExpiration()) {
                        System.out.printf("  NEAR EXPIRATION: %s (ID: %s) - Expires on %s\n", 
                                grocery.getItemName(), grocery.getItemId(),
                                grocery.getExpirationDate().format(DateTimeFormatter.ISO_DATE));
                        hasAlerts = true;
                    }
                } else if (item instanceof PerishableItem) {
                    PerishableItem perishable = (PerishableItem) item;
                    if (perishable.needsDisposal()) {
                        System.out.printf("  NEEDS DISPOSAL: %s (ID: %s) - Expired on %s\n", 
                                perishable.getItemName(), perishable.getItemId(),
                                perishable.getExpirationDate().format(DateTimeFormatter.ISO_DATE));
                        hasAlerts = true;
                    }
                }
            }
            
            if (!hasAlerts) {
                System.out.println("  No expired or near-expiration items.");
            }
        }
        
        // Low stock items
        if (!stockItems.isEmpty()) {
            System.out.println("\nLow Stock Items (quantity < 10):");
            boolean hasLowStock = false;
            
            for (StockItem item : stockItems) {
                if (item.getQuantityInStock() < 10 && item.getQuantityInStock() > 0) {
                    System.out.printf("  LOW STOCK: %s (ID: %s) - Only %d left\n", 
                            item.getItemName(), item.getItemId(), item.getQuantityInStock());
                    hasLowStock = true;
                }
            }
            
            if (!hasLowStock) {
                System.out.println("  No items with low stock.");
            }
        }
        
        // Out of stock items
        if (!stockItems.isEmpty()) {
            System.out.println("\nOut of Stock Items:");
            boolean hasOutOfStock = false;
            
            for (StockItem item : stockItems) {
                if (item.getQuantityInStock() == 0) {
                    System.out.printf("  OUT OF STOCK: %s (ID: %s)\n", 
                            item.getItemName(), item.getItemId());
                    hasOutOfStock = true;
                }
            }
            
            if (!hasOutOfStock) {
                System.out.println("  No items out of stock.");
            }
        }
    }
}

