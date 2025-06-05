package se.kth.iv1350.seminarie3.view;
import se.kth.iv1350.seminarie3.controller.Controller;
import se.kth.iv1350.seminarie3.model.SaleDTO;
import se.kth.iv1350.seminarie3.model.Payment;
import se.kth.iv1350.seminarie3.model.ItemDTO;
import se.kth.iv1350.seminarie3.model.InvalidIdentifierException;

/**
* This class handles the user interface for running a sale.
* It shows messages and receipt details to the user.
*/
public class View {
    private Controller controller;

    /**
    * Creates a new view that talks to the controller.
    *
    * @param controller The controller that handles the sale logic.
    */
    public View(Controller controller) {
        this.controller = controller;
    }
    
    /**
    * Prints a receipt for the current sale, showing item details, VAT, total, and change.
    */
    public void printReceipt() {
        System.out.println("------------------ Begin receipt------------------");
        System.out.println("Time of Sale:"  + controller.getSaleDTO().getStartedAt());
        System.out.println();
    
        for (ItemDTO item : controller.getSaleDTO().getSaleList()){
            double lineTotal = item.getPrice() * item.getQuantity();
            System.out.println(item.getName() + " " + item.getQuantity() + "x" + " " + item.getPrice() +" " + lineTotal + "SEK");
        }
       
        System.out.println();
        System.out.println("Total including VAT:" + controller.getSaleDTO().getTotalPrice());
        System.out.println("VAT:" + controller.getSaleDTO().getTotalVat());
        System.out.println();
    
        System.out.println("Cash:" + controller.getPayment().getPaidAmount());
        System.out.println("Change:" + controller.getPayment().getChangeBack());
        System.out.println("------------------ End receipt-------------------");
    }

    /**
    * Adds one item to the cart and prints a confirmation message.
    *
    * @param identifier The item ID to add.
    */
    private void addItemAndPrint(String identifier) {
        controller.addItem(identifier);
        System.out.println("Item successfully added to the cart.");
    }

    /**
     * Adds multiple items of the same kind and prints item and sale info.
     * 
     * @param identifier The item ID.
     * @param quantity The number of items to add.
     */
    private void addItemAndPrint(String identifier,int quantity){

        SaleDTO sale = controller.getSaleDTO();
        ItemDTO recentleyAddedItem = sale.getSaleList().get(sale.getSaleList().size() - 1);

            System.out.println("Add "  + quantity+ " item" + (quantity > 1 ? "s" : "")+ " with item id " + recentleyAddedItem.getId() + ":");
            System.out.println("Item ID" + recentleyAddedItem.getId());
            System.out.println("Item name" + recentleyAddedItem.getName());
            System.out.println("Item price" + recentleyAddedItem.getPrice()+ "SEK");
            System.out.println("Item Vat" + recentleyAddedItem.getVatRate());
            System.out.println("Item description" + recentleyAddedItem.getDescription());

            System.out.println("Total cost" + sale.getRunningTotal() + "SEK");
            System.out.println("Total VAT" + sale.getTotalVat() + "SEK");
            System.out.println();

    }

    /**
    * Runs a sample sale, including adding items, ending the sale, and printing a receipt.
    */
    public void runSale (){

        
        

        controller.startSale();
    
        addItemAndPrint("9876765443");// Milk
        //controller.addItem("FORCE_FAIL");
        //addItemAndPrint("98");//error
        addItemAndPrint("0737229360");// Soda
        addItemAndPrint("9876765443");//milkS
        boolean item = controller.addMultipuleItems("4", 5); //appelJuice 0733823065
        System.out.println(item ? "Multiple items successfully added to the cart." : "Item does not exist");
        addItemAndPrint("0737229360");//soda
        //controller.addItem("FORCE_FAIL");

        controller.endSale("12345");// MemberID
        controller.endAndCompleteSale();

        controller.cashPayment(300.0);

        printReceipt();

       


        

       
        
    }




    
}
