package se.kth.iv1350.seminarie3.controller;
import se.kth.iv1350.seminarie3.model.Payment;
import se.kth.iv1350.seminarie3.model.Sale;
import se.kth.iv1350.seminarie3.integration.DatabaseFailureException;
import se.kth.iv1350.seminarie3.integration.DeveloperLogHandler;
import se.kth.iv1350.seminarie3.integration.DiscountDatabase;
import se.kth.iv1350.seminarie3.integration.ExternalAccountingSystem;
import se.kth.iv1350.seminarie3.integration.ExternalInventorySystem;
import se.kth.iv1350.seminarie3.integration.UserFriendlyErrorHandler;
import se.kth.iv1350.seminarie3.model.Register;
import se.kth.iv1350.seminarie3.model.RevenueObserver;
import se.kth.iv1350.seminarie3.model.SaleDTO;
import se.kth.iv1350.seminarie3.model.InvalidIdentifierException;
import se.kth.iv1350.seminarie3.model.SalesSubject;
/**
 * The Controller handles the operations between the view and the model layer.
 * It works as the main part of the program’s logic, it connects to external systems
 * and also manages the steps of a sale.
 */
public class Controller{
    private Sale sale;
    private ExternalAccountingSystem accountingSystem;
    private ExternalInventorySystem inventorySystem;
    private Payment payment;
    private Register register;
    private SalesSubject salesSubject;
    
    private UserFriendlyErrorHandler     userHandler;
    private DeveloperLogHandler          devLogger;
    private DiscountDatabase             discountDb;

   

    /**
     * Creates a new instance of Controller and initializes the external systems.
     */
    public Controller (){
        this.accountingSystem = new ExternalAccountingSystem();
        this.inventorySystem = new ExternalInventorySystem();
        this.register = new Register();
        this.salesSubject = new SalesSubject();

        try {
            
            devLogger       = new DeveloperLogHandler();
            discountDb      = new DiscountDatabase();
            userHandler     = new UserFriendlyErrorHandler();

        } catch (DatabaseFailureException e) {
            
            if (devLogger != null) {
                devLogger.logException(e);
            }
            
            throw e;
        }
    }
    /**
     * Starts a new sale session. Must be called before performing salerelated operations.
     */
    public void startSale(){
        sale = new Sale();
        salesSubject.setCurrentSale(sale);
    }

    /**
     * Adds an item to the current sale based on its identifier.
     *
     * @param itemIdentifier The identifier of the item to add.
     * @throws InvalidIdentifierException if the item ID is not found in the inventory.
     */
    public void addItem(String itemIdentifier){
        try {
             sale.addItemToCart(itemIdentifier);
        }catch(InvalidIdentifierException e){
            userHandler.showErrorMsg("No such item. Please check the ID and try again.");

        } catch (DatabaseFailureException e) {
        
        userHandler.showErrorMsg("Sorry, we can’t add that item right now. Please try again later.");
        devLogger.logException(e);
    }
    }

    /** 
     * Adds multiple items of the same kind to the current sale, with a specific quantity. 
     * 
     * @param itemId The identifier of the item to add.
     * @param itemQuantity The number of items to add.
     * @return true if items are added, false if not.
    */
    public boolean addMultipuleItems(String itemId,int itemQuantity){
      try {
        return sale.addMultipleItemsAtOnce(itemId, itemQuantity);
    } catch (InvalidIdentifierException invEx) {
        userHandler.showErrorMsg("No such item. Please check the ID and try again.");
        return false;
    } catch (DatabaseFailureException dbEx) {
        userHandler.showErrorMsg("Sorry, we can’t add that item right now. Please try again later.");
        devLogger.logException(dbEx);
        return false;
    }
    }

    /**
     * Returns the running total for the sale.
     * @return The total amount to be paid.
     */
    public double getRunningTotal() {
        return sale.getRunningTotal();
    }

    /**
     * Ends the sale, applies discounts and updates external systems. 
     * @throws DatabaseFailureException if the sale can't be processed. 
     */
    public void endSale(String memberId) {
        sale.endSale();
        sale.applyDiscountPercentageGivenCustomerId(memberId);
        sale.DiscountGivenTheTotalCost();
        sale.DiscountGivenTheSaleList();
        accountingSystem.recordSaleInAccountingSystem(sale);
        inventorySystem.updateSaleInInventorySystem(sale);
        salesSubject.setCurrentSale(sale);

     
       
    }
    /**
     * Registers a cash payment and updates the register with the paid amount.
     * 
     * @param paidAmount The amount paid by the customer.
     */
    public void cashPayment(double paidAmount){
        double totPrice = sale.calculateTotalPrice();
        payment = new Payment(paidAmount, totPrice);
        register.amountPaidAddedToRegister(payment.getPaidAmount());
        
    }

    /**
     * Retrieves information about the sale as a DTO. 
     *
     * @return A DTO representing the sale.
     */
    public SaleDTO getSaleDTO() {
        return sale.toDTO();
    }
    /**
     * Returns information from the recent payment.
     *
     * @return Information from the payment containing paid and change amounts.
     */
    public Payment getPayment (){
        return payment;
    }

    /**
     * Adds an observer that will be notified when a sale is completed.
     * 
     * @param observer The observer to add.
     */
    public void addObserver(RevenueObserver observer) {
        salesSubject.addObserver(observer);
    }

    /**
     * Marks the sale as finished and informs all observers.
     */
    public void endAndCompleteSale() {
        salesSubject.completeSale();
    }
}

