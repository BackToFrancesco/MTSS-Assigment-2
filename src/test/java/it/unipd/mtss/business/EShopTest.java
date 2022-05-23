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
    public void testNotEmptyOrder() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem(EItemType.PROCESSOR, "intel", 10.00));
        items.add(new EItem(EItemType.PROCESSOR, "intel", 20.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 30.00;
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
        items.add(new EItem(EItemType.MOUSE, "mouse 1", 6.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);
        // Assert
        assert total == 216.00 - 5.00;
    }

    @Test
    public void testMoreThanFiveEqualProcessorDiscountOrder() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            items.add(new EItem(EItemType.PROCESSOR, "processor", 100.00));
        }

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 800.00 - 50.00;
    }

    @Test
    public void testMoreThanTenMouseOrder() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        items.add(new EItem(EItemType.MOUSE, "1", 5.00));
        items.add(new EItem(EItemType.MOUSE, "2", 10.00));
        items.add(new EItem(EItemType.MOUSE, "3", 15.00));
        items.add(new EItem(EItemType.MOUSE, "4", 20.00));
        items.add(new EItem(EItemType.MOUSE, "5", 25.00));
        items.add(new EItem(EItemType.MOUSE, "6", 30.00));
        items.add(new EItem(EItemType.MOUSE, "7", 35.00));
        items.add(new EItem(EItemType.MOUSE, "8", 40.00));
        items.add(new EItem(EItemType.MOUSE, "9", 45.00));
        items.add(new EItem(EItemType.MOUSE, "10", 50.00));
        items.add(new EItem(EItemType.MOUSE, "11", 55.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 325.00;
    }

    @Test
    public void testLessThanTenMouseOrder() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem(EItemType.MOUSE, "Mouse 1", 15.00));
        items.add(new EItem(EItemType.MOUSE, "Mouse 2", 16.00));
        items.add(new EItem(EItemType.MOUSE, "Mouse 3", 10.00));
        items.add(new EItem(EItemType.MOUSE, "Mouse 4", 10.00));
        items.add(new EItem(EItemType.MOUSE, "Mouse 5", 5.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "Motherboard 1", 250.00));
        items.add(new EItem(EItemType.PROCESSOR, "Processor 1", 110.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 416.00;
    }

    @Test
    public void testMoreThanFiveProcessorAndMoreThan10Mouse() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            items.add(new EItem(EItemType.PROCESSOR, "processor", 100.00));
        }
        for (int i = 0; i < 11; i++) {
            items.add(new EItem(EItemType.MOUSE, "mouse", 10.00));
        }

        items.add(new EItem(EItemType.MOTHERBOARD, "Motherboard 1", 250.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 960.00 - 50.00 - 10.00;
    }

    @Test
    public void testMouseDiscountAndProcessorAndMouseOrKeayboardDiscount() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            items.add(new EItem(EItemType.PROCESSOR, "processor", 50.00));
        }
        for (int i = 0; i < 11; i++) {
            items.add(new EItem(EItemType.MOUSE, "mouse", 10.00));
        }

        for (int i = 0; i < 11; i++) {
            items.add(new EItem(EItemType.KEYBOARD, "keyboard", 20.00));
        }

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == (630.00 - 25.00 - 10.00 - 10.00);
    }

    @Test
    public void testNumberMouseEqualsKeyboardiscount() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        items.add(new EItem(EItemType.MOUSE, "1", 10.00));
        items.add(new EItem(EItemType.MOUSE, "2", 20.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "0", 20.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "0", 20.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "0", 20.00));
        items.add(new EItem(EItemType.KEYBOARD, "1", 30.00));
        items.add(new EItem(EItemType.KEYBOARD, "2", 5.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 125.00 - 5.00;
    }

    @Test
    public void testMoreThan1000() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            items.add(new EItem(EItemType.MOTHERBOARD, "motherboard", 200.00));
        }

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 1200.00 * 0.9;
    }

    @Test
    public void testNotMoreThan1000() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem(EItemType.MOTHERBOARD, "motherboard", 999.99));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 999.99;
    }

    @Test
    public void testMoreThanFiveProcessorAndMoreThan10MouseAndTotalMoreThan1000() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            items.add(new EItem(EItemType.PROCESSOR, "processor", 100.00));
        }
        for (int i = 0; i < 11; i++) {
            items.add(new EItem(EItemType.MOUSE, "mouse", 10.00));
        }

        items.add(new EItem(EItemType.MOTHERBOARD, "Motherboard 1", 250.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "Motherboard 2", 250.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == (1210.00 - 50.00 - 10.00) * 0.9;
    }

    @Test
    public void testAllDiscounts() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            items.add(new EItem(EItemType.PROCESSOR, "processor", 50.00));
        }
        for (int i = 0; i < 11; i++) {
            items.add(new EItem(EItemType.MOUSE, "mouse", 10.00));
        }

        for (int i = 0; i < 11; i++) {
            items.add(new EItem(EItemType.KEYBOARD, "keyboard", 20.00));
        }

        items.add(new EItem(EItemType.MOTHERBOARD, "motherboard", 300.00));
        items.add(new EItem(EItemType.MOTHERBOARD, "motherboard", 200.00));

        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == (1130.00 - 25.00 - 10.00 - 10.00) * 0.9;
    }

    @Test(expected = BillException.class)
    public void testMoreThan30Items() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem item1 = new EItem(EItemType.MOUSE, "Mouse", 50.00);
        for (int i = 0; i < 33; i++) {
            items.add(item1);
        }

        // Act
        bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        fail();
    }

    @Test
    public void testCommission() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        items.add(new EItem(EItemType.MOUSE, "1", 8.00));
        // Act
        double total = bill.getOrderPrice(items, userMaggiorenne);

        // Assert
        assert total == 8 + 2;
    }

    @Test
    public void testGiveAwayToOnlyUnder18UsersInTime() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));
        listOrders.add(new Order(LocalTime.of(18, 59, 59), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMaggiorenne)));

        // Act
        int freeOrdersListSize = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert freeOrdersListSize == 1;
    }

    @Test
    public void testGiveAwayToOnlyUnder18UserNotInTime() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));
        listOrders.add(new Order(LocalTime.of(19, 00, 00), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMaggiorenne)));

        // Act
        int freeOrdersListSize = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert freeOrdersListSize == 0;
    }

    @Test
    public void testGiveAwayToOnlyOver18UserInTime() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));
        listOrders.add(new Order(LocalTime.of(18, 59, 59), userMaggiorenne, listEItem,
                bill.getOrderPrice(listEItem, userMaggiorenne)));

        // Act
        int freeOrdersListSize = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert freeOrdersListSize == 0;
    }

    @Test
    public void testGiveAwayToOnlyOver18UserNotInTime() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));
        listOrders.add(new Order(LocalTime.of(19, 00, 00), userMaggiorenne, listEItem,
                bill.getOrderPrice(listEItem, userMaggiorenne)));

        // Act
        int freeOrdersListSize = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert freeOrdersListSize == 0;
    }

    @Test
    public void testGiveAwayToAllTypeOfUsersInTime() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));
        listOrders.add(new Order(LocalTime.of(18, 59, 59), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMaggiorenne)));
        listOrders.add(new Order(LocalTime.of(18, 59, 59), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMinorenne)));
        // Act
        int freeOrdersListSize = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert freeOrdersListSize == 1;
    }

    @Test
    public void testGiveAwayToAllTypeOfUsersNotInTime() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));
        listOrders.add(new Order(LocalTime.of(19, 00, 00), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMaggiorenne)));
        listOrders.add(new Order(LocalTime.of(19, 00, 00), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMinorenne)));
        // Act
        int freeOrdersListSize = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert freeOrdersListSize == 0;
    }

    @Test
    public void testGiveAwayToOnlyUnder18DifferentUsersInTime() throws BillException {

        // Arrange
        boolean duplicated = false;
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));
        listEItem.add(new EItem(EItemType.PROCESSOR, "intel i5", 200.00));
        listEItem.add(new EItem(EItemType.MOUSE, "Apple magic", 80.00));
        listOrders.add(new Order(LocalTime.of(18, 59, 59), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMinorenne)));
        listOrders.add(new Order(LocalTime.of(18, 59, 59), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMinorenne2)));
        listOrders.add(new Order(LocalTime.of(18, 59, 59), userMinorenne, listEItem,
                bill.getOrderPrice(listEItem, userMinorenne3)));

        // Act
        List<Order> giftsList = bill.getUnder18FreeOrders(listOrders);
        List<User> userWithAlreadyAGift = new ArrayList<>();
        for (Order order : giftsList) {
            if (userWithAlreadyAGift.contains(order.getUser())) {
                duplicated = true;
            } else {
                userWithAlreadyAGift.add(order.getUser());
            }
        }

        // Assert
        if (duplicated) {
            fail();
        }
    }

    @Test
    public void testGiveAwayToOnlyUnder18UsersMoreThenTenInTime() throws BillException {

        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();

        listEItem.add(new EItem(EItemType.KEYBOARD, "RedDragon Lite", 40.00));

        for (int i = 1; i <= 20; i++) {
            User under18User = new User("Nome", "Cognome", "NumTel", LocalDate.of(2005, 1, i));
            listOrders.add(new Order(LocalTime.of(18, 59, 59), under18User, listEItem,
                    bill.getOrderPrice(listEItem, under18User)));
        }

        // Act
        int freeOrdersListSize = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert freeOrdersListSize == 10;
    }
}