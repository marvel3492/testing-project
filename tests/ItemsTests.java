import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemsTests {
    @AfterEach
    void afterEach() {
        Items.clear();
    }

    @Test
    void invalidItemsTests() {
        assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertThrows(IllegalArgumentException.class, ()->Items.addItem(null, 0.0));
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("", 0.0));
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("a", -0.01));
        assertNull(Items.getItem(1));
        assertEquals(0, Items.getMaxId());
    }

    @Test
    void validItemsTest1() {
        assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()->Items.addItem("a", 0.0));
        Item item = Items.getItem(1);
        assertEquals("a", item.name());
        assertEquals(0.0, item.price());
        assertEquals(1, Items.getMaxId());
    }

    @Test
    void validItemsTest2() {
        assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()->Items.addItem("aa", 0.01));
        Item item = Items.getItem(1);
        assertEquals("aa", item.name());
        assertEquals(0.01, item.price());
        assertEquals(1, Items.getMaxId());
    }

    @Test
    void duplicateTests() {
        assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()->Items.addItem("a", 0.0));
        Item item = Items.getItem(1);
        assertEquals("a", item.name());
        assertEquals(0.0, item.price());
        assertEquals(1, Items.getMaxId());
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("a", 0.0));
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("a", 0.01));
        item = Items.getItem(1);
        assertEquals("a", item.name());
        assertEquals(0.0, item.price());
        assertEquals(1, Items.getMaxId());
        assertNull(Items.getItem(2));
    }

    @Test
    void trimTests() {
        assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()->Items.addItem("a ", 0.0));
        Item item = Items.getItem(1);
        assertEquals("a", item.name());
        assertEquals(0.0, item.price());
        assertEquals(1, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem(" b", 0.01));
        item = Items.getItem(2);
        assertEquals("b", item.name());
        assertEquals(0.01, item.price());
        assertEquals(2, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem(" c ", 0.02));
        item = Items.getItem(3);
        assertEquals("c", item.name());
        assertEquals(0.02, item.price());
        assertEquals(3, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem("d  ", 0.03));
        item = Items.getItem(4);
        assertEquals("d", item.name());
        assertEquals(0.03, item.price());
        assertEquals(4, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem(" e  ", 0.04));
        item = Items.getItem(5);
        assertEquals("e", item.name());
        assertEquals(0.04, item.price());
        assertEquals(5, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem("  f", 0.05));
        item = Items.getItem(6);
        assertEquals("f", item.name());
        assertEquals(0.05, item.price());
        assertEquals(6, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem("  g ", 0.06));
        item = Items.getItem(7);
        assertEquals("g", item.name());
        assertEquals(0.06, item.price());
        assertEquals(7, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem("  h  ", 0.07));
        item = Items.getItem(8);
        assertEquals("h", item.name());
        assertEquals(0.07, item.price());
        assertEquals(8, Items.getMaxId());
        for (char c = 'a'; c <= 'h'; c++) {
            char finalC = c;
            assertThrows(IllegalArgumentException.class, ()->Items.addItem(String.valueOf(finalC), 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem(finalC + " ", 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem(finalC + "  ", 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem(" " + finalC, 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem("  " + finalC, 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem(" " + finalC + " ", 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem("  " + finalC + " ", 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem(" " + finalC + "  ", 0.00));
            assertThrows(IllegalArgumentException.class, ()->Items.addItem("  " + finalC + "  ", 0.00));
        }

        assertNull(Items.getItem(9));
    }

    @Test
    void capitalizationTests() {
        assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()->Items.addItem("a", 0.0));
        Item item = Items.getItem(1);
        assertEquals("a", item.name());
        assertEquals(0.0, item.price());
        assertEquals(1, Items.getMaxId());
        assertDoesNotThrow(()->Items.addItem("B", 0.01));
        item = Items.getItem(2);
        assertEquals("B", item.name());
        assertEquals(0.01, item.price());
        assertEquals(2, Items.getMaxId());
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("a", 0.00));
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("A", 0.00));
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("b", 0.00));
        assertThrows(IllegalArgumentException.class, ()->Items.addItem("B", 0.00));
        assertNull(Items.getItem(3));
    }

    @Test
    void itemsSizeTest() {
        assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        int itemId = 1;
        for (; itemId <= Items.MAX_ITEMS; itemId++) {
            String name = Integer.toString(itemId);
            double price = Double.parseDouble(name);
            assertDoesNotThrow(()->Items.addItem(name, price));
            Item item = Items.getItem(itemId);
            assertEquals(name, item.name());
            assertEquals(price, item.price());
            assertEquals(itemId, Items.getMaxId());
        }

        String name = Integer.toString(itemId);
        double price = Double.parseDouble(name);
        assertThrows(IllegalStateException.class, ()->Items.addItem(name, price));
        assertNull(Items.getItem(itemId));
        assertEquals(Items.MAX_ITEMS, Items.getMaxId());
    }
}
