import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<InsurancePolicy> policies = new ArrayList<>();
    private static List<Claim> claims = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Advanced Motor Vehicle Insurance System");
        
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Policy");
            System.out.println("2. Process Claim");
            System.out.println("3. Generate Reports");
            System.out.println("4. Display All Policies");
            System.out.println("5. Display All Claims");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    createPolicy();
                    break;
                case 2:
                    processClaim();
                    break;
                case 3:
                    generateReports();
                    break;
                case 4:
                    displayAllPolicies();
                    break;
                case 5:
                    displayAllClaims();
                    break;
                case 6:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createPolicy() {
        System.out.println("\nCreate New Insurance Policy");
        
        // Get policy holder details
        System.out.print("Enter person ID: ");
        String personId = scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        
        Person policyHolder = new Person(personId, fullName, dob, email, phone);
        
        // Get vehicle details
        System.out.print("\nEnter vehicle ID: ");
        String vehicleId = scanner.nextLine();
        System.out.print("Enter vehicle make: ");
        String vehicleMake = scanner.nextLine();
        System.out.print("Enter vehicle model: ");
        String vehicleModel = scanner.nextLine();
        System.out.print("Enter vehicle year: ");
        int vehicleYear = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter vehicle type (Car/SUV/Truck): ");
        String vehicleType = scanner.nextLine();
        
        Vehicle vehicle = new Vehicle(vehicleId, vehicleMake, vehicleModel, vehicleYear, vehicleType);
        
        // Get policy details
        System.out.print("\nEnter policy ID: ");
        String policyId = scanner.nextLine();
        System.out.print("Enter coverage amount: ");
        double coverageAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter policy start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter policy end date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        
        // Select policy type
        System.out.println("\nSelect Policy Type:");
        System.out.println("1. Comprehensive");
        System.out.println("2. Third Party");
        System.out.println("3. Collision");
        System.out.println("4. Liability");
        System.out.println("5. Roadside Assistance");
        System.out.print("Enter choice: ");
        int policyType = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        InsurancePolicy policy = null;
        
        switch (policyType) {
            case 1:
                policy = new ComprehensivePolicy(policyId, vehicle, policyHolder, 
                                               coverageAmount, startDate, endDate);
                break;
            case 2:
                System.out.print("Include extended coverage? (yes/no): ");
                boolean extendedCoverage = scanner.nextLine().equalsIgnoreCase("yes");
                policy = new ThirdPartyPolicy(policyId, vehicle, policyHolder, 
                                           coverageAmount, startDate, endDate, extendedCoverage);
                break;
            case 3:
                System.out.print("Is the driver eligible for safe driver discount? (yes/no): ");
                boolean safeDriver = scanner.nextLine().equalsIgnoreCase("yes");
                policy = new CollisionPolicy(policyId, vehicle, policyHolder, 
                                          coverageAmount, startDate, endDate, safeDriver);
                break;
            case 4:
                System.out.print("Include extended disability coverage? (yes/no): ");
                boolean extendedDisability = scanner.nextLine().equalsIgnoreCase("yes");
                policy = new LiabilityPolicy(policyId, vehicle, policyHolder, 
                                          coverageAmount, startDate, endDate, extendedDisability);
                break;
            case 5:
                System.out.print("Is this a commercial vehicle? (yes/no): ");
                boolean commercial = scanner.nextLine().equalsIgnoreCase("yes");
                policy = new RoadsideAssistancePolicy(policyId, vehicle, policyHolder, 
                                                   coverageAmount, startDate, endDate, commercial);
                break;
            default:
                System.out.println("Invalid policy type.");
                return;
        }
        
        // Validate and calculate premium
        if (policy.validatePolicy()) {
            policy.calculatePremium();
            policies.add(policy);
            System.out.println("Policy created successfully!");
            System.out.println(policy.generatePolicyReport());
        } else {
            System.out.println("Policy validation failed. Policy not created.");
        }
    }

    private static void processClaim() {
        if (policies.isEmpty()) {
            System.out.println("No policies available to make a claim.");
            return;
        }
        
        System.out.println("\nProcess Claim");
        System.out.print("Enter policy ID: ");
        String policyId = scanner.nextLine();
        
        InsurancePolicy policy = null;
        for (InsurancePolicy p : policies) {
            if (p.getPolicyId().equals(policyId)) {
                policy = p;
                break;
            }
        }
        
        if (policy == null) {
            System.out.println("Policy not found.");
            return;
        }
        
        System.out.print("Enter claim amount: ");
        double claimAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter claim date (YYYY-MM-DD): ");
        LocalDate claimDate = LocalDate.parse(scanner.nextLine());
        
        boolean claimResult = policy.processClaim(claimAmount);
        String claimStatus = claimResult ? "Approved" : "Rejected";
        
        String claimId = "CLM-" + System.currentTimeMillis();
        Claim claim = new Claim(claimId, claimAmount, claimDate, claimStatus, policy);
        claims.add(claim);
        
        System.out.println("Claim " + claimStatus + " with ID: " + claimId);
    }

    private static void generateReports() {
        System.out.println("\nInsurance System Reports");
        
        // Premiums collected
        double totalPremiums = policies.stream().mapToDouble(InsurancePolicy::getPremiumAmount).sum();
        System.out.println("Total Premiums Collected: $" + totalPremiums);
        
        // Claims processed
        System.out.println("\nClaims Summary:");
        long approvedClaims = claims.stream().filter(c -> c.getClaimStatus().equals("Approved")).count();
        long rejectedClaims = claims.stream().filter(c -> c.getClaimStatus().equals("Rejected")).count();
        System.out.println("Approved Claims: " + approvedClaims);
        System.out.println("Rejected Claims: " + rejectedClaims);
        
        // Coverage by policy type
        System.out.println("\nCoverage by Policy Type:");
        policies.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                p -> p.getClass().getSimpleName(),
                java.util.stream.Collectors.summingDouble(InsurancePolicy::getCoverageAmount)
            ))
            .forEach((type, coverage) -> System.out.println(type + ": $" + coverage));
    }

    private static void displayAllPolicies() {
        if (policies.isEmpty()) {
            System.out.println("No policies available.");
            return;
        }
        
        System.out.println("\nAll Insurance Policies:");
        for (InsurancePolicy policy : policies) {
            policy.display();
            System.out.println("Policy Type: " + policy.getClass().getSimpleName());
            System.out.println("-----------------------------");
        }
    }

    private static void displayAllClaims() {
        if (claims.isEmpty()) {
            System.out.println("No claims available.");
            return;
        }
        
        System.out.println("\nAll Claims:");
        for (Claim claim : claims) {
            claim.display();
            System.out.println("-----------------------------");
        }
    }
}
