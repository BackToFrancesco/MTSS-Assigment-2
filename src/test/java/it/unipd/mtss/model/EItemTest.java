////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import org.junit.Before;
import org.junit.Test;

public class EItemTest {
    private EItem mouse, motherboard;

    @Before
    public void setup() {
        mouse = new EItem(EItemType.MOUSE, "mouse 1",  10.00);
        motherboard = new EItem(EItemType.KEYBOARD, "keyboard 1",  10.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithTypeNull() {
        new EItem( null,"???", 10.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNameNull() {
        new EItem(EItemType.MOUSE,null,  10.00);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativePrice() {
        new EItem(EItemType.KEYBOARD,"keyboard", -10.00);
    }

    @Test
    public void testTypeGetter() {
        assert mouse.getEItemType() == EItemType.MOUSE;
        assert motherboard.getEItemType() == EItemType.KEYBOARD;
    }

    @Test
    public void testPriceGetter() {
        assert motherboard.getPrice() == 10.00;
        assert mouse.getPrice() == 10.00;
    }



}