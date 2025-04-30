import java.time.LocalDate;
import java.util.Scanner;
abstract class InsurancePolicy {
    protected String policyId;
    protected Vehicle vehicle;
    protected Person policyHolder;
    protected double coverageAmount;
    protected double premiumAmount;
    protected LocalDate policyStartDate;
    protected LocalDate policyEndDate;

    public InsurancePolicy(String policyId, Vehicle vehicle, Person policyHolder, 
                          double coverageAmount, LocalDate policyStartDate, LocalDate policyEndDate) {
        this.policyId = policyId;
        this.vehicle = vehicle;
        this.policyHolder = policyHolder;
        this.coverageAmount = coverageAmount;
        this.policyStartDate = policyStartDate;
        this.policyEndDate = policyEndDate;
    }

    public abstract void calculatePremium();
    public abstract boolean processClaim(double claimAmount);
    public abstract String generatePolicyReport();
    public abstract boolean validatePolicy();

    // Getters
    public String getPolicyId() { return policyId; }
    public Vehicle getVehicle() { return vehicle; }
    public Person getPolicyHolder() { return policyHolder; }
    public double getCoverageAmount() { return coverageAmount; }
    public double getPremiumAmount() { return premiumAmount; }
    public LocalDate getPolicyStartDate() { return policyStartDate; }
    public LocalDate getPolicyEndDate() { return policyEndDate; }

    // Display basic policy info
    public void display() {
        System.out.println("Policy ID: " + policyId);
        System.out.println("Policy Holder: " + policyHolder.getFullName());
        System.out.println("Vehicle: " + vehicle.getVehicleMake() + " " + vehicle.getVehicleModel() + " (" + vehicle.getVehicleYear() + ")");
        System.out.println("Coverage Amount: $" + coverageAmount);
        System.out.println("Premium Amount: $" + premiumAmount);
        System.out.println("Policy Period: " + policyStartDate + " to " + policyEndDate);
    }
}

class ComprehensivePolicy extends InsurancePolicy {
    public ComprehensivePolicy(String policyId, Vehicle vehicle, Person policyHolder, 
                              double coverageAmount, LocalDate policyStartDate, LocalDate policyEndDate) {
        super(policyId, vehicle, policyHolder, coverageAmount, policyStartDate, policyEndDate);
    }

    @Override
    public void calculatePremium() {
        int vehicleAge = LocalDate.now().getYear() - vehicle.getVehicleYear();
        double basePremium = coverageAmount * 0.05; // 5% of coverage amount
        premiumAmount = basePremium * (1 + (vehicleAge * 0.02)); // 2% increase per year of age
    }

    @Override
    public boolean processClaim(double claimAmount) {
        if (claimAmount > coverageAmount) {
            System.out.println("Claim amount exceeds coverage limit.");
            return false;
        }
        System.out.println("Comprehensive claim processed for $" + claimAmount);
        return true;
    }

    @Override
    public String generatePolicyReport() {
        return "Comprehensive Policy Report:\n" +
               "Covers both damage and theft\n" +
               "Vehicle: " + vehicle.getVehicleMake() + " " + vehicle.getVehicleModel() + "\n" +
               "Premium: $" + premiumAmount + "\n" +
               "Coverage: $" + coverageAmount;
    }

    @Override
    public boolean validatePolicy() {
        if (vehicle.getVehicleYear() < 1980) {
            System.out.println("Vehicle too old for comprehensive coverage.");
            return false;
        }
        return true;
    }
}

class ThirdPartyPolicy extends InsurancePolicy {
    private boolean extendedCoverage;

    public ThirdPartyPolicy(String policyId, Vehicle vehicle, Person policyHolder, 
                          double coverageAmount, LocalDate policyStartDate, LocalDate policyEndDate, 
                          boolean extendedCoverage) {
        super(policyId, vehicle, policyHolder, coverageAmount, policyStartDate, policyEndDate);
        this.extendedCoverage = extendedCoverage;
    }

