package se.kth.iv1350.seminarie3.integration;

import se.kth.iv1350.seminarie3.model.Sale;

/**
 * Represents an external accounting system that receives sale information.
 */
public class ExternalAccountingSystem {

    /**
     * Sends the sale information to the external accounting system.
     * 
     * @param sale The sale that will be recorded.
     */
    public void recordSaleInAccountingSystem (Sale sale){

        System.out.println(" Sale information has been succesfuly sent to the Accounting System");


    }
    
}