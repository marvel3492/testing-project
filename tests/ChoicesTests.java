import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ChoicesTests {
    @Test
    void getNameTests() {
        assertThrows(IllegalArgumentException.class, ()->Choices.getName(null));
        assertEquals("a", Choices.getName(new Scanner("\r\n \na")));
        assertEquals("aa", Choices.getName(new Scanner("aa")));
    }

    @Test
    void getStateTests() {
        assertThrows(IllegalArgumentException.class, ()->Choices.getState(null));
        assertThrows(InputMismatchException.class, ()->Choices.getState(new Scanner("a")));
        assertEquals(1, Choices.getState(new Scanner("0 1")));
        assertEquals(2, Choices.getState(new Scanner("3 2")));
    }

    @Test
    void getShippingTests() {
        assertThrows(IllegalArgumentException.class, ()->Choices.getShipping(null));
        assertThrows(InputMismatchException.class, ()->Choices.getShipping(new Scanner("a")));
        assertEquals(1, Choices.getShipping(new Scanner("0 1")));
        assertEquals(2, Choices.getShipping(new Scanner("3 2")));
    }

    @Test
    void getOptionTests() {
        assertThrows(IllegalArgumentException.class, ()->Choices.getOption(null));
        assertThrows(InputMismatchException.class, ()->Choices.getOption(new Scanner("a")));
        assertEquals(1, Choices.getOption(new Scanner("0 1")));
        assertEquals(2, Choices.getOption(new Scanner("2")));
        assertEquals(3, Choices.getOption(new Scanner("3")));
        assertEquals(4, Choices.getOption(new Scanner("4")));
        assertEquals(5, Choices.getOption(new Scanner("5")));
        assertEquals(6, Choices.getOption(new Scanner("7 6")));
    }

    @Test
    void getItemIdTests() {
        assertThrows(IllegalArgumentException.class, ()->Choices.getItemId(null));
        assertEquals(0, Choices.getItemId(new Scanner("")));

        assertDoesNotThrow(()->Items.addItem("a", 0.01));
        assertThrows(InputMismatchException.class, ()->Choices.getItemId(new Scanner("a")));
        assertEquals(1, Choices.getItemId(new Scanner("0 2 1")));

        assertDoesNotThrow(()->Items.addItem("b", 49.98));
        assertEquals(1, Choices.getItemId(new Scanner("0 1")));
        assertEquals(2, Choices.getItemId(new Scanner("3 2")));

        assertDoesNotThrow(()->Items.addItem("c", 50.0));
        assertEquals(1, Choices.getItemId(new Scanner("0 1")));
        assertEquals(2, Choices.getItemId(new Scanner("2")));
        assertEquals(3, Choices.getItemId(new Scanner("4 3")));

        assertDoesNotThrow(()->Items.addItem("d", 100.0));
        assertEquals(1, Choices.getItemId(new Scanner("0 1")));
        assertEquals(2, Choices.getItemId(new Scanner("2")));
        assertEquals(3, Choices.getItemId(new Scanner("3")));
        assertEquals(4, Choices.getItemId(new Scanner("5 4")));

        Items.clear();
    }

    @Test
    void getQuantityTests() {
        assertThrows(IllegalArgumentException.class, ()->Choices.getQuantity(null));
        assertThrows(InputMismatchException.class, ()->Choices.getQuantity(new Scanner("a")));
        assertEquals(1, Choices.getQuantity(new Scanner("0 1")));
        assertEquals(2, Choices.getQuantity(new Scanner("2")));
    }
}
