import java.util.*;
class Payment {
    private String paymentId;
    private String paymentMethod;
    private double amountPaid;
    private Date transactionDate;
    private boolean isSuccessful;

    public Payment(String paymentId, String paymentMethod, double amountPaid, Date transactionDate) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
        this.transactionDate = transactionDate;
        this.isSuccessful = false;
    }

    public boolean processPayment() {
        // Simulate payment processing
        if (validatePayment()) {
            this.isSuccessful = true;
            return true;
        }
        return false;
    }

    private boolean validatePayment() {
        if (amountPaid <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        
        if (!paymentMethod.equals("Credit Card") && 
            !paymentMethod.equals("PayPal") && 
            !paymentMethod.equals("Bank Transfer")) {
            System.out.println("Invalid payment method.");
            return false;
        }
        
        return true;
    }

    // Getters
    public String getPaymentId() { return paymentId; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getAmountPaid() { return amountPaid; }
    public Date getTransactionDate() { return transactionDate; }
    public boolean isSuccessful() { return isSuccessful; }
}
