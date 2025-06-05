package se.kth.iv1350.seminarie3.model;

/**
* This class observes completed sales and logs the total revenue to a file.
* It writes the updated total to a text file each time a sale is completed.
*/
public class TotalRevenueFileOutput implements RevenueObserver {
    private double totalSoFar = 0.0;
    private FileLogger logger = new FileLogger();

    /**
    * Called when a sale is finished.
    * Adds the sale's total price to the running total and writes it to a file.
    *
    * @param saleDTO The finished sale's data.
    */
    @Override
    public void saleCompleted(SaleDTO saleDTO) {
        totalSoFar += saleDTO.getTotalPrice();
        logger.log("Total revenue so far (file): " + totalSoFar);
    }
}