    @Override
    public void calculatePremium() {
        // Premium based on engine capacity (simplified)
        double engineFactor = vehicle.getVehicleType().equals("SUV") ? 1.2 : 1.0;
        premiumAmount = coverageAmount * 0.03 * engineFactor; // 3% of coverage amount
        
        if (extendedCoverage) {
            premiumAmount *= 1.15; // 15% increase for extended coverage
        }
    }

    @Override
    public boolean processClaim(double claimAmount) {
        if (claimAmount > coverageAmount) {
            System.out.println("Claim amount exceeds coverage limit.");
            return false;
        }
        System.out.println("Third party claim processed for $" + claimAmount);
        return true;
    }

    @Override
    public String generatePolicyReport() {
        return "Third Party Policy Report:\n" +
               "Covers third-party liability only\n" +
               "Extended Coverage: " + (extendedCoverage ? "Yes" : "No") + "\n" +
               "Premium: $" + premiumAmount + "\n" +
               "Coverage: $" + coverageAmount;
    }

    @Override
    public boolean validatePolicy() {
        // Basic validation that vehicle exists
        return vehicle != null;
    }
}

class CollisionPolicy extends InsurancePolicy {
    private boolean safeDriverDiscount;

    public CollisionPolicy(String policyId, Vehicle vehicle, Person policyHolder, 
                          double coverageAmount, LocalDate policyStartDate, LocalDate policyEndDate, 
                          boolean safeDriverDiscount) {
        super(policyId, vehicle, policyHolder, coverageAmount, policyStartDate, policyEndDate);
        this.safeDriverDiscount = safeDriverDiscount;
    }

    @Override
    public void calculatePremium() {
        premiumAmount = coverageAmount * 0.04; // 4% of coverage amount
        
        if (safeDriverDiscount) {
            premiumAmount *= 0.9; // 10% discount for safe drivers
        }
    }

    @Override
    public boolean processClaim(double claimAmount) {
        if (claimAmount > coverageAmount) {
            System.out.println("Claim amount exceeds coverage limit.");
            return false;
        }
        System.out.println("Collision claim processed for $" + claimAmount);
        return true;
    }

    @Override
    public String generatePolicyReport() {
        return "Collision Policy Report:\n" +
               "Covers collision damage only\n" +
               "Safe Driver Discount: " + (safeDriverDiscount ? "Yes" : "No") + "\n" +
               "Premium: $" + premiumAmount + "\n" +
               "Coverage: $" + coverageAmount;
    }

