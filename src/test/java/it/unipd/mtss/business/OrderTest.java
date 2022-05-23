////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import it.unipd.mtss.business.Order;
import it.unipd.mtss.model.User;
import it.unipd.mtss.model.EItem;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;


public class OrderTest {
    private Order genericOrder;
    private final User user = new User("Lorenzo", "Bacchin", "3463358550", LocalDate.now());

    @Before
    public void setup() {
        genericOrder = new Order(
                LocalTime.NOON,
                user,
                new ArrayList<EItem>(),
                0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithOrderTimeNull() {
        new Order(null,
                user,
                new ArrayList<EItem>(),
                0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithUserNullU() {
        new Order(LocalTime.NOON,
                null,
                new ArrayList<EItem>(),
                0);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithOrderListNull() {
        new Order(LocalTime.NOON,
                user,
                null,
                0);
    }

    @Test
    public void testGetterUser() {
        assert genericOrder.getUser() == user;
    }

    @Test
    public void testOrderTimeGetter() {
        assert genericOrder.getOrderTime() == LocalTime.NOON;
    }




}