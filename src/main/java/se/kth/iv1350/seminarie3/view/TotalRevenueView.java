package se.kth.iv1350.seminarie3.model;

/**
* This class shows the total revenue in the console after each completed sale.
* It observes sales and prints the updated total revenue to the screen.
*/
public class TotalRevenueView implements  RevenueObserver {

    private double totbalance = 0.0;

    /**
    * Called when a sale is completed.
    * Adds the sale's total price to the running total and prints it.
    *
    * @param saleDTO The completed sale's data.
    */
    @Override
    public void saleCompleted(SaleDTO saleDTO){

        totbalance += saleDTO.getTotalPrice();
        System.out.println("The Total revenue for the sale this far is" + totbalance);
    }
    
}
