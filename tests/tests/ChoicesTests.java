package tests;

import application.Choices;
import application.Items;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testtools.CaptureStdOut;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ChoicesTests {
    private final CaptureStdOut captureStdOut = new CaptureStdOut();

    @BeforeEach
    void beforeEach() {
        captureStdOut.redirectToBuffer();
    }

    @AfterEach
    void afterEach() {
        captureStdOut.redirectToStdOut();
    }

    @Test
    void instantiatedTest() {
        assertThrows(IllegalStateException.class, Choices::new);
    }

    @Test
    void getNameTests() {
        assertThrows(IllegalArgumentException.class, ()-> Choices.getName(null));
        Assertions.assertEquals("a", Choices.getName(new Scanner("\r\n \na")));
        Assertions.assertEquals("aa", Choices.getName(new Scanner("aa")));
        captureStdOut.compare(
                """
                Enter name:
                Name cannot be blank
                Enter name:
                Name cannot be blank
                Enter name:
                Enter name:
                """
        );
    }

    @Test
    void getStateTests() {
        assertThrows(IllegalArgumentException.class, ()-> Choices.getState(null));
        assertThrows(InputMismatchException.class, ()-> Choices.getState(new Scanner("a")));
        Assertions.assertEquals(1, Choices.getState(new Scanner("0 1")));
        Assertions.assertEquals(2, Choices.getState(new Scanner("3 2")));
        captureStdOut.compare(
                """
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                Invalid option
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                Invalid option
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                """
        );
    }

    @Test
    void getShippingTests() {
        assertThrows(IllegalArgumentException.class, ()-> Choices.getShipping(null));
        assertThrows(InputMismatchException.class, ()-> Choices.getShipping(new Scanner("a")));
        Assertions.assertEquals(1, Choices.getShipping(new Scanner("0 1")));
        Assertions.assertEquals(2, Choices.getShipping(new Scanner("3 2")));
        captureStdOut.compare(
                """
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                Invalid option
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                Invalid option
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                """
        );
    }

    @Test
    void getOptionTests() {
        assertThrows(IllegalArgumentException.class, ()-> Choices.getOption(null));
        assertThrows(InputMismatchException.class, ()-> Choices.getOption(new Scanner("a")));
        Assertions.assertEquals(1, Choices.getOption(new Scanner("0 1")));
        Assertions.assertEquals(2, Choices.getOption(new Scanner("2")));
        Assertions.assertEquals(3, Choices.getOption(new Scanner("3")));
        Assertions.assertEquals(4, Choices.getOption(new Scanner("4")));
        Assertions.assertEquals(5, Choices.getOption(new Scanner("5")));
        Assertions.assertEquals(6, Choices.getOption(new Scanner("7 6")));
        captureStdOut.compare(
                """
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Invalid option
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Invalid option
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                """
        );
    }

    @Test
    void getItemIdTests() {
        assertThrows(IllegalArgumentException.class, ()-> Choices.getItemId(null));
        Assertions.assertEquals(0, Choices.getItemId(new Scanner("")));
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        assertThrows(InputMismatchException.class, ()-> Choices.getItemId(new Scanner("a")));
        Assertions.assertEquals(1, Choices.getItemId(new Scanner("0 2 1")));
        assertDoesNotThrow(()-> Items.addItem("b", 49.98));
        Assertions.assertEquals(1, Choices.getItemId(new Scanner("0 1")));
        Assertions.assertEquals(2, Choices.getItemId(new Scanner("3 2")));
        assertDoesNotThrow(()-> Items.addItem("c", 50.0));
        Assertions.assertEquals(1, Choices.getItemId(new Scanner("0 1")));
        Assertions.assertEquals(2, Choices.getItemId(new Scanner("2")));
        Assertions.assertEquals(3, Choices.getItemId(new Scanner("4 3")));
        assertDoesNotThrow(()-> Items.addItem("d", 100.0));
        Assertions.assertEquals(1, Choices.getItemId(new Scanner("0 1")));
        Assertions.assertEquals(2, Choices.getItemId(new Scanner("2")));
        Assertions.assertEquals(3, Choices.getItemId(new Scanner("3")));
        Assertions.assertEquals(4, Choices.getItemId(new Scanner("5 4")));
        Items.clear();
        captureStdOut.compare(
                """
                No items available
                Item ID (1 - 1):
                Item ID (1 - 1):
                Invalid item ID
                Item ID (1 - 1):
                Invalid item ID
                Item ID (1 - 1):
                Item ID (1 - 2):
                Invalid item ID
                Item ID (1 - 2):
                Item ID (1 - 2):
                Invalid item ID
                Item ID (1 - 2):
                Item ID (1 - 3):
                Invalid item ID
                Item ID (1 - 3):
                Item ID (1 - 3):
                Item ID (1 - 3):
                Invalid item ID
                Item ID (1 - 3):
                Item ID (1 - 4):
                Invalid item ID
                Item ID (1 - 4):
                Item ID (1 - 4):
                Item ID (1 - 4):
                Item ID (1 - 4):
                Invalid item ID
                Item ID (1 - 4):
                """
        );
    }

    @Test
    void getQuantityTests() {
        assertThrows(IllegalArgumentException.class, ()-> Choices.getQuantity(null));
        assertThrows(InputMismatchException.class, ()-> Choices.getQuantity(new Scanner("a")));
        Assertions.assertEquals(1, Choices.getQuantity(new Scanner("0 1")));
        Assertions.assertEquals(2, Choices.getQuantity(new Scanner("2")));
        captureStdOut.compare(
                """
                Quantity:
                Quantity:
                Invalid quantity
                Quantity:
                Quantity:
                """
        );
    }
}