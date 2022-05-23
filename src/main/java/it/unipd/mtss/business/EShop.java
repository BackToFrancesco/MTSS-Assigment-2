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

        // se gli item sono più di 30
        if (totalItems > 30) {
            throw new BillException("Totale di 30 elementi superato");
        }

        // se non c'è nessun item
        if (totalItems == 0) {
            throw new BillException("Nessun item nell'ordine");
        }

        double processorDiscount = getProcessorDiscount(itemsOrdered);
        double mouseDiscount = getMouseDiscount(itemsOrdered);

        double mouseOrKeyboardDiscount=getMouseOrKeyBoardDiscount(itemsOrdered);

        total = total + processorDiscount - mouseDiscount -
                mouseOrKeyboardDiscount;

        double overallDiscount = getOverallDiscount(total);
        double commission = getCommissionAmount(total);

        return total + commission - overallDiscount;
    }

    // Se vengono ordinati più di 5 Processori viene fatto uno sconto del
    // 50% sul prezzo del Processori meno caro;
    private double getProcessorDiscount(List<EItem> itemsOrdered) {
        int processorNumber = 0;
        double minPrice = 0;
        for (EItem eItem : itemsOrdered) {
            if (eItem.getEItemType() == EItemType.PROCESSOR) {
                processorNumber++;
            }

            if (processorNumber > 5) {
                EItem min = itemsOrdered.get(0);
                minPrice = itemsOrdered.get(0).getPrice();
                for (EItem e : itemsOrdered) {
                    if (e.getEItemType() == EItemType.PROCESSOR) {
                        if (e.getPrice() < min.getPrice()) {
                            min = e;
                            minPrice = e.getPrice();
                        }
                    }
                }
            }
        }
        if (minPrice == 0) {
            return 0;
        } else {
            return (-minPrice + (minPrice / 2));
        }
    }

    // Se vengono ordinati più di 10 Mouse il meno caro viene regalato
    private double getMouseDiscount(List<EItem> itemsOrdered) {
        int mouseNumber = 0;
        double minPrice = 0;
        for (EItem eItem : itemsOrdered) {
            if (eItem.getEItemType() == EItemType.MOUSE) {
                mouseNumber++;
            }

            if (mouseNumber > 10) {
                EItem min = itemsOrdered.get(0);
                minPrice = itemsOrdered.get(0).getPrice();
                for (EItem e : itemsOrdered) {
                    if (e.getEItemType() == EItemType.MOUSE) {
                        if (e.getPrice() < min.getPrice()) {
                            min = e;
                            minPrice = e.getPrice();
                        }
                    }
                }
            }
        }

        if (minPrice == 0) {
            return 0;
        } else {
            return (minPrice);
        }
    }

    // Se vengono ordinati lo stesso numero di Mouse e Tastier
    // viene regalato l’articolo meno caro
    private double getMouseOrKeyBoardDiscount(List<EItem> itemsOrdered) {
        int keyboardNumber = 0;
        double minPrice = 0;
        for (EItem eItem : itemsOrdered) {
            if (eItem.getEItemType() == EItemType.KEYBOARD) {
                keyboardNumber++;
            }
        }

        int mouseNumber = 0;
        for (EItem eItem2 : itemsOrdered) {
            if (eItem2.getEItemType() == EItemType.MOUSE) {
                mouseNumber++;
            }
        }
        if (keyboardNumber == mouseNumber && mouseNumber > 0) {
            EItem min = itemsOrdered.get(0);
            minPrice = itemsOrdered.get(0).getPrice();
            for (EItem e : itemsOrdered) {
                if (e.getPrice() < min.getPrice()) {
                    min = e;
                    minPrice = e.getPrice();
                }
            }
        }
        if (minPrice == 0) {
            return 0;
        } else {
            return (minPrice);
        }
    }

    // Se l’importo totale degli articoli supera
    // i 1000 euro viene fatto uno sconto del 10% sul totale;
    private double getOverallDiscount(double totalPrice) {
        if (totalPrice > 1000) {
            return totalPrice * 0.1;
        } else {
            return 0;
        }
    }

    // Se l’importo totale è inferiore a 10 €
    // viene aggiunta una commissione di 2 €;
    private double getCommissionAmount(double totalPrice) {
        if (totalPrice < 10) {
            return 2;
        } else {
            return 0;
        }
    }

    // Prevedere la possibilità di regalare,
    // in modo casuale, 10 ordini effettuati dalle 18:00
    // alle 19:00 da utenti minorenni differenti.
    public List<Order> getUnder18FreeOrders(List<Order> itemOrders) {

        List<User> userList = new ArrayList<>();
        List<Order> freeOrders = new ArrayList<>();

        ArrayList<Order> orders = new ArrayList<>();
        for (Order itemOrder : itemOrders) {
            if (itemOrder.getUser().getAge() < 18
                    && itemOrder.getOrderTime().isAfter(itemOrder.startGiftTime)
                    && itemOrder.getOrderTime().isBefore(itemOrder.endGiftTime))
            {
                orders.add(itemOrder);
            }
        }
        boolean isIn = false;
        while (freeOrders.size() < 10 && orders.size() > 0) {
            isIn = false;
            int i = (int) (Math.random() * orders.size());

            if (userList.contains(orders.get(i).getUser())) {
                isIn = true;
                orders.remove(i);
            }

            if (isIn == false) {
                userList.add(orders.get(i).getUser());
                freeOrders.add(orders.get(i));
                orders.remove(i);
            }
        }
        return freeOrders;
    }

}