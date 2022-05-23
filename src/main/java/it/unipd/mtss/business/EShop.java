////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;


import java.util.ArrayList;
import java.util.List;



public class EShop implements Bill {
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
    throws BillException {

        int totalItems = itemsOrdered.size();
        double total = 0;
        for (EItem eItem : itemsOrdered) {
            total += eItem.getPrice();
        }

         //se non c'è nessun item
        if(totalItems == 0) {
            throw new BillException("Nessun item nell'ordine");
        }

        double processorDiscount = getProcessorDiscount(itemsOrdered);

        total = total + processorDiscount;

        return total;
    }

    //Se vengono ordinati più di 5 Processori viene fatto uno sconto del 
    //50% sul prezzo del Processori meno caro;
    private double getProcessorDiscount(List<EItem> itemsOrdered) {
        int processorNumber = 0 ;
        double minPrice = 0;
        for (EItem eItem : itemsOrdered) {
            if (eItem.getEItemType() == EItemType.PROCESSOR){
                processorNumber++;
            }

            if(processorNumber > 5){
                EItem min = itemsOrdered.get(0);
                minPrice = itemsOrdered.get(0).getPrice();
                for (EItem e : itemsOrdered) {
                    if (e.getEItemType() == EItemType.PROCESSOR){
                         if (e.getPrice() < min.getPrice()){
                            min = e;
                            minPrice = e.getPrice();
                        }
                    }
                }
            }
        }
        if(minPrice==0){
            return 0;
        }
        else{
            return (-minPrice + (minPrice/2));
        }
    }


}