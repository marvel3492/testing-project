package tests;

import application.Item;
import application.Items;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemsTests {
    @AfterEach
    void afterEach() {
        Items.clear();
    }

    @Test
    void instantiatedTest() {
        assertThrows(IllegalStateException.class, Items::new);
    }

    @Test
    void invalidItemsTests() {
        Assertions.assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem(null, 0.0));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("", 0.0));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("a", -0.01));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("a", 100000.0));
        assertNull(Items.getItem(1));
        Assertions.assertEquals(0, Items.getMaxId());
    }

    @Test
    void validItemsTest1() {
        Assertions.assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()-> Items.addItem("a", 0.0));
        Item item = Items.getItem(1);
        Assertions.assertEquals("a", item.name());
        Assertions.assertEquals(0.0, item.price());
        Assertions.assertEquals(1, Items.getMaxId());
    }

    @Test
    void validItemsTest2() {
        Assertions.assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()-> Items.addItem("aa", 0.01));
        Item item = Items.getItem(1);
        Assertions.assertEquals("aa", item.name());
        Assertions.assertEquals(0.01, item.price());
        Assertions.assertEquals(1, Items.getMaxId());
    }

    @Test
    void duplicateTests() {
        Assertions.assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()-> Items.addItem("a", 0.0));
        Item item = Items.getItem(1);
        Assertions.assertEquals("a", item.name());
        Assertions.assertEquals(0.0, item.price());
        Assertions.assertEquals(1, Items.getMaxId());
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("a", 0.0));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("a", 0.01));
        item = Items.getItem(1);
        Assertions.assertEquals("a", item.name());
        Assertions.assertEquals(0.0, item.price());
        Assertions.assertEquals(1, Items.getMaxId());
        assertNull(Items.getItem(2));
    }

    @Test
    void trimTests() {
        Assertions.assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()-> Items.addItem("a ", 0.0));
        Item item = Items.getItem(1);
        Assertions.assertEquals("a", item.name());
        Assertions.assertEquals(0.0, item.price());
        Assertions.assertEquals(1, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem(" b", 0.01));
        item = Items.getItem(2);
        Assertions.assertEquals("b", item.name());
        Assertions.assertEquals(0.01, item.price());
        Assertions.assertEquals(2, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem(" c ", 0.02));
        item = Items.getItem(3);
        Assertions.assertEquals("c", item.name());
        Assertions.assertEquals(0.02, item.price());
        Assertions.assertEquals(3, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem("d  ", 0.03));
        item = Items.getItem(4);
        Assertions.assertEquals("d", item.name());
        Assertions.assertEquals(0.03, item.price());
        Assertions.assertEquals(4, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem(" e  ", 0.04));
        item = Items.getItem(5);
        Assertions.assertEquals("e", item.name());
        Assertions.assertEquals(0.04, item.price());
        Assertions.assertEquals(5, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem("  f", 0.05));
        item = Items.getItem(6);
        Assertions.assertEquals("f", item.name());
        Assertions.assertEquals(0.05, item.price());
        Assertions.assertEquals(6, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem("  g ", 0.06));
        item = Items.getItem(7);
        Assertions.assertEquals("g", item.name());
        Assertions.assertEquals(0.06, item.price());
        Assertions.assertEquals(7, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem("  h  ", 0.07));
        item = Items.getItem(8);
        Assertions.assertEquals("h", item.name());
        Assertions.assertEquals(0.07, item.price());
        Assertions.assertEquals(8, Items.getMaxId());
        for (char c = 'a'; c <= 'h'; c++) {
            char finalC = c;
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem(String.valueOf(finalC), 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem(finalC + " ", 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem(finalC + "  ", 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem(" " + finalC, 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem("  " + finalC, 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem(" " + finalC + " ", 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem("  " + finalC + " ", 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem(" " + finalC + "  ", 0.00));
            assertThrows(IllegalArgumentException.class, ()-> Items.addItem("  " + finalC + "  ", 0.00));
        }

        assertNull(Items.getItem(9));
    }

    @Test
    void capitalizationTests() {
        Assertions.assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        assertDoesNotThrow(()-> Items.addItem("a", 0.0));
        Item item = Items.getItem(1);
        Assertions.assertEquals("a", item.name());
        Assertions.assertEquals(0.0, item.price());
        Assertions.assertEquals(1, Items.getMaxId());
        assertDoesNotThrow(()-> Items.addItem("B", 0.01));
        item = Items.getItem(2);
        Assertions.assertEquals("B", item.name());
        Assertions.assertEquals(0.01, item.price());
        Assertions.assertEquals(2, Items.getMaxId());
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("a", 0.00));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("A", 0.00));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("b", 0.00));
        assertThrows(IllegalArgumentException.class, ()-> Items.addItem("B", 0.00));
        assertNull(Items.getItem(3));
    }

    @Test
    void itemsSizeTest() {
        Assertions.assertEquals(0, Items.getMaxId());
        assertNull(Items.getItem(0));
        int itemId = 1;
        for (; itemId <= Items.MAX_ITEMS; itemId++) {
            String name = Integer.toString(itemId);
            double price = Double.parseDouble(name);
            assertDoesNotThrow(()-> Items.addItem(name, price));
            Item item = Items.getItem(itemId);
            Assertions.assertEquals(name, item.name());
            Assertions.assertEquals(price, item.price());
            Assertions.assertEquals(itemId, Items.getMaxId());
        }

        String name = Integer.toString(itemId);
        double price = Double.parseDouble(name);
        assertThrows(IllegalStateException.class, ()-> Items.addItem(name, price));
        assertNull(Items.getItem(itemId));
        Assertions.assertEquals(Items.MAX_ITEMS, Items.getMaxId());
    }
}