    @Override
    public boolean validatePolicy() {
        // Check if vehicle safety check was done (simplified)
        System.out.println("Has the vehicle passed safety check? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}

class LiabilityPolicy extends InsurancePolicy {
    private boolean extendedDisabilityCoverage;

    public LiabilityPolicy(String policyId, Vehicle vehicle, Person policyHolder, 
                         double coverageAmount, LocalDate policyStartDate, LocalDate policyEndDate, 
                         boolean extendedDisabilityCoverage) {
        super(policyId, vehicle, policyHolder, coverageAmount, policyStartDate, policyEndDate);
        this.extendedDisabilityCoverage = extendedDisabilityCoverage;
    }

    @Override
    public void calculatePremium() {
        premiumAmount = coverageAmount * 0.025; // 2.5% of coverage amount
        
        if (extendedDisabilityCoverage) {
            premiumAmount *= 1.2; // 20% increase for extended disability coverage
        }
    }

    @Override
    public boolean processClaim(double claimAmount) {
        if (claimAmount > coverageAmount) {
            System.out.println("Claim amount exceeds coverage limit.");
            return false;
        }
        System.out.println("Liability claim processed for $" + claimAmount);
        return true;
    }

    @Override
    public String generatePolicyReport() {
        return "Liability Policy Report:\n" +
               "Covers third-party liability\n" +
               "Extended Disability Coverage: " + (extendedDisabilityCoverage ? "Yes" : "No") + "\n" +
               "Premium: $" + premiumAmount + "\n" +
               "Coverage: $" + coverageAmount;
    }

    @Override
    public boolean validatePolicy() {
        // Check if medical checkup was done (simplified)
        System.out.println("Has the policyholder completed medical checkup? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}

class RoadsideAssistancePolicy extends InsurancePolicy {
    private boolean commercialVehicle;

    public RoadsideAssistancePolicy(String policyId, Vehicle vehicle, Person policyHolder, 
                                  double coverageAmount, LocalDate policyStartDate, LocalDate policyEndDate, 
                                  boolean commercialVehicle) {
        super(policyId, vehicle, policyHolder, coverageAmount, policyStartDate, policyEndDate);
        this.commercialVehicle = commercialVehicle;
    }

    @Override
    public void calculatePremium() {
        premiumAmount = commercialVehicle ? 250 : 150; // Flat rates
    }

    @Override
    public boolean processClaim(double claimAmount) {
        if (claimAmount > coverageAmount) {
            System.out.println("Claim amount exceeds coverage limit.");
            return false;
        }
        System.out.println("Roadside assistance claim processed for $" + claimAmount);
        return true;
    }

    @Override
    public String generatePolicyReport() {
        return "Roadside Assistance Policy Report:\n" +
               "Covers emergency assistance\n" +
               "Vehicle Type: " + (commercialVehicle ? "Commercial" : "Private") + "\n" +
               "Premium: $" + premiumAmount + "\n" +
               "Coverage: $" + coverageAmount;
    }

    @Override
    public boolean validatePolicy() {
        // Check if registration and inspection are valid (simplified)
        System.out.println("Is the vehicle registration and inspection current? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}

class Vehicle {
    private String vehicleId;
    private String vehicleMake;
    private String vehicleModel;
    private int vehicleYear;
    private String vehicleType;

    public Vehicle(String vehicleId, String vehicleMake, String vehicleModel, 
                  int vehicleYear, String vehicleType) {
        this.vehicleId = vehicleId;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.vehicleType = vehicleType;
    }

    // Getters
    public String getVehicleId() { return vehicleId; }
    public String getVehicleMake() { return vehicleMake; }
    public String getVehicleModel() { return vehicleModel; }
    public int getVehicleYear() { return vehicleYear; }
    public String getVehicleType() { return vehicleType; }

    public void display() {
        System.out.println("Vehicle ID: " + vehicleId);
        System.out.println("Make/Model: " + vehicleMake + " " + vehicleModel);
        System.out.println("Year: " + vehicleYear);
        System.out.println("Type: " + vehicleType);
    }
}

class Person {
    private String personId;
    private String fullName;
    private LocalDate dob;
    private String email;
    private String phone;

    public Person(String personId, String fullName, LocalDate dob, String email, String phone) {
        this.personId = personId;
        this.fullName = fullName;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public String getPersonId() { return personId; }
    public String getFullName() { return fullName; }
    public LocalDate getDob() { return dob; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void display() {
        System.out.println("Person ID: " + personId);
        System.out.println("Name: " + fullName);
        System.out.println("DOB: " + dob);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}

class Claim {
    private String claimId;
    private double claimAmount;
    private LocalDate claimDate;
    private String claimStatus;
    private InsurancePolicy policy;

    public Claim(String claimId, double claimAmount, LocalDate claimDate, 
                String claimStatus, InsurancePolicy policy) {
        this.claimId = claimId;
        this.claimAmount = claimAmount;
        this.claimDate = claimDate;
        this.claimStatus = claimStatus;
        this.policy = policy;
    }

    // Getters
    public String getClaimId() { return claimId; }
    public double getClaimAmount() { return claimAmount; }
    public LocalDate getClaimDate() { return claimDate; }
    public String getClaimStatus() { return claimStatus; }
    public InsurancePolicy getPolicy() { return policy; }

    public void display() {
        System.out.println("Claim ID: " + claimId);
        System.out.println("Amount: $" + claimAmount);
        System.out.println("Date: " + claimDate);
        System.out.println("Status: " + claimStatus);
        System.out.println("Policy ID: " + policy.getPolicyId());
    }
}