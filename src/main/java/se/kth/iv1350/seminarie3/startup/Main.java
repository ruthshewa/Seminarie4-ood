package se.kth.iv1350.seminarie3.startup;
import se.kth.iv1350.seminarie3.controller.Controller;
import se.kth.iv1350.seminarie3.model.RevenueObserver;
import se.kth.iv1350.seminarie3.model.TotalRevenueFileOutput;
import se.kth.iv1350.seminarie3.model.TotalRevenueView;
import se.kth.iv1350.seminarie3.view.View;



public class Main {


    public static void main(String[] args) {
        Controller controller = new Controller();
        TotalRevenueView consoleObs = new TotalRevenueView();
        TotalRevenueFileOutput fileObs = new TotalRevenueFileOutput();
         controller.addObserver(consoleObs);
        

        controller.addObserver(fileObs);
        
        View view = new View(controller);
        view.runSale();
    }

        

   
}
    

