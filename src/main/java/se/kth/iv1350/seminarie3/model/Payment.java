package se.kth.iv1350.seminarie3.model;

/**
 * This class handles payment details made by a customer.
 * It stores the amount paid and calculates how much change to give back.
 */
public class Payment {
    private double paidAmount;
    private double changeBack;

    /**
    * Creates a new payment.
    *
    * @param paidAmount The amount of money the customer paid.
    * @param totPrice The total price of the purchase.
    */
    public Payment(double paidAmount, double totPrice){
        this.paidAmount = paidAmount;
        this.changeBack = calculateChangeBack(totPrice);
    }

    /**
    * Calculates how much money to give back to the customer.
    *
    * @param totPrice The total price of the purchase.
    * @return The change that should be given back.
    */

    private double calculateChangeBack(double totPrice){
        return paidAmount - totPrice;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getChangeBack() {
        return changeBack;
    }
    
}

