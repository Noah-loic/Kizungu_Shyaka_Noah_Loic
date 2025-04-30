import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create sample products
        ElectronicsItem laptop = new ElectronicsItem("E001", "Laptop", "15-inch, 16GB RAM, 512GB SSD", 999.99, 10, 24);
        ClothingItem tshirt = new ClothingItem("C001", "T-Shirt", "Cotton, short sleeve", 19.99, 50, "M", true, 0.2);
        GroceriesItem apple = new GroceriesItem("G001", "Apple", "Fresh red apples", 0.99, 200, new Date(System.currentTimeMillis() + 86400000 * 7), 0.1);
        BooksItem novel = new BooksItem("B001", "Great Novel", "Bestselling fiction", 12.99, 30, "978-1234567890", "First", "High");
        AccessoriesItem watch = new AccessoriesItem("A001", "Smart Watch", "Water resistant, fitness tracking", 199.99, 15, "Wearable");
        
        // Add reviews to accessory
        watch.addReview(4.5);
        watch.addReview(5.0);
        watch.addReview(3.5);
        
        // Customer registration
        System.out.println("Welcome to the Online Shopping System!");
        System.out.println("Please register to continue:");
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();
        
        Customer customer = new Customer(UUID.randomUUID().toString(), name, email, address, phone);
        
        if (!customer.validateDetails()) {
            System.out.println("Invalid customer details. Please try again.");
            return;
        }
        
        customer.displayCustomerInfo();
        
        // Shopping loop
        boolean shopping = true;
        while (shopping) {
            System.out.println("\nAvailable Products:");
            System.out.println("1. " + laptop.getItemName() + " - $" + laptop.getPrice());
            System.out.println("2. " + tshirt.getItemName() + " - $" + tshirt.getPrice());
            System.out.println("3. " + apple.getItemName() + " - $" + apple.getPrice());
            System.out.println("4. " + novel.getItemName() + " - $" + novel.getPrice());
            System.out.println("5. " + watch.getItemName() + " - $" + watch.getPrice());
            System.out.println("6. View Cart");
            System.out.println("7. Checkout");
            System.out.println("8. Exit");
            
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    laptop.addToCart(customer);
                    break;
                case 2:
                    tshirt.addToCart(customer);
                    break;
                case 3:
                    apple.addToCart(customer);
                    break;
                case 4:
                    novel.addToCart(customer);
                    break;
                case 5:
                    watch.addToCart(customer);
                    break;
                case 6:
                    customer.getShoppingCart().displayCart();
                    break;
                case 7:
                    customer.getShoppingCart().checkout();
                    break;
                case 8:
                    shopping = false;
                    System.out.println("Thank you for visiting our store!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}
    

