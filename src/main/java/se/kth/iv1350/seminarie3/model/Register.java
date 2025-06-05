package se.kth.iv1350.seminarie3.model;

/**
* This class represents a cash register that keeps track of money paid by customers.
 */
public class Register {

    private double balance;

    /**
    * Creates a new register with a starting balance of 0.
    */
    public Register (){
        this.balance = 0;
    
    }

    /**
    * Adds the paid amount to the register’s balance.
    *
    * @param paidAmount The amount of money the customer paid.
    */
    public void amountPaidAddedToRegister(double paidAmount){

        balance += paidAmount;

    }
    
}

