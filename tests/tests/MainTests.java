package tests;

import application.Items;
import application.Main;
import application.ShoppingCart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testtools.CaptureStdOut;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTests {
    private final CaptureStdOut captureStdOut = new CaptureStdOut();
    private final InputStream originalIn = System.in;

    @BeforeEach
    void beforeEach() {
        captureStdOut.redirectToBuffer();
    }

    @AfterEach
    void afterEach() {
        captureStdOut.redirectToStdOut();
        System.setIn(originalIn);
        ShoppingCart.clear();
        Items.clear();
    }

    static void runMain(String input) {
        if (input == null) {
            System.setIn(null);
        } else {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
        }
        Main.main(null);
    }

    @Test
    void instantiatedTest() {
        assertThrows(IllegalStateException.class, Main::new);
    }

    @Test
    void invalidQuantityTest() {
        runMain(
                """
                a
                1 1
                4 1 a
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                java.util.InputMismatchException: null
                """
        );
    }

    @Test
    void printCurrentTotalTest() {
        runMain(
                """
                a
                2 2
                2
                1 3 1
                2
                6
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Cart is empty
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                c is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                The total for the items in the shopping cart: $74.98
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Transaction completed
                """
        );
    }

    @Test
    void removeItemTest4() {
        runMain(
                """
                a
                1 1
                3
                1 1 1
                1 2 1
                1 3 1
                1 4 1
                3
                5 0 1
                3
                5 2
                3
                5 3
                3
                5 5 4
                3
                1 2 1
                3
                6
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Cart is empty
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                a is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                b is added to the shopping cart
                The current count of items in the cart: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                c is added to the shopping cart
                The current count of items in the cart: 3
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                d is added to the shopping cart
                The current count of items in the cart: 4
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Invalid item ID
                Item ID (1 - 4):
                Removed item with item ID 1
                The current count of items in the cart: 3
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Removed item with item ID 2
                The current count of items in the cart: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Removed item with item ID 3
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                ID: 4 | Item: d | Quantity: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Invalid item ID
                Item ID (1 - 4):
                Removed item with item ID 4
                The current count of items in the cart: 0
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Cart is empty
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                b is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                ID: 2 | Item: b | Quantity: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Transaction completed
                """
        );
    }

    @Test
    void removeItemQuantityTest() {
        runMain(
                """
                a
                1 1
                3
                1 1 2
                3
                5 1
                3
                1 2 1
                3
                6
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Cart is empty
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                a is added to the shopping cart
                The current count of items in the cart: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                ID: 1 | Item: a | Quantity: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Removed item with item ID 1
                The current count of items in the cart: 0
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Cart is empty
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                b is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                ID: 2 | Item: b | Quantity: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest1() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                6
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                d is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                c is added to the shopping cart
                The current count of items in the cart: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                b is added to the shopping cart
                The current count of items in the cart: 3
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest2() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                1 1 1
                6
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                d is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                c is added to the shopping cart
                The current count of items in the cart: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                b is added to the shopping cart
                The current count of items in the cart: 3
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                a is added to the shopping cart
                The current count of items in the cart: 4
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest3() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                1 1 2
                6
                4 1 1
                6
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                d is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                c is added to the shopping cart
                The current count of items in the cart: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                b is added to the shopping cart
                The current count of items in the cart: 3
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                a is added to the shopping cart
                The current count of items in the cart: 5
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                The maximum acceptable purchase amount is $99,999.99
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                Changed quantity of a
                The current count of items in the cart: 4
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest4() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                1 1 3
                6
                4 1 1
                6
                """
        );
        captureStdOut.compare(
                """
                Enter name:
                Hello, a
                
                Enter state:
                1. CA/IL/NY
                2. Other
                Option:
                
                Enter shipping
                1. Standard
                2. Next Day
                Option:
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                d is added to the shopping cart
                The current count of items in the cart: 1
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                c is added to the shopping cart
                The current count of items in the cart: 2
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                b is added to the shopping cart
                The current count of items in the cart: 3
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                a is added to the shopping cart
                The current count of items in the cart: 6
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                The maximum acceptable purchase amount is $99,999.99
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Item ID (1 - 4):
                Quantity:
                Changed quantity of a
                The current count of items in the cart: 4
                
                1. Add item to the shopping cart
                2. Get current total
                3. See contents of shopping cart
                4. Edit quantity of items in shopping cart
                5. Remove items from shopping cart
                6. Checkout
                Option:
                Transaction completed
                """
        );
    }
}