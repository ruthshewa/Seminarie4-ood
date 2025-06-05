package se.kth.iv1350.seminarie3.integration;

import se.kth.iv1350.seminarie3.integration.DatabaseFailureException;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.seminarie3.model.CustomerIdentification;

/**
 * This class stores discount information for customers.
 * It checks if a customer has a discount based on their ID.
 */
public class DiscountDatabase {

    private List<CustomerIdentification> discountBasedOnCustomerId = new ArrayList<>();

    private String ruthMemberId = "14122024";
    private String simonMemberId = "26042025";

    private String ruthEmail = "ruthnunu3@gmail.com";
    private String simonEmail = "simontekle@gmail.com";

    private String ruthPhoneNumber = "0760523065";
    private String simonPhoneNumber = "0706652344";

    /**
     * Creates the discount database and adds some example customers with discounts.
     * 
     * @throws DatabaseFailureException If the data cannot be added.
     */
    public DiscountDatabase(){
        try {

            discountBasedOnCustomerId.add(new CustomerIdentification(ruthMemberId, ruthEmail, ruthPhoneNumber,6));
            discountBasedOnCustomerId.add(new CustomerIdentification(simonMemberId, simonEmail, simonPhoneNumber,4));


            
        }catch(Exception e){
            throw new DatabaseFailureException("Database failed");
        }
        
    }

    /**
     * Looks for a discount by customer ID.
     *
     * @param id The customer’s membership ID.
     * @return The discount percentage if found, or 0 if no discount exists.
     * @throws DatabaseFailureException If there is an error while checking the database.
     */
    public int findDiscountUsingId(String id){

        if ("FORCE_FAIL".equals(id)) {
            throw new DatabaseFailureException("Forced exception for testing");
        }
        try{
             for(CustomerIdentification customer :  discountBasedOnCustomerId){
            if(customer.getMemberId().equals(id)){
                return customer.getDiscountPercentage();

            }
        }
        return 0;

        }catch(Exception e){
            throw new DatabaseFailureException("Could not query ID:" + id + "in the DiscountDatabase");
        }
       
    }  
    
}
