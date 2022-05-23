////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.time.LocalTime;
import java.util.List;

import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.User;



public class Order {
        
    public static LocalTime startGiftTime = LocalTime.of(18,00,00);
    public static LocalTime endGiftTime = LocalTime.of(19,00,00);

    private LocalTime orderTime;
    private User user;
    private List<EItem> itemList;
    private double price;

    public Order(LocalTime orderTime, User user,
    List<EItem> itemList, double price) {

        if(orderTime == null) {
            throw new IllegalArgumentException
            ("OrderTime non puo' essere nullo");
        }

        if(user == null) {
            throw new IllegalArgumentException("User non puo' essere nullo");
        }

        if(itemList == null) {
            throw new IllegalArgumentException
            ("ItemList non puo' essere nullo");
        }
        this.orderTime = orderTime;
        this.user = user;
        this.itemList = itemList;
        this.price = price;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public User getUser() {
        return user;
    }

}