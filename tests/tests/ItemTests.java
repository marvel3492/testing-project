package tests;

import application.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTests {
    @Test
    void invalidItemTests() {
        assertThrows(IllegalArgumentException.class, ()->new Item(null, 0.0));
        assertThrows(IllegalArgumentException.class, ()->new Item("", 0.0));
        assertThrows(IllegalArgumentException.class, ()->new Item("a", -0.01));
        assertThrows(IllegalArgumentException.class, ()->new Item("a", 100000.0));
    }

    @Test
    void validItemTest1() {
        Item item = new Item("a", 0.0);
        Assertions.assertEquals("a", item.getName());
        Assertions.assertEquals(0.0, item.getPrice());
    }

    @Test
    void validItemTest2() {
        Item item = new Item("aa", 0.01);
        Assertions.assertEquals("aa", item.getName());
        Assertions.assertEquals(0.01, item.getPrice());
    }

    @Test
    void validItemTest3() {
        Item item = new Item("a", 99999.98);
        Assertions.assertEquals("a", item.getName());
        Assertions.assertEquals(99999.98, item.getPrice());
    }

    @Test
    void validItemTest4() {
        Item item = new Item("a", 99999.99);
        Assertions.assertEquals("a", item.getName());
        Assertions.assertEquals(99999.99, item.getPrice());
    }
}
