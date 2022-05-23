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

        return total;
    }


}