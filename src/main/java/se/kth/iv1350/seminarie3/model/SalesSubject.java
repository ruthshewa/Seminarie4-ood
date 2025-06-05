package se.kth.iv1350.seminarie3.model;

import java.util.ArrayList;
import java.util.List;

/**
* This class keeps track of the current sale and notifies all registered observers when the sale is complete.
*/
public class SalesSubject {
    private Sale currentSale;
    private List<RevenueObserver> observers;

    /**
    * Creates a new SalesSubject with no observers and no active sale.
    */
    public SalesSubject() {
        this.observers = new ArrayList<>();
    }

    /**
    * Sets the sale that is currently in progress.
    *
    * @param sale The sale to set as current.
    */
    public void setCurrentSale(Sale sale) {
        this.currentSale = sale;

    }

    /**
    * Adds an observer that will be notified when a sale is complete.
    *
    * @param observer The observer to add.
    */
    public void addObserver(RevenueObserver observer) {
        observers.add(observer);
    }

    /**
    * Ends the current sale and notifies all registered observers.
    * If there is no sale set, nothing happens.
    */
    public void completeSale() {
    

        if (currentSale == null) {
            return;
        }

        currentSale.endSale();
        SaleDTO dto = currentSale.toDTO();
        notifyObservers(dto);
    }

    /**
    * Notifies all observers that the sale is complete.
    *
    * @param saleDTO The data of the completed sale.
    */
    private void notifyObservers(SaleDTO saleDTO) {
        for (RevenueObserver o : observers) {
            o.saleCompleted(saleDTO);
        }
    }
}
