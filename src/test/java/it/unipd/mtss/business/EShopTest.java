////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EShopTest {
    private EShop bill = new EShop();
    private final User userMaggiorenne = new User("Lorenzo", "Onelia", "3414521524", LocalDate.of(2000, 4, 1));
    private final User userMinorenne = new User("Francesco", "Bacchin", "3259992412", LocalDate.of(2005, 2, 26));
    private final User userMinorenne2 = new User("Gianni", "Morandi", "3259992412", LocalDate.of(2005, 2, 26));
    private final User userMinorenne3 = new User("Adriano", "Meis", "3259992412", LocalDate.of(2005, 2, 26));

    @Before
    public void before() {
        bill = new EShop();
    }

    @Test
    public void testGetOrderPrice() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem(EItemType.PROCESSOR, "1", 10.00));
        items.add(new EItem(EItemType.MOUSE, "2", 20.00));
        items.add(new EItem(EItemType.KEYBOARD, "3", 20.00));
        items.add(new EItem(EItemType.KEYBOARD, "4", 20.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "5", 20.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 90.00;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCost() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem(EItemType.PROCESSOR, "1", -100.00));
        items.add(new EItem(EItemType.MOUSE, "2", 20.00));
        items.add(new EItem(EItemType.KEYBOARD, "3", 20.00));
        items.add(new EItem(EItemType.KEYBOARD, "4", 20.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "5", 20.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);
 
        // Assert
        assert total == -20.00;
    }   


    @Test(expected = BillException.class)
    public void testEmptyOrder() throws BillException {
        // Arrange
        List<EItem> itemList = new LinkedList<>();

        // Act
        bill.getOrderPrice(itemList, userMaggiorenne);

        // Assert
        fail();
    }

    @Test
    public void testMoreThanFiveProcessorDiscountOrder() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem(EItemType.PROCESSOR, "intel i2", 20.00));
        items.add(new EItem(EItemType.PROCESSOR, "intel i3", 10.00));
        items.add(new EItem(EItemType.PROCESSOR, "intel i4", 30.00));
        items.add(new EItem(EItemType.PROCESSOR, "intel i5", 40.00));
        items.add(new EItem(EItemType.PROCESSOR, "intel i6", 50.00));
        items.add(new EItem(EItemType.PROCESSOR, "intel i7", 60.00));
        items.add(new EItem( EItemType.MOUSE, "mouse 1", 6.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);
        // Assert
        assert total == 216.00 - 5.00;
    }

    @Test
    public void testMoreThanFiveEqualProcessorDiscountOrder() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            items.add(new EItem(EItemType.PROCESSOR,"processor", 100.00));
        }

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 800.00 - 50.00;
    }

}