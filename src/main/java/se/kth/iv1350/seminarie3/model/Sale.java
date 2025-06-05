package se.kth.iv1350.seminarie3.model;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import se.kth.iv1350.seminarie3.model.Item;
import se.kth.iv1350.seminarie3.integration.ExternalInventorySystem;
import se.kth.iv1350.seminarie3.integration.DatabaseFailureException;
import se.kth.iv1350.seminarie3.integration.DiscountDatabase;
import se.kth.iv1350.seminarie3.model.CustomerIdentification;
import se.kth.iv1350.seminarie3.model.SaleDTO;
import se.kth.iv1350.seminarie3.model.ItemDTO;

/**
 * This class represents a sale in a store.
 * It keeps track of the items being sold, the total price, VAT, and sale status.
 */
public class Sale{
    private List<Item> list;
    private double runningTotal;
    private double totalPrice;
    private LocalDateTime startedAt;
    private double totalVat;
    private SaleStatus status;
    private ExternalInventorySystem externalInventorySystem;
    private DiscountDatabase discountDatabase;
    
    /**
     * Describes the current status of the sale.
     */
    public enum SaleStatus {
        ONGOING,
        COMPLETED,
        CANCELLED,
        REFUNDED
    }
    
    /**
    * Starts a new sale. Sets the time and starts with an empty item list.
    */
    public Sale() {
        this.list = new ArrayList<>();
        this.runningTotal = 0;
        this.totalPrice = 0;
        this.startedAt = LocalDateTime.now();
        this.totalVat = 0;
        this.status = Sale.SaleStatus.ONGOING;
        this.externalInventorySystem = new ExternalInventorySystem();
        this.discountDatabase = new DiscountDatabase();
    }

    /**
    * Adds an item to the sale using its ID.
    * If the item already exists in the sale, it increases the quantity.
    *
    * @param targetId The ID of the item to add.
    * @throws InvalidIdentifierException If the ID is not found in the inventory.
    * @throws DatabaseFailureException If there is a system error during the lookup.
    */
    public void addItemToCart(String targetId) throws InvalidIdentifierException,DatabaseFailureException {
        Item targetItem = externalInventorySystem.findItemUsingId(targetId);
        
        if (targetItem == null) {
            throw new InvalidIdentifierException("The following itemID is invalid" + targetId);
        }
        if(targetItem.getId().equals("1")){
            throw new DatabaseFailureException("A datbase failure has occured");
        }
    
        if (itemAlreadyInSaleList(targetItem)) {
            increaseItemQuantity(targetItem);
        } else {
            addNewItemToCart(targetItem.getId());
        }
    
       
    }

    

