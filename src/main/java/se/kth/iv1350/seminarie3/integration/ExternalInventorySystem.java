package se.kth.iv1350.seminarie3.integration;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.seminarie3.model.InvalidIdentifierException;
import se.kth.iv1350.seminarie3.model.Item;
import se.kth.iv1350.seminarie3.model.Sale;

/**
 * Represents an external inventory system that stores item data and updates inventory after a sale.
 */
public class ExternalInventorySystem {

    private List<Item> items = new ArrayList<>();

    private double milkPrice = 10.0;
    private double proteinShakePrice = 26.0;
    private double sodaPrice = 14.0 ;
    private double appleJuicePrice = 20.0;

    private int milkVat = 2;
    private int proteinShakeVat = 6;
    private int sodaVat = 5;
    private int appleJuiceVat = 8;

    private String milkDescription = "Fresh and  natural";
    private String proteinShakeDescription = "Fuel";
    private String sodaDescription = " mmm, yummy";
    private String  appleJuiceDescription = "Tasty";

    /**
     * Creates the inventory system and adds some items to it.
     */
    public ExternalInventorySystem() {
        items.add(new Item("9876765443", "Milk", milkVat, milkPrice, "liter", milkDescription,0));
        items.add(new Item("0752535696", "Protein Shake",proteinShakeVat, proteinShakePrice, "mililiter", proteinShakeDescription,0));
        items.add(new Item("0737229360", "Soda", sodaVat, sodaPrice, "centiliter", sodaDescription,0));
        items.add(new Item("0733823065", "Apple Juice", appleJuiceVat, appleJuicePrice, "liter", appleJuiceDescription,0));    
    }

    /**
     * Returns all the items stored in the inventory.
     * 
     * @return A list of all items. 
     */
    public List<Item> getAllItems() {
        return items;
    }

    /**
    * Finds and returns an item based on its ID.
    *
    * @param id The ID of the item to find.
    * @return The item that matches the ID.
    * @throws InvalidIdentifierException If no item with the given ID is found.
    * @throws DatabaseFailureException If the ID "FORCE_FAIL" is used to simulate a database error.
    */
    public Item findItemUsingId(String id) throws InvalidIdentifierException {
    
        if ("invalid".equals(id)) {
            throw new DatabaseFailureException("Forced exception from ExternalInventorySystem");
        }

        
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }


        throw new InvalidIdentifierException("The itemID: " + id + " is INVALID RUTH");
    }
 

    /**
    * Simulates sending the sale to the inventory system to update it.
    *
    * @param sale The sale that just finished.
    */
    public void updateSaleInInventorySystem (Sale sale){

        System.out.println(" The inventory has been updated given the saleinformation!");


    }
}

