package se.kth.iv1350.seminarie3.model;


/**
 * This interface should be implemented by any class that wants to be notified
 * when a sale is completed. It is used to observe and react to revenue updates.
 */
public interface RevenueObserver {

    /**
     * Called when a sale is completed. This method gives access to the sale data.
     *
     * @param saleDTO The completed sale’s data.
     */
    void saleCompleted(SaleDTO saleDTO);
}