    /**
    * Checks if the item is already in the sale list.
    *
    * @param targetItem The item to check.
    * @return true if the item is already in the list, false if not.
    */
    private boolean itemAlreadyInSaleList(Item targetItem) {
        for (Item item : list) {
            if (item.getId().equals(targetItem.getId())) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
    * Increases the quantity of an item that is already in the sale list by 1.
    *
    * @param item The item to increase the quantity of.
    */
    public void increaseItemQuantity(Item item) {
        for (Item good : list) {
            if (good.getId().equals(item.getId())) {
                good.setQuantity(good.getQuantity() + 1);
                return;
            }
        }
    }


    /**
    * Adds several of the same item to the sale at once.
    *
    * @param targetId The ID of the item.
    * @param quantity How many of the item to add.
    * @return true if the items were added successfully.
    * @throws InvalidIdentifierException If the item ID is not found.
    * @throws DatabaseFailureException If there is a system or database error.
    */
    public boolean addMultipleItemsAtOnce(String targetId, int quantity)
        throws InvalidIdentifierException, DatabaseFailureException {
    
        Item mulItemDTO = externalInventorySystem.findItemUsingId(targetId);

    
        if (mulItemDTO == null) {
            throw new InvalidIdentifierException("The itemID: " + targetId + " is INVALID RUTH");
        }

   
        Item newItem = new Item(
            mulItemDTO.getId(),
            mulItemDTO.getName(),
            mulItemDTO.getVatRate(),
            mulItemDTO.getPrice(),
            mulItemDTO.getUnitOfMeasure(),
            mulItemDTO.getDescription(),
            quantity
        );
        list.add(newItem);
        return true;
    }

    /**
    * Adds one item to the sale using its ID.
    *
    * @param targetId The ID of the item to add.
    * @throws InvalidIdentifierException If the item ID is not found.
    * @throws DatabaseFailureException If there is a system or database error.
    */ 
    private void addNewItemToCart(String targetId)
        throws InvalidIdentifierException, DatabaseFailureException {
        
        Item original = externalInventorySystem.findItemUsingId(targetId);

        
        if (original == null) {
            throw new InvalidIdentifierException("The itemID: " + targetId + " is INVALID RUTH");
        }

    
        Item newItem = new Item(
            original.getId(),
            original.getName(),
            original.getVatRate(),
            original.getPrice(),
            original.getUnitOfMeasure(),
            original.getDescription(),
            1
        );
        list.add(newItem);
    }
    
    /**
    * Calculates and returns the current total amount of the sale including VAT,
    * based only on item price and VAT.
    *
    * @return The current total including VAT.
    */
    public double getRunningTotal() {
        double total = 0;
        for (Item item : list) {
            total += item.getPrice() + (item.getPrice() * item.getVatRate() / 100.0);
        }
        return total;
    }

    /**
    * Returns the time when the sale was started.
    *
    * @return The starting time of the sale.
    */
    public LocalDateTime getStartedAt(){
        return startedAt;
    }

    /**
    * Returns the list of items currently in the sale.
    *
    * @return The list of added items.
    */
    public List<Item> getSaleList(){
        return list;
    }

    /**
    * Calculates the final total price of the sale, including VAT for each item and quantity.
    *
    * @return The total price including VAT.
    */
    public double calculateTotalPrice() {
        totalPrice = 0;
        totalVat = 0;

        for (Item item : list) {
            double vatAmount = item.getPrice() * item.getQuantity()  *(item.getVatRate() / 100.0);
            totalVat += vatAmount;
            totalPrice += (item.getPrice()  * item.getQuantity()) + vatAmount ;
        }
        return totalPrice; 
    }

    /**
    * Returns the total VAT calculated during the sale.
    *
    * @return The total VAT.
    */
    public double getTotalVat(){
        return totalVat;
    }

    /**
    * Marks the sale as completed and calculates the total price.
    */
    public void endSale() {
        this.status = SaleStatus.COMPLETED;
        calculateTotalPrice();
    }

    /**
    * Applies a percentage based discount based on the customer's member ID.
    * If a discount is found, it is subtracted from the total price.
    *
    * @param memberId The customer's membership ID.
    * @return The total price after discount.
    */
    public double applyDiscountPercentageGivenCustomerId(String memberId){

        int discount = discountDatabase.findDiscountUsingId(memberId);

        if(discount > 0){
            double discountInKronor = totalPrice * (discount/100.0);
            totalPrice -= discountInKronor;

        }
        return totalPrice;
    }

    /**
    * Checks if a discount should be given based on the items in the sale.
    * A discount of 25 is given if 3 or more Soda items are bought.
    *
    * @return The discount amount.
    */
    public double DiscountGivenTheSaleList(){
        double discount = 0;

        for(Item item : list){

            if(item.getName().equals("Soda") && item.getQuantity() >= 3 ){
                discount = 25.0;
            }
        }

        return discount;
    }

    /**
    * Applies a discount based on the items in the sale.
    * Currently gives 25 SEK off if 3 or more sodas are bought.
    */
    public void applyDiscountGivenTheSaleList(){

        double discount = DiscountGivenTheSaleList();
        totalPrice = totalPrice - discount;
    }

    /**
    * Checks if a discount should be given based on the total cost.
    * A discount of 50 is given if the total price is 100 or more.
    *
    * @return The discount amount.
    */
    public double DiscountGivenTheTotalCost(){
        double discount = 0;

        if(totalPrice >= 100){
            discount = 50.0;
        }
        return discount;
    }

    /**
    * Applies a discount if the total cost is 100 or more.
    * Subtracts 50 from the total price.
    */
    public void applyDiscountGivenTheTotalCost(){

        double discount = DiscountGivenTheTotalCost();
        totalPrice = totalPrice - discount;

    } 

    /**
    * Converts the sale into a SaleDTO object, which contains all the sale data.
    *
    * @return A SaleDTO with item list, total price, total VAT, running total, and start time.
    */
    public SaleDTO toDTO() {
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : list) {
            itemDTOs.add(item.toDTO());
        }
        return new SaleDTO(
            itemDTOs,   
            calculateTotalPrice(),
            this.totalVat,
            getRunningTotal(),
            this.startedAt
        );
    }

}
